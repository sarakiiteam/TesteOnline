package utils.controllers;

import java.util.Map;

public interface IModelChecker {
    Map.Entry<Boolean, String> isModelValid(final Object model);
}
