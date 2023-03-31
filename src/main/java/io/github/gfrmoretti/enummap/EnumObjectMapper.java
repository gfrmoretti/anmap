package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.gfrmoretti.ReflectionUtils.getMethodThatParameterMatchesWithClass;

public class EnumObjectMapper extends EnumMapper {

    public EnumObjectMapper(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass) {
        super(annotation, sourceValue, sourceClass, targetClass);
    }

    @Override
    public Predicate<Object> predicate() {
        if (annotation.matchEnumMapByEnumValue())
            return aEnum -> {
                try {
                    var valueTarget = aEnum.getClass().getMethod(annotation.functionName()).invoke(aEnum);
                    return Objects.equals(
                            sourceClass.getMethod(annotation.functionName()).invoke(sourceValue),
                            applyFunctionConversion(valueTarget).orElseThrow()
                    );
                } catch (Exception e) {
                    return false;
                }
            };
        return anEnum -> anEnum.toString().equals(sourceValue.toString());
    }

    @Override
    public Optional<Object> getValueFromEnum() throws Exception {
        if (annotation.matchEnumMapByEnumValue())
            return Optional.ofNullable(
                    Arrays.stream((targetClass).getEnumConstants())
                            .filter(predicate())
                            .findFirst()
                            .orElse(null)
            );
        var valueOfMethod = targetClass.getMethod("valueOf", Class.class, String.class);
        var result = valueOfMethod.invoke(null, targetClass, sourceValue.toString());
        return Optional.ofNullable(applyFunctionConversion(result).orElse(null));
    }

    private Optional<?> applyFunctionConversion(Object result) throws Exception {
        var function = annotation.functionMapper().getDeclaredConstructor().newInstance();
        var method = getMethodThatParameterMatchesWithClass(function.getClass(), result.getClass());
        return (Optional<?>) method.invoke(function, result);
    }
}
