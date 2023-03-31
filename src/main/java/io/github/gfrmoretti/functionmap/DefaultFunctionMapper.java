package io.github.gfrmoretti.functionmap;

import java.util.Optional;

public class DefaultFunctionMapper implements FunctionMapper<Object, Object> {
    @Override
    public Optional<Object> mapValue(Object o) {
        return Optional.ofNullable(o);
    }

    @Override
    public Optional<Object> inverseMapValue(Object o) {
        return Optional.ofNullable(o);
    }
}
