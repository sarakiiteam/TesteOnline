package cache.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = VolatileCaches.class)
public @interface VolatileCache {
    String cacheUpdate();
}
