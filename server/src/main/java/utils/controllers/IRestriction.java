package utils.controllers;

public interface IRestriction<T> {

    boolean respects(final T object);

    boolean canBeApplied(final Object object);
}
