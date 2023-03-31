package io.github.gfrmoretti;

import io.github.gfrmoretti.conf.AnnotationSide;
import io.github.gfrmoretti.custom.CustomMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AnMap (Annotation Mapper)
 * <p>
 * This class represents the lib itself, because all map actions must be trigger here.
 * This class has all map actions possibles for user to use.
 */
@Slf4j
public class AnMap {

    /**
     * Map any class to any target since the field can be mapped.
     *
     * @param source      instance of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link Optional} with the instance of the Target class with the data get from the source, or an empty
     * optional if something fail to map.
     */
    public static <Source, Target> Optional<Target> map(@Nullable Source source, @Nullable Class<Target> targetClass) {
        return Mapper.map(source, targetClass, AnnotationSide.AUTO_DETECT);
    }

    /**
     * Map any class to any target since the field can be mapped, you can specify which side (Source or Target) the
     * AnMap must consider to process the annotations.
     *
     * @param source      instance of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param side        the {@link AnnotationSide} to process the annotations.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link Optional} with the instance of the Target class with the data get from the source, or an empty
     * optional if something fail to map.
     */
    public static <Source, Target> Optional<Target> map(@Nullable Source source, @Nullable Class<Target> targetClass,
                                                        @NotNull AnnotationSide side) {
        return Mapper.map(source, targetClass, side);
    }

    /**
     * Map any class to any target since the field can be mapped.
     *
     * @param source      instance of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An instance of the Target class with the data get from the source or will throw a NoSuchElementException
     * – if no value is present.
     * @throws java.util.NoSuchElementException if AnMap can not map the class.
     */
    public static <Source, Target> Target mapOrElseThrow(@Nullable Source source, @Nullable Class<Target> targetClass) {
        return Mapper.map(source, targetClass, AnnotationSide.AUTO_DETECT).orElseThrow();
    }

    /**
     * Map any class to any target since the field can be mapped, you can specify which side (Source or Target) the
     * AnMap must consider to process the annotations. You can specify which side (Source or Target) the
     * AnMap must consider to process the annotations.
     *
     * @param source      instance of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param side        the {@link AnnotationSide} to process the annotations.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An instance of the Target class with the data get from the source or will throw a NoSuchElementException
     * – if no value is present.
     * @throws java.util.NoSuchElementException if AnMap can not map the class.
     */
    public static <Source, Target> Target mapOrElseThrow(@Nullable Source source, @Nullable Class<Target> targetClass,
                                                         @NotNull AnnotationSide side) {
        return Mapper.map(source, targetClass, side).orElseThrow();
    }

    /**
     * Map by reference any class to any target since the field can be mapped. This method, update the non-null fields
     * in reference target, using this, the AnMap do not need to create a target instance, it already received the
     * instance and put the non-null fields from source in the target instance.
     *
     * @param source   instance of the class with the data source to be transfer to target instance.
     * @param target   a target instance that you want to receive the source data.
     * @param <Source> type of the source data class.
     * @param <Target> type of the target to map.
     * @throws java.util.NoSuchElementException if AnMap can not map the class.
     */
    public static <Source, Target> void mapReference(@Nullable Source source, @Nullable Target target) {
        if (source == null || target == null)
            throw new IllegalArgumentException("Source or target can not be nulls.");
        Mapper.map(source, target, AnnotationSide.AUTO_DETECT).orElseThrow();
    }

    /**
     * Map by reference any class to any target since the field can be mapped. This method, update the non-null fields
     * in reference target, using this, the AnMap do not need to create a target instance, it already received the
     * instance and put the non-null fields from source in the target instance. You can specify which side
     * (Source or Target) the AnMap must consider to process the annotations.
     *
     * @param source   instance of the class with the data source to be transfer to target instance.
     * @param target   a target instance that you want to receive the source data.
     * @param side     the {@link AnnotationSide} to process the annotations.
     * @param <Source> type of the source data class.
     * @param <Target> type of the target to map.
     * @throws java.util.NoSuchElementException if AnMap can not map the class.
     */
    public static <Source, Target> void mapReference(@Nullable Source source,
                                                     @Nullable Target target,
                                                     @NotNull AnnotationSide side) {
        if (source == null || target == null)
            throw new IllegalArgumentException("Source or target can not be nulls.");
        Mapper.map(source, target, side).orElseThrow();
    }

