package utils.controllers.injection;

import cache.annotations.Cacheable;

public class CacheableChecker {

    public static <T> boolean isCacheable(final T object) {
        return object
                .getClass()
                .getDeclaredAnnotation(Cacheable.class) != null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T asCacheable(final T object) {

        if (!isCacheable(object)) {
            throw new RuntimeException(
                    String.format(
                            "No Cacheable instance found. Please annotate with @Cacheable annotation %s class and provide the cacheable proxy implementation",
                            object.getClass().getName()
                    )
            );
        }

        try {
            return (T) object
                    .getClass()
                    .getMethod("asCacheableProxy")
                    .invoke(object);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }

}
