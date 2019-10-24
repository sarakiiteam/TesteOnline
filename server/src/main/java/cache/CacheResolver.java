package cache;

import cache.annotations.Cached;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class CacheResolver<T> implements ICacheResolver<T> {

    private BiConsumer<Object, Object> consumer;
    private volatile ConcurrentHashMap<String, Pair<Long, Object>> localCache = new ConcurrentHashMap<>();

    public CacheResolver(final BiConsumer<Object, Object> consumer) {
        this.consumer = consumer;
    }

    @Override
    public synchronized void resetCache() {
        localCache.clear();
    }

    @Override
    public synchronized Object getValue(
            final Method method,
            final T instance, final Object... args) throws Exception {

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

        // if the cache is invalid than restore the value
        if (!isCacheValid(method, cached, instance)) {
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

        //get the value from cache
        return localCache
                .get(cacheKey(instance, method))
                .getValue();
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
        long cacheValue = cached.cacheTime();
        return cachedValue.getKey() + cacheValue > new Date().getTime();
    }

    private String cacheKey(final T instance, final Method method) {
        return instance.getClass().getName() + "." + method.getName();
    }
}

