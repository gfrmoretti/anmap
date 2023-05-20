# Constructor Map Annotation (<span style='color: #A8C023'>@ConstructorMap</span>)

## Goal

This annotation has the goal to inform to the mapper which constructor use to create a
`target` class and specify order of priority and if this constructor accept null values.

## Why I need this?

The **most important** thing to know is that by default the mapper ignore null fields
in source to ensure the best matching of the fields during the map action. Knowing that,
the constructor use the same structure to match the fields and create a target object, but
sometimes you will need create a target object that accepts null values in constructor for
some reasons.

A reason example can be: I have only one way to construct the target object,
and it is using all args, but the source field that I am trying to map does not have all
args and this is normal in my case. The default behavior AnMap will ignore values
not found (null values) and will fail matching the constructor by args.
This example is a great case that you need accept null values in your constructor, and this
annotation do it for you.

Other reason to use this, is when you need specify a constructor priority between all
the constructor in the target class.

## Use example

```java
import io.github.gfrmoretti.annotations.ConstructorMap;

public class MapSample {
    private String id;
    private String name;
    private String additionalInfo;

    @ConstructorMap(acceptNullValues = true)
    public MapSample(String id, String name, String additionalInfo) {
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
    }

    @ConstructorMap(priority = 1)
    public MapSample(String name) {
        this.id = null;
        this.name = name;
        this.additionalInfo = null;
    }

    public MapSample(DomainObject obj) {
        this.id = obj.id;
        this.name = obj.name;
        this.additionalInfo = obj.additionalInfo;
    }
}
```

This example make AnMap to try to create first the annotated constructor in order
by the priority, so `zero priority` is the first attempt (by default the priority is zero).
And this constructor is allowed to get null values from the source to match the constructor args.
If fails will try the next annotated constructor with priority `one` in these case.
If fails will try the unannotated constructor.

If all priorities is zeros, the order of creation can be any one, but is certainly that the AnMap will try
to create first, all annotated constructors.

## Does Not Support MapperConfig

Actually, this annotation does not have a support to the `@MapperConfig` annotation
to use in multiples classes configuration like the others annotations.