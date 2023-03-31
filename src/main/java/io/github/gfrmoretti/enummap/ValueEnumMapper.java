package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.functionmap.DefaultFunctionMapper;

import java.util.Optional;
import java.util.function.Predicate;

import static io.github.gfrmoretti.ReflectionUtils.getMethodThatParameterMatchesWithClass;

public class ValueEnumMapper extends EnumMapper {
    public ValueEnumMapper(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass) {
        super(annotation, sourceValue, sourceClass, targetClass);
    }

    @Override
    public Predicate<Object> predicate() {
        return anEnum -> {
            try {
                return annotation.functionMapper().equals(DefaultFunctionMapper.class) ?
                        findDefault(anEnum) :
                        findWithFunction(anEnum);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private boolean findDefault(Object anEnum) throws Exception {
        return targetClass.getMethod(annotation.functionName()).invoke(anEnum).equals(sourceValue);
    }

    private boolean findWithFunction(Object anEnum) throws Exception {
        var function = annotation.functionMapper().getDeclaredConstructor().newInstance();
        var method = getMethodThatParameterMatchesWithClass(function.getClass(), sourceValue.getClass());
        var value = (Optional<?>) method.invoke(function, sourceValue);
        return targetClass.getMethod(annotation.functionName()).invoke(anEnum).equals(value.orElse(null));
    }

    private Optional<?> getValueFromFunction(Object valueToMap) throws Exception {
        var function = annotation.functionMapper().getDeclaredConstructor().newInstance();
        var method = getMethodThatParameterMatchesWithClass(function.getClass(), valueToMap.getClass());
        return (Optional<?>) method.invoke(function, valueToMap);
    }

    public Optional<Object> getValueFromEnum() throws Exception {
        return annotation.functionMapper().equals(DefaultFunctionMapper.class) ?
                Optional.ofNullable(sourceClass.getMethod(annotation.functionName()).invoke(sourceValue)) :
                Optional.ofNullable(getValueFromFunction(sourceClass.getMethod(annotation.functionName()).invoke(sourceValue))
                        .orElse(null));
    }
}
