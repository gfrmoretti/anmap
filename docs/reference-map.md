# Reference map

The library can make the same map using a reference (Object) as `Target`.

## When to use.

This feature give you dynamism to make the map. For example, you have an object with
some fields and wants update some values. You can use this feature to do that.

```java
import io.github.gfrmoretti.AnMap;

public class Source {
    private String name;
    private String age;

    public Source(String name, String age) {
        this.age = age;
        this.name = name;
    }
}

public class Target {
    private String name;
    private String age;
    //already have this info
    private int order;
    //already have this info
    private String register;
}

public class Test {
    @Test
        // received target to update with order and register field with value
    void test(Target target) {
        var source = new Source("name", "age");
        System.out.println(AnMap.mapReference(source, target));
    }
}
```

## Output
```
   Test.Target(name=name, age=age, order=1, register=test)
```

In the example above, the AnMap only update the field with values in source to the target. 
If you have a null field in source and the valid value in target field, when you map by reference the AnMap will 
prevent the overriding of the valid value by a null. By default, null fields in source are not set in target, this 
make this reference map a great option to update object that already exists with some fields values and you want to
optimize the update using this library.