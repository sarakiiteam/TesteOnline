package cache;

import java.lang.reflect.Method;

public interface ICacheResolver<T> {

    void resetCache();

    Object getValue(
            final Method method,
            final T instance, final Object... args) throws Exception;

}
