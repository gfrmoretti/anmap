# AnMap (<span style="color:orange; font-weight:bolder">An</span>notation <span style="color:orange; font-weight:bolder">Map</span>per)

---


[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label)](https://https://maven.apache.org/)
[![Open Source? Yes!](https://badgen.net/badge/Open%20Source%20%3F/Yes%21/blue?icon=github)](https://github.com/Naereen/badges/)
[![Generic badge](https://img.shields.io/badge/docs-passing-<>.svg)](https://gfrmoretti.github.io/anmap/)
[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://gfrmoretti.github.io/anmap/javadoc/)

[![Coverage](/jacoco/badge/jacoco.svg)]()
[![Forks](https://img.shields.io/github/forks/gfrmoretti/anmap.svg)]()
[![Stars](https://img.shields.io/github/stars/gfrmoretti/anmap.svg)]()
[![Issues Open](https://img.shields.io/github/issues/gfrmoretti/anmap.svg)]()
[![Issues Closed](https://img.shields.io/github/issues-closed/gfrmoretti/anmap.svg)]()
[![Follow](https://img.shields.io/github/followers/gfrmoretti.svg?style=social&label=Follow&maxAge=2592000)]()

## Index

- [Objective](#objective)
- [Mapper Concept](#mapper-concept)
- [Current Existing Annotations](#current-annotations)
- [Inheritance](#inheritance)
- [Quick Start](#quick-start)
- [Learn More](#learn-more)

## Objective

This library is meant to be a mapper based with Java annotations, this way you don't need to configure nothing, only
import and use it. This is a need when you are working trying to isolate logical or critical classes from DTO or POJO's
for example. This can be useful when you are following architectures or concepts like clean arch, but this can use like
any map you want, works fine for simple or complex objects.

---

## Mapper Concept

The main concept to know about this mapper is the `source` and `target` concepts. To mapper any object to another
always exists the data source object and the target object. The library works considering this concepts.
Source is the object with the data to map and target is the object
to receive this data.

### Annotations

Annotations are useful for customizing mapper actions and telling AnMap what to do with that field when mapped.
They can be placed in both object, source or target, it is important to know that when you want to map
one object to another, all the annotations must be put only in one side of the relationship, for example,
if you put one annotation in the `source side`, you need to put all in the source side and vice-versa.

The lib auto-detect which side has the annotations, if is the `source` or `target`, but can be configured manually. The
auto-detection logic is
specified bellow.

### Auto-Detection Annotations Cases

- If both classes are annotated, `source` has the preference.
- If any classes are annotated, `source` has preference, but in
  these case doesn't matter, without annotation the lib will try to match
  field by field literally.
- If you need, you can specify on the map method which side use to process the annotations.
- If you need multiples mapper config to same object, exists one annotation to configure that (@MapperConfig)

---

## Current Annotations:

- [@Map](/docs/annotations/map.md): use to map fields with different names. Ex: **id** field to **uuid** field.
- [@IgnoreMap](/docs/annotations/ignore-map.md): use to ignore the field.
- [@FunctionMap](/docs/annotations/function-map.md): use to map a field using a function. Ex: you can convert a type to
  another like **Double** to
  **String**.
- [@DateMap](/docs/annotations/date-map.md): use to convert and format dates.
- [@EnumMap](/docs/annotations/enum-map.md): use to convert enums, simple enums can be mapped to string without
  annotation, but to map string to
  enum you will need this annotation.
- [@ImplicitMap](/docs/annotations/implicit-map.md): use to implicit map an object inside the current mapped object,
  this means that you can create a
  hierarchy between objects and call mapper on each element using this annotation.

---

## Inheritance

The library support Inheritance mapper, that means, if you have a source inheritance structure and the target class have
the same inheritance structure, the mapper can be made for all the fields. Also, the AnMap can compile all the
source inheritance fields to a single target class.

You can find some examples in `InheritanceMapperTest` class [here](/src/test/java/features/InheritanceMapperTest.java).

---

## Quick Start

### Installing with maven

```xml
<dependency>
  <groupId>io.github.gfrmoretti</groupId>
  <artifactId>an-map</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Simple map

```java

@AllArgsConstructor
@Getter
public class User {
    private @NotNull String login;
    private @NotNull String password;
    private @NotNull Integer remainingTrialDays;
    private @NotNull Status status;
    private @NotNull Address address;
}

@Data
public class InsertUserRequest {
    private @NotNull String login;
    private @NotNull String password;
    private @NotNull Integer remainingTrialDays;
    private @NotNull String status;
    private @NotNull Address address;
}

public class QuickStartTest {

    @Test
    @DisplayName("Quick Start: Simple map.")
    void simpleMap() {
        var user = new User("gm123456", "test", 15, Status.ENABLE,
                new Address("Street 14", 444, "Pan City")
        );

        var request = AnMap.map(user, InsertUserRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }
}

```

### Output

```
InsertUserRequest(login=gm123456, password=test, remainingTrialDays=15, status=ENABLE, address=Address(street=Street 14, number=444, city=Pan City))
```

### The Best of AnMap

```java

@AllArgsConstructor
@Data
public class House {
    private long id;
    @Map("nameOfOwner")
    private String ownerName;
    @ImplicitMap
    private List<Door> doors;
    @Map("valuationStatusRequest")
    @EnumMap(enumMapType = EnumMapType.MAP_ENUM)
    private ValuationStatus valuationStatus;
    @DateMap(formatPattern = "yyyy-MM-dd")
    private Instant acquisitionDate;
    @FunctionMap(BigDecimalToDoubleFunctionMapper.class)
    private BigDecimal value;
}

@AllArgsConstructor
@Data
public class Door {
    private int height;
    private int width;
}

public enum ValuationStatus {
    HIGH,
    MID,
    DOWN
}

// ##### TARGET CLASSES #####

@AllArgsConstructor
@Data
public class HouseRequest {
    private long id;
    private String nameOfOwner;
    private List<DoorRequest> doors;
    private ValuationStatusRequest valuationStatusRequest;
    private String acquisitionDate;
    private double value;
}

@AllArgsConstructor
@Data
public class DoorRequest {
    private int height;
    private int width;
}

public enum ValuationStatusRequest {
    HIGH,
    MID,
    DOWN
}

public class QuickStartTest {

    @Test
    @DisplayName("Quick Start: Best of AnMap")
    void bestOf() {
        var doors = List.of(new Door(120, 130), new Door(120, 90));
        var house = new House(123, "owner", doors, ValuationStatus.HIGH, Instant.now(), new BigDecimal(10000));

        var request = AnMap.map(house, HouseRequest.class).orElse(null);

        assertNotNull(request);
        System.out.println(request);
    }
}

```

### Output

```
HouseRequest(id=123, nameOfOwner=owner, doors=[DoorRequest(height=120, width=130), DoorRequest(height=120, width=90)], valuationStatusRequest=HIGH, acquisitionDate=2023-03-28, value=10000.0)
```

---

### More examples

All these samples can be found in the `quickstart` folder inside the `test` directory, also you can use all the test in
features to learn how to use all the features that this library can do it, these tests are located in the
`features` folder inside the `test` directory.

## Learn more

### Learn more about the annotations and how to use.

- [Map](/docs/annotations/map.md)
- [FunctionMap](/docs/annotations/function-map.md)
- [DateMap](/docs/annotations/date-map.md)
- [EnumMap](/docs/annotations/enum-map.md)
- [ImplicitMap](/docs/annotations/implicit-map.md)
- [IgnoreMap](/docs/annotations/ignore-map.md)

### Learn some important concepts to understand some behaviors.

- [Constructors](/docs/constructors.md)
- [ReferenceMap](/docs/reference-map.md)
- [ErrorHandling](/docs/error-handling.md)
- [AnnotationSide](/docs/annotation-side.md)

### JavaDoc

- [Javadoc](https://gfrmoretti.github.io/anmap/javadoc/)