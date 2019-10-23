package utils.controllers.checkers;

import javafx.util.Pair;
import utils.controllers.IModelChecker;
import utils.controllers.IRestriction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RestrictionModelChecker implements IModelChecker {

    private final List<IRestriction<Object>> restrictions = new ArrayList<>();

    @Override
    public Pair<Boolean, String> isModelValid(final Object model) {

        final StringBuilder errors = new StringBuilder();

        //get object field
        final Field[] fields = getFields(model);

        //iterate through fields
        for (final Field field : fields) {
            if (!respectsCondition(field, model)) {
                errors.append(
                        String.format("Field '%s' has a bad value. ", field.getName())
                );
            }
        }

        return new Pair<>(errors.length() == 0,  errors.toString());
    }

    public void addRestriction(final IRestriction<Object> restriction) {
        restrictions.add(restriction);
    }

    private boolean respectsCondition(final Field field, final Object model) {
        return checkRestrictionsByType(model, field.getName());
    }

    private boolean checkRestrictionsByType(final Object model, final String fieldName) {

        try {

            for (final IRestriction<Object> restriction : restrictions) {

                if (!restriction.canBeApplied(getValueFromField(fieldName, model))) {
                    continue;
                }

                if (!restriction.respects(getValueFromField(fieldName, model))) {
                    return false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    private Field[] getFields(final Object object) {
        return object.getClass().getDeclaredFields();
    }

    @SuppressWarnings("unchecked")
    private <T> T getValueFromField(
            final String filedName, final Object object) throws Exception {

        final String methodName = "get" + (filedName.charAt(0) + "").toUpperCase() + filedName.substring(1);
        final Method method = object.getClass().getMethod(methodName);

        return (T) method.invoke(object);
    }
}
