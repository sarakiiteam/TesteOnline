package utils.controllers.restrictions;

import database.models.enums.Difficulty;

public class DifficultyEnumRestriction implements IRestriction<Object> {
    @Override
    public boolean respects(final Object object) {
        return object != null;
    }

    @Override
    public boolean canBeApplied(final Object object) {

        if (object == null) {
            return true;
        }

        return Difficulty.class.equals(object.getClass());
    }
}
