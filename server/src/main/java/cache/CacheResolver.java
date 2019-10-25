package cache;

import cache.annotations.Cached;
import cache.annotations.TTL;
import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class CacheResolver<T> implements ICacheResolver<T> {

    private BiConsumer<Object, Object> consumer;
    private volatile ConcurrentHashMap<String, Pair<Long, Object>> localCache = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<String, Tuple<Method, T, List<Object>>> cacheResetHandler = new ConcurrentHashMap<>();

    public CacheResolver(final BiConsumer<Object, Object> consumer) {
        this.consumer = consumer;
    }

    @Override
    public synchronized void resetCache(String cacheName, final Class<?> instanceClass) {

        cacheName = resetCacheKey(instanceClass.getName(), cacheName);

        //check if the value is present into cache
        if (cacheResetHandler.get(cacheName) == null) {
            return;
        }

        //get the reset handler
        final Tuple<Method, T, List<Object>> cacheReseated = cacheResetHandler.get(cacheName);

        //put the rew value into local cache
        try {
            addInLocalCache(
                    cacheReseated.first,
                    cacheReseated.second,
                    cacheReseated.third != null ? cacheReseated.third.toArray() : null
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized Object getValue(
            final Method method, final T instance, final Object... args) throws Exception {


        //get the method from implementation instead of interface
        //enables that cache annotation to be on method from interface implementation
        final Cached cached = getCacheAnnotation(
                instance.getClass().getDeclaredMethod(
                        method.getName(), method.getParameterTypes()
                )
        );

        //check if the method is not cacheable
        if (cached == null) {
            return method.invoke(instance, args);
        }

        //put the most recent value
        cacheResetHandler.put(
                resetCacheKey(instance.getClass().getName(), cached.cacheName()),
                new Tuple<>(method, instance, args != null ? Arrays.asList(args) : null)
        );

        // if the cache is invalid than restore the value
        if (!isCacheValid(method, cached, instance)) {
            addInLocalCache(method, instance, args);
        }

        //get the value from cache
        return localCache
                .get(cacheKey(instance, method))
                .getValue();
    }


    private void addInLocalCache(
            final Method method, final T instance, final Object[] args) throws IllegalAccessException, InvocationTargetException {

        //old value
        final Object old = localCache.get(
                cacheKey(instance, method)
        );

        //new value
        final Object newValue = method.invoke(instance, args);
        localCache.put(
                cacheKey(instance, method),
                new Pair<>(
                        new Date().getTime(),
                        newValue
                )
        );

        //notify
        consumer.accept(old, newValue);
    }

    private Cached getCacheAnnotation(final Method method) {
        return method.getDeclaredAnnotation(Cached.class);
    }

    private boolean isCacheValid(final Method method, final Cached cached, final T instance) {

        final Pair<Long, Object> cachedValue = localCache.get(
                cacheKey(instance, method)
        );

        // if the cache is not saved then the result is invalid
        if (cachedValue == null) {
            return false;
        }

        //compare if the time when the data was cached + cache live value is greater than current time
        long cacheValue = cached.timeUnit().equals(TTL.MILLISECONDS) ?
                cached.cacheTime() : cached.cacheTime() * 1000;

        return cachedValue.getKey() + cacheValue > new Date().getTime();
    }


    private String cacheKey(final T instance, final Method method) {
        return instance.getClass().getName() + "." + method.getName();
    }

    private String resetCacheKey(final String instanceClassName, final String cacheName) {
        return String.format(
                "%s.%s", instanceClassName, cacheName
        );
    }

    private static class Tuple<T, U, V> {
        T first;
        U second;
        V third;

        Tuple(final T first, final U second, final V third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }
}

