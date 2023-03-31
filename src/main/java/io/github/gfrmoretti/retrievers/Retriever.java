package io.github.gfrmoretti.retrievers;

import java.util.Optional;

public interface Retriever<T> {
    boolean canRetrieveValue();

    Optional<T> retrieve();
}
