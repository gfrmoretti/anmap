# Function Map Annotation (<span style='color: #A8C023'>@FunctionMap</span>)

## Goal

This annotation has the goal to give to the user a way to pass a conversion function to map
the source field value to the target field value and vice-versa.

## Why I need this?

When you are mapping a class, maybe the classes of the attributes (fields) are different. The default behavior
of the mapper in this situation is fail to map and let the field with null value, if the field is not null the mapper
will
return an empty optional. This annotation make the AnMap convert a field to another.

## Use example

```java
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;

public class MapSample {
    @FunctionMap(IntegerToStringFunctionMapper.class)
    private String id;
}
```

This example make this class (MapSample) to map the id field value to id field integer value in target class.

## Converter Function

```java
public class IntegerToStringFunctionMapper implements FunctionMapper<Integer, String> {

    @Override
    public Optional<String> mapValue(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.of(integer.toString());
    }

    @Override
    public Optional<Integer> inverseMapValue(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (Exception e) {
            log.warn("Problem to convert to integer", e);
            return Optional.empty();
        }
    }
}
```

You can create your own personalize `FunctionMapper` to make a conversion inside your object. You just need to
implement the `FunctionMapper<F,S>` interface passing the two values that you will convert. The order is not important.

## Current FunctionMapper

The library already gives to you some standard `FunctionMapper` that can be used in your projects.
The functions are bellow:

- BooleanToStringFunctionMapper (Converts boolean values to String and vice-versa)
- DoubleToStringFunctionMapper (Converts double values to String and vice-versa)
- IntegerToStringFunctionMapper (Converts int values to String and vice-versa)
- LongToStringFunctionMapper (Converts long values to String and vice-versa)
- FloatToStringFunctionMapper (Converts float values to String and vice-versa)
- ShortToStringFunctionMapper (Converts short values to String and vice-versa)
- BigDecimalToStringFunctionMapper (Converts BigDecimal values to String and vice-versa)
- BigDecimalToDoubleFunctionMapper (Converts BigDecimal values to Double and vice-versa)

## Mapping more than one class

Examples with more than on class.

### With Annotations in Target Classes

```java

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;

public class MapSample {
    private String id;
}

public class Target1 {
    @FunctionMap(IntegerToStringFunctionMapper.class)
    private Integer id;
}

public class Target2 {
    @FunctionMap(LongToStringFunctionMapper.class)
    private Long id;
}
```

### With MapperConfig

All the annotations have a mapper configuration. The annotations need this configuration to personalize the
source and target that is valid to that annotation.

```java

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;
import io.github.gfrmoretti.annotations.MapperConfig;

public class MapSample {
    @FunctionMap(value = IntegerToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target1.class))
    @FunctionMap(value = LongToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
    private String id;
}

public class Target1 {
    private Integer id;
}

public class Target2 {
    private Long id;
}
```