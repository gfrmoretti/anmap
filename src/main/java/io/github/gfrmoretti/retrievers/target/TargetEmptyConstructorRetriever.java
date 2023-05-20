package io.github.gfrmoretti.retrievers.target;

import java.lang.reflect.Constructor;
import java.util.Optional;

class TargetEmptyConstructorRetriever<Target> extends TargetRetriever<Target> {
    public TargetEmptyConstructorRetriever(Constructor<?>[] constructors, TargetFactory<?, Target> factory) {
        super(constructors, factory);
    }

    @Override
    public boolean canRetrieveValue() {
        return true;
    }

    @Override
    public Optional<Target> retrieve() {
        try {
            return Optional.ofNullable(factory.createWithEmptyConstructor());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
