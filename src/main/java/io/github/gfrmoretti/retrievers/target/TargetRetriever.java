package io.github.gfrmoretti.retrievers.target;

import io.github.gfrmoretti.retrievers.Retriever;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
abstract class TargetRetriever<Target> implements Retriever<Target> {
    protected final Constructor<?>[] constructors;
    protected final TargetFactory<?, Target> factory;

}
