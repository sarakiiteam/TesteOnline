package utils.controllers.restrictions;

public class StringRestriction implements IRestriction<Object> {

    @Override
    public boolean respects(final Object object) {
        final String obj = (String) object;
        return obj != null && !obj.isEmpty();
    }

    @Override
    public boolean canBeApplied(final Object object) {

        if (object == null) {
            return true;
        }

        return String.class.equals(object.getClass());
    }
}
