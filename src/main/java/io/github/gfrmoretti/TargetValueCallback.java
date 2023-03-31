package io.github.gfrmoretti;

import java.lang.reflect.Field;

@FunctionalInterface
interface TargetValueCallback {
    void executeAction(Field targetField, Object sourceValue) throws Exception;
}
