package utils.controllers.restrictions;

import utils.controllers.IRestriction;

public class NumberRestriction implements IRestriction<Object> {
    @Override
    public boolean respects(final Object object) {
        Integer integer = (Integer) object;
        return integer >= 0;
    }

    @Override
    public boolean canBeApplied(final Object object) {
        return Integer.class.equals(object.getClass());
    }
}
