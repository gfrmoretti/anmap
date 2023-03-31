# Constructors

This page objective is to let you know how AnMap create a `TargetClass`.

In order to create a class by reflection the AnMap will search in the TargetClass all the
declared constructors and the logic is the follow one:

*TRY TO CREATE IF ERROR TRY THE NEXT ONE*

*IF NO ONE IS SUCCESSFUL TRY TO CREATE WITHOUT PARAMS*

*IF FAIL RETURN OPTIONAL EMPTY*

## Matching constructors parameters with source fields.

The AnMap will follow the same logic and respect the annotation inside the class to match
the constructor fields with the source fields. For example, if you have an annotation to convert a value
and the value is needed in constructor, the AnMap will convert first and give to the constuctor after.

## Example

```java
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;

public class Source {
    @FunctionMap(LongToStringFunctionMapper.class)
    private Long id;
    @Map("fullName")
    private String name;
}

public class Target {
    private String id;
    private String fullName;

    public Target(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}

public class Test {
    @Test
    void constructorTest() {
        System.out.println(AnMap.mapOrElseThrow(new Source(123L, "test"), Target.class));
    }
}
```

## Output Conversion

```
Test.Target(id=123, fullName=test)
```