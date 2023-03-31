# Annotation Side

The annotation side is an enum responsible to inform which side (source or target) of the classes
the AnMap must consider to process the annotation.

This is useful when you have both classes (source and target) annotated because different flows that can occurred in
your project, cause that, you can specify during the function of map which side of the mapper, source or target, must
have the annotation processed.

This feature solves the same problem that `@MapperConfig` solves, but with some limitations and a less extensive way.
Only specifying in the map method which side consider the annotation.

```
AnMap.map(source, Target.class, AnnotationSide.TARGET);
```

# Use Example

```java
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;

public class Class1 {
    private String name;
    private Integer age;

    public Source(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

public class Class2 {
    @Map("name")
    private String fullName;
    @Map("age")
    @FunctionMap(IntegerToStringFunctionMapper.class)
    private String ageText;
}

public class Class3 {
    @Map("fullName")
    private String completeName;
    @Map("ageText")
    private String actualAge;
}
```

In this example lets assume that in one flow the `Class1` have his data mapped to `Class2`, but in a different flow, you
need map `Class3` to `Class2` too, but the annotation must be set different in this case. Let's assume that you can't
change
`Class1` to solve the annotation conflict problem, cause that, you set `Class2` with annotation but these annotations
will conflict with the annotation in `Class3`, if you don't want to use the `@MapperConfig` annotation property, a
simple way to solve that is when you are
doing the map, inform to the AnMap which side process the annotations.

```java
import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.conf.AnnotationSide;

public class Example {
    Class2 sample(Class1 class1) {
        return AnMap.mapOrElseThrow(class1, Class2.class, AnnotationSide.TARGET);
    }

    Class2 sample(Class3 class3) {
        // this annotation side can be concealed cause by the default if both class
        // are annotated, the AnMap always process the Source Side.
        return AnMap.mapOrElseThrow(class3, Class2.class, AnnotationSide.SOURCE);
    }
}
```