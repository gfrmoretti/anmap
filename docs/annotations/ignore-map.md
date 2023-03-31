# Ignore Map Annotation (<span style='color: #A8C023'>@IgnoreMap</span>)

## Goal

This annotation has the goal to indicates a field to ignore, this way the mapper will not map this field.

## Why I need this?

When you are doing a map, you may found some situation where a field present in the two object is the same and can be
mapped, but you don't want that. You do not need that info for some reason and you need a way to prevent the mapper to
map this field. `IgnoreMap` annotation can do that for you.

This annotation make the AnMap ignores a field.

## Use example

```java
import io.github.gfrmoretti.annotations.IgnoreMap;

public class MapSample {
    @IgnoreMap
    private String name;
}
```

This example make this class (MapSample) to ignore the field name and do not map this value to target class.

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java
import io.github.gfrmoretti.annotations.IgnoreMap;

public class MapSample {
    private String name;
}

public class Target1 {
    @IgnoreMap
    private String name;
}

public class Target2 {
    @IgnoreMap
    private String name;
}

//the Target3 will get the value from the source MapSample
public class Target3 {
    private String name;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.MapperConfig;

public class MapSample {
    @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = MapSample, targetClass = Target1))
    @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = MapSample, targetClass = Target2))
    private String name;
}

public class Target1 {
    private String name;
}

public class Target2 {
    private String name;
}

//the Target3 will get the value from the source MapSample
public class Target3 {
    private String name;
}
```