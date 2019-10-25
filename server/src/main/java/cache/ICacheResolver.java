package cache;

import java.lang.reflect.Method;

public interface ICacheResolver<T> {

    void resetCache(final String cacheName, final Class<?> instanceClass);

    Object getValue(
            final Method method,
            final T instance, final Object... args) throws Exception;

}
