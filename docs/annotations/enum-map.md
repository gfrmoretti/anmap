# Enum Map Annotation (<span style='color: #A8C023'>@EnumMap</span>)

## Goal

This annotation goal is to make enum mapping. This means, this annotation will give to you some options to extract a
value from enum or convert a value to an enum.

## Why I need this?

When you are mapping a class, you may have an Enum Object in your class. This can make the things difficult. Not with
this annotation. The Enum Map can simplify enum conversion in some way that allow you to extract any value from your
enum.

The follow options are supported by the library:

- Simple enum name conversion to String (MAP_STRING)
- Ordinal enum conversion to int or String (MAP_ORDINAL)
- Enum to Enum with same values (MAP_ENUM)
- Enum value conversion to any value using `FunctionMapper` (MAP_VALUE)

## Use example

Enum used in examples:

```java
public enum Status {
    SUCCESS(200, "Success"),
    ERROR(500, "Error");

    private final int code;
    private final String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
```

### MAP_STRING

```java
import io.github.gfrmoretti.annotations.EnumMap;

public class MapSample {
    @EnumMap
    private Status status = Status.SUCCESS;
}

public class Target {
    private String status;
}
```

### Result Output

By default, the simple annotation will map the enum to a String with the name of the enum.
Example:

```
Target(status = "SUCCESS")
```

### MAP_ORDINAL (ordinal to string)

```java
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.enummap.EnumMappingType;

public class MapSample {
    @EnumMap(enumMapType = EnumMapType.MAP_ORDINAL)
    private Status status = Status.SUCCESS;
}

public class Target {
    private String status;
}
```

### Result Output

```
Target(status = "0")
```

### MAP_ORDINAL (ordinal to int)

```java
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;

public class MapSample {
    @EnumMap(enumMapType = EnumMapType.MAP_ORDINAL, mustMapOrdinalToInt = true)
    private Status status = Status.SUCCESS;
}

public class Target {
    private int status;
}
```

### Result Output

```
Target(status = 0)
```

### MAP_ENUM

```java
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.enummap.EnumMappingType;

public class MapSample {
    @EnumMap(enumMapType = EnumMapType.MAP_ENUM)
    private Status status = Status.SUCCESS;
}

public enum ResponseStatus {
    SUCCESS,
    ERROR
}

public class Target {
    private ResponseStatus status;
}
```

### Result Output

```
Target(status = ResponseStatus.SUCCESS)
```

### MAP_VALUE

Important thing to know is if you enum has a function named `getValue`, you don't need to inform
the name of the function in enum to get the value, by default the mapper will search by the `getValue` function 
inside the enum.

```java
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;

public class MapSample {
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE, functionName = "getDescription")
    private Status status = Status.SUCCESS;
}

public class Target {
    private String status;
}
```

### Result Output

```
Target(status = "Success")
```


### MAP_VALUE (With Conversion)

```java
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;

public class MapSample {
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE,
            functionName = "getCode",
            functionMapper = IntegerToStringFunctionMapper.class)
    private Status status = Status.SUCCESS;
}

public class Target {
    private String status;
}
```

### Result Output

```
Target(status = "200")
```

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;

public class MapSample {
    private Status status;
}

public class Target1 {
    @EnumMap
    private String status;
}

public class Target2 {
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE, functionName = "getCode")
    private int status;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.enummap.EnumMappingType;

public class MapSample {
    @EnumMap(mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target1.class))
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE,
            functionName = "getCode",
            mapperConfig = @MapperConfig(sourceClass = MapSample.class, targetClass = Target2.class))
    private Status status;
}

public class Target1 {
    private String status;
}

public class Target2 {
    private int status;
}
```