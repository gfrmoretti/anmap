package io.github.gfrmoretti.custom;

import java.util.Optional;

public interface CustomMapper<Source, Target> {
    Optional<Target> map(Source source);
}
