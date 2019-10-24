package cache.proxies;

import cache.ICacheResolver;

import java.lang.reflect.Proxy;

public abstract class ProxyCacheBuilder<T> implements ICacheableProxy<T> {

    private T cacheable;
    private final ICacheResolver<T> cacheResolver;

    protected ProxyCacheBuilder(final ICacheResolver<T> cacheResolver) {
        this.cacheResolver = cacheResolver;
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

    protected void setCacheable(T cacheable) {
        this.cacheable = cacheable;
    }
}
