package io.github.gfrmoretti.retrievers.target;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Optional;

@Slf4j
class TargetRegularConstructorRetriever<Target> extends TargetRetriever<Target> {
    public TargetRegularConstructorRetriever(Constructor<?>[] constructors, TargetFactory<?, Target> factory) {
        super(constructors, factory);
    }

    @Override
    public boolean canRetrieveValue() {
        return true;
    }

    @Override
    public Optional<Target> retrieve() {
        Optional<Target> result = Optional.empty();
        for (var constructor : constructors) {
            if (TargetFactory.constructorHasArguments(constructor)) {
                try {
                    result = Optional.ofNullable(factory.createWithConstructor(constructor));
                    break;
                } catch (Exception e) {
                    log.trace("Cannot construct", e);
                }
            }
        }
        return result;
    }
}
