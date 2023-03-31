# Implicit Map Annotation (<span style='color: #A8C023'>@ImplicitMap</span>)

## Goal

This annotation has the goal to make implicit maps, classes inside classes that can be mapped. This works with
collections too. (Set and List)

## Why I need this?

When you are mapping a class, you may have a class inside the class you want to map and the target class also have
one class inside but is not the same class, the fields are corresponding but the target context is different and this
make the target class inside different from the source. In this case, you need the `ImplicitMap` annotation to solve
this problem.

## Use example

```java
import io.github.gfrmoretti.annotations.ImplicitMap;

public class SubClass {
    private String name;
}

public class MapSample {
    @ImplicitMap
    private SubClass subClass;
}
```

This example make this class (MapSample) to map the subClass <b>SubClass</b> field to a subClass <b>SubClassTarget</b>
field in target class for example.

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java

import io.github.gfrmoretti.annotations.DateMap;

public class SubClass {
    private String name;
}

public class MapSample {
    private SubClass subClass;
}

public class SubClassTarget {
    private String name;
}

public class Target1 {
    @ImplicitMap
    private SubClassTarget subClass;
}

public class Target2 {
    @ImplicitMap
    private SubClassTarget subClass;
}

//the map will not work in Target3 class because does not have annotation.
public class Target3 {
    private SubClassTarget subClass;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.MapperConfig;

public class SubClass {
    private String name;
}

public class MapSample {
    @ImplicitMap(mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target1))
    @ImplicitMap(mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target2))
    private SubClass subClass;
}

public class SubClassTarget {
    private String name;
}

public class Target1 {
    private SubClassTarget subClass;
}

public class Target2 {
    private SubClassTarget subClass;
}

//the map will not work in Target3 class because mapper config is configured to this class.
public class Target3 {
    private SubClassTarget subClass;
}
```