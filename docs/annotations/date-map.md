# Date Map Annotation (<span style='color: #A8C023'>@DateMap</span>)

## Goal

This annotation has the goal to format field there are dates to string and vice-versa.

## Why I need this?

When you are mapping a class, maybe you have a date field, and you want to convert to string with a specific pattern.
In this situation without an annotation the mapper will fail let the field with null value.

The difference between `this` annotation and the `@FunctionMap` is that this annotation already knows how to format the
date in UTC time to any Temporal Object following the given pattern, this make this annotation more simple and dynamic
to use, because you can provide the pattern that you need and convert to a temporal class that you want.

Current the library suports the follow Temporal Classes:
 - Instant
 - LocalDate
 - LocalDateTime
 - ZonedDateTime

This annotation make the AnMap convert a string to date and vice-versa.

## Use example

```java
import io.github.gfrmoretti.annotations.DateMap;

public class MapSample {
    @DateMap(formatPattern = "yyyy-MM-dd")
    private String createdAt;
}
```

This example make this class (MapSample) to map the createdAt <b>String</b> field to a createdAt <b>Instant</b> field
in target class.

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java

import io.github.gfrmoretti.annotations.DateMap;

import java.time.Instant;
import java.time.LocalDate;

public class MapSample {
    private String createdAt;
}

public class Target1 {
    @DateMap(formatPattern = "yyyy-MM-dd")
    private Instant createdAt;
}

public class Target2 {
    @DateMap(formatPattern = "dd/MM/yyyy")
    private LocalDate createdAt;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.MapperConfig;

import java.time.LocalDate;

public class MapSample {
    @DateMap(formatPattern = "yyyy-MM-dd", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target1.class))
    @DateMap(formatPattern = "dd/MM/yyyy", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
    private String createdAt;
}

public class Target1 {
    private Instant createdAt;
}

public class Target2 {
    private LocalDate createdAt;
}
```