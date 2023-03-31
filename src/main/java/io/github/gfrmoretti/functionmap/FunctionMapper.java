package io.github.gfrmoretti.functionmap;

import java.util.Optional;

public interface FunctionMapper<F, S> {

    Optional<S> mapValue(F value);

    Optional<F> inverseMapValue(S value);
}
