package cache.proxies;

public interface ICacheableProxy<T> {

    /**
     * This method is used in order to create a proxy,
     * Before each method of that proxy to be called, it will be invoked an InvocationHandler to get the cache memorised
     * value, if the method is @Cached
     * @return a proxy class from interface T
     */
    T asCacheableProxy();

    /**
     * Updates the cache value into local memory, for the current instance
     * @param cacheName: the name of the cache
     */
    void refreshCacheForThisInstance(final String cacheName);

    /**
     * Updates the cache value into local memory, for the current instance specified by class @cachedMethodLocationClass
     * @param cacheName: the name of the cache
     * @param cachedMethodLocationClass: the class of the method location
     */
    void refreshGlobalCache(
            final String cacheName, final Class<?> cachedMethodLocationClass);
}