    /**
     * This method make a custom map using the interface {@link CustomMapper}. When you want to full personalize the
     * mapper and still using the library, you have this method to do that.
     *
     * @param source       instance of the class with the data source to be transfer to target instance.
     * @param customMapper an instance of the custom mapper interface.
     * @param <Source>     type of the source data class.
     * @param <Target>     type of the target to map and return.
     * @return An {@link Optional} with the instance of the Target class with the data get from the source, or an empty
     * optional if something fail to map.
     */
    public static <Source, Target> Optional<Target> mapCustom(@Nullable Source source,
                                                              @Nullable CustomMapper<Source, Target> customMapper) {
        if (customMapper == null || source == null)
            return Optional.empty();
        return customMapper.map(source);
    }

    /**
     * Map a list of anything to a list of any target since the field can be mapped.
     *
     * @param source      list of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link List} with all the instance of the Target class with the data get from the source, or an empty
     * list if something fail to map.
     */
    public static <Source, Target> List<Target> mapList(@Nullable List<Source> source,
                                                        @Nullable Class<Target> targetClass) {
        return mapCollection(source, targetClass).collect(Collectors.toList());
    }

    /**
     * Map a list of anything to a list of any target since the field can be mapped. You can specify which side
     * (Source or Target) the AnMap must consider to process the annotations.
     *
     * @param source      list of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param side        the {@link AnnotationSide} to process the annotations.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link List} with all the instance of the Target class with the data get from the source, or an empty
     * list if something fail to map.
     */
    public static <Source, Target> List<Target> mapList(@Nullable List<Source> source,
                                                        @Nullable Class<Target> targetClass,
                                                        @NotNull AnnotationSide side) {
        return mapCollection(source, targetClass, side).collect(Collectors.toList());
    }

    /**
     * Map a set of anything to a set of any target since the field can be mapped.
     *
     * @param source      set of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link Set} with all the instance of the Target class with the data get from the source, or an empty
     * set if something fail to map.
     */
    public static <Source, Target> Set<Target> mapSet(@Nullable Collection<Source> source,
                                                      @Nullable Class<Target> targetClass) {
        return mapCollection(source, targetClass).collect(Collectors.toSet());
    }

    /**
     * Map a set of anything to a set of any target since the field can be mapped. You can specify which side
     * (Source or Target) the AnMap must consider to process the annotations.
     *
     * @param source      set of the class with the data source to be transfer to target class.
     * @param targetClass a target {@link Class} that you want to receive the source data.
     * @param side        the {@link AnnotationSide} to process the annotations.
     * @param <Source>    type of the source data class.
     * @param <Target>    type of the target to map and return.
     * @return An {@link Set} with all the instance of the Target class with the data get from the source, or an empty
     * set if something fail to map.
     */
    public static <Source, Target> Set<Target> mapSet(@Nullable Collection<Source> source,
                                                      @Nullable Class<Target> targetClass,
                                                      @NotNull AnnotationSide side) {
        return mapCollection(source, targetClass, side).collect(Collectors.toSet());
    }

    /**
     * Map a collection of anything to a collection of any target since the field can be mapped.
     *
     * @param source             collection of the class with the data source to be transfer to target class.
     * @param targetClass        a target {@link Class} that you want to receive the source data.
     * @param <Source>           type of the source data class.
     * @param <Target>           type of the target to map and return.
     * @param <CollectionSource> type of the collection of the source.
     * @return An {@link Stream} with all the instance of the Target class with the data get from the source, or
     * an empty collection if something fail to map.
     */
    public static <Source, Target, CollectionSource extends Collection<Source>> Stream<Target> mapCollection(
            @Nullable CollectionSource source,
            @Nullable Class<Target> targetClass) {
        if (source == null || targetClass == null)
            return Stream.empty();
        return source.stream().map(s -> map(s, targetClass).orElse(null))
                .filter(Objects::nonNull);
    }

    /**
     * Map a collection of anything to a collection of any target since the field can be mapped. You can specify which
     * side (Source or Target) the AnMap must consider to process the annotations.
     *
     * @param source             collection of the class with the data source to be transfer to target class.
     * @param targetClass        a target {@link Class} that you want to receive the source data.
     * @param side               the {@link AnnotationSide} to process the annotations.
     * @param <Source>           type of the source data class.
     * @param <Target>           type of the target to map and return.
     * @param <CollectionSource> type of the collection of the source.
     * @return An {@link Stream} with all the instance of the Target class with the data get from the source, or an
     * empty collection if something fail to map.
     */
    public static <Source, Target, CollectionSource extends Collection<Source>> Stream<Target> mapCollection(
            @Nullable CollectionSource source,
            @Nullable Class<Target> targetClass,
            @NotNull AnnotationSide side) {
        if (source == null || targetClass == null)
            return Stream.empty();
        return source.stream().map(s -> map(s, targetClass, side).orElse(null))
                .filter(Objects::nonNull);
    }

}
