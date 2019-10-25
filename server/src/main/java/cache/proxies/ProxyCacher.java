package cache.proxies;

import cache.ICacheResolver;
import cache.annotations.Cacheable;
import cache.annotations.Cached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class ProxyCacher<T> implements ICacheableProxy<T> {

    private final T cacheable;
    private final ICacheResolver<T> cacheResolver;

    /**
     * Constructs a proxy cacher
     *
     * @param cacheResolver: the object that is delegated to make memory cache-ing
     * @throws RuntimeException if cache resolver is not instantiated
     */
    @Autowired
    protected ProxyCacher(final ICacheResolver<T> cacheResolver) {

        if (cacheResolver == null) {
            throw new RuntimeException("No bean found for ICacheResolver");
        }

        this.cacheResolver = cacheResolver;
        this.cacheable = new Checker(getProxySource()).getModel();
    }


    /**
     * This method is used in order to transform a resource into a cacheable one
     *
     * @param object: the object that will be converted into a cacheable resource
     * @param <T>:    generic type representing the objects interface
     * @return a cacheable proxy that caches only annotated methods with @Cache
     */
    @SuppressWarnings("unchecked")
    public static <T> T getCacheable(final T object) {

        if (!isCacheable(object)) {

            new Exception(
                    String.format(
                            "Object %s is not cacheable.....\n" +
                            "Please annotate with @Cacheable annotation.\n" +
                            "Returned the non cacheable version of object ...\n",
                            object.getClass().getName()
                    )
            ).printStackTrace();

            return object;
        }

        try {
            return (T) object
                    .getClass()
                    .getMethod("asCacheableProxy")
                    .invoke(object);
        } catch (final Exception ex) {
            new Exception(
                    String.format(
                            "Object %s is not cacheable....\n" +
                            "Please extend from ProxyCacher class.\n" +
                            "Returned the non cacheable version of object ...\n",
                            object.getClass().getName()
                    )
            ).printStackTrace();
            return object;
        }
    }

    private static <T> boolean isCacheable(final T object) {
        return object
                .getClass()
                .getDeclaredAnnotation(Cacheable.class) != null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public T asCacheableProxy() {
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                cacheable.getClass().getInterfaces(),
                (proxy, method, args) -> cacheResolver.getValue(method, cacheable, args)
        );
    }

    @Override
    public void refreshCacheForThisInstance(final String cacheName) {
        cacheResolver.resetCache(cacheName, cacheable.getClass());
    }


    @Override
    @Deprecated
    public void refreshGlobalCache(
            final String cacheName, final Class<?> cachedMethodLocationClass) {

        cacheResolver.resetCache(
                cacheName, cachedMethodLocationClass
        );
    }

    /**
     * This method should return the instance of the object that will be cached
     *
     * @return the cached instance
     */
    protected abstract T getProxySource();

    private class Checker {

        private final T model;

        Checker(final T model) {
            checkCachedAnnotation(model);
            this.model = model;
        }

        T getModel() {
            return model;
        }

        private void checkCachedAnnotation(final T model) {

            //check if the class is annotated with @Cacheable annotation
            if (model.getClass().getDeclaredAnnotation(Cacheable.class) == null) {
                throw new RuntimeException(
                        String.format("The %s class must be annotated with @Cacheable annotation",
                                model.getClass().getName()
                        )
                );
            }

            final Set<String> annotations = new HashSet<>();
            //go through all methods annotations and check if there are distinct names for annotations
            Arrays
                    .stream(model.getClass().getMethods())
                    .map(method -> method.getAnnotation(Cached.class))
                    .filter(Objects::nonNull)
                    .forEach(annotation -> {
                        if (annotations.contains(annotation.cacheName())) {
                            throw new RuntimeException("All cached names must be different");
                        }
                        annotations.add(annotation.cacheName());
                    });
        }
    }
}
