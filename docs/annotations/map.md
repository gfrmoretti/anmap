# Map Annotation (<span style='color: #A8C023'>@Map</span>)

## Goal

This annotation goal is to link a field to another one with a different name.

## Why I need this?

When you are mapping a class, maybe the name of the attributes (fields) is different. The default behavior
of the mapper is match one field with other by the name. This annotation make the AnMap match the field
with the value passed to this annotation.

## Use example

```java
import io.github.gfrmoretti.annotations.Map;

public class MapSample {
    @Map("uuid")
    private String id;
}
```

This example make this class (MapSample) to map the id field value with the uuid field value from another class.

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java

import io.github.gfrmoretti.annotations.Map;

public class MapSample {
    private String id;
}

public class Target1 {
    @Map("id")
    private String uuid;
}

public class Target2 {
    @Map("id")
    private String guid;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java

import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.MapperConfig;

public class MapSample {
    @Map(value = "uuid", mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target1.class))
    @Map(value = "guid", mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target2.class))
    private String id;
}

public class Target1 {
    private String uuid;
}

public class Target2 {
    private String guid;
}
```