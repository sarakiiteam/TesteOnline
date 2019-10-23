package utils.controllers.checkers;

import javafx.util.Pair;

import java.util.List;

public interface IModelChecker {
    Pair<Boolean, String> isModelValid(final Object model);
}
