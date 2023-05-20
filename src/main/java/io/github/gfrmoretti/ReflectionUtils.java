package io.github.gfrmoretti;

import io.github.gfrmoretti.functionmap.DefaultFunctionMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * Returns a list containing one parameter name for each argument accepted
     * by the given constructor. If the class was compiled with debugging
     * symbols, the parameter names will match those provided in the Java source
     * code. Otherwise, a generic "arg" parameter name is generated ("arg0" for
     * the first argument, "arg1" for the second...).
     * <p>
     * This method relies on the constructor's class loader to locate the
     * bytecode resource that defined its class.
     *
     * @param constructor constructor to get parameter names.
     * @return list of parameter names.
     * @throws IOException If something fails.
     */
    public static List<String> getParameterNames(Constructor<?> constructor) throws IOException {
        var declaringClass = constructor.getDeclaringClass();
        var declaringClassLoader = declaringClass.getClassLoader();

        var declaringType = Type.getType(declaringClass);
        var constructorDescriptor = Type.getConstructorDescriptor(constructor);
        var url = declaringType.getInternalName() + ".class";

        var classFileInputStream = declaringClassLoader.getResourceAsStream(url);

        if (classFileInputStream == null)
            throw new IllegalArgumentException("The constructor's class loader cannot find the bytecode that " +
                    "defined the constructor's class (URL: " + url + ")");


        ClassNode classNode;
        try {
            classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classFileInputStream);
            classReader.accept(classNode, 0);
        } finally {
            classFileInputStream.close();
        }

        var methods = classNode.methods;
        for (MethodNode method : methods) {
            if (method.name.equals("<init>") && method.desc.equals(constructorDescriptor)) {
                Type[] argumentTypes = Type.getArgumentTypes(method.desc);
                List<String> parameterNames = new ArrayList<>(argumentTypes.length);

                List<LocalVariableNode> localVariables = method.localVariables;
                for (int i = 0; i < argumentTypes.length; i++) {
                    // The first local variable actually represents the "this" object
                    parameterNames.add(localVariables.get(i + 1).name);
                }

                return parameterNames;
            }
        }

        return Collections.emptyList();
    }

    public static Field[] getFieldsJoinSuperClass(Class<?> classToJoin) {
        if (classToJoin.getSuperclass().equals(Object.class))
            return classToJoin.getDeclaredFields();

        var superClass = classToJoin.getSuperclass();
        var fields = getFieldsJoinSuperClass(superClass);
        return concat(fields, classToJoin.getDeclaredFields());
    }

    /**
     * Concatenate N array into a single.
     *
     * @param array1 First array.
     * @param array2 Second array.
     * @return Optional of bytes array concatenated.
     */
    @NotNull
    private static <T> T[] concat(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static Method getMethodThatParameterMatchesWithClass(Class<?> classToSearch, Class<?> parameterType) throws NoSuchMethodException {
        if (classToSearch.equals(DefaultFunctionMapper.class)) {
            return DefaultFunctionMapper.class.getMethod("mapValue", Object.class);
        }
        for (var declaredMethod : classToSearch.getDeclaredMethods()) {
            var classToCompare = parameterType;
            if (parameterType.isPrimitive())
                classToCompare = getClassFromPrimitive(parameterType);
            if (declaredMethod.getParameterTypes()[0] == classToCompare)
                return declaredMethod;
        }
        log.debug("Function for class {} method not found.", parameterType.getSimpleName());
        throw new NoSuchMethodException("Corresponding method not found.");
    }

    public static Class<?> getClassFromPrimitive(Class<?> primitiveClass) {
        var map = new HashMap<Class<?>, Class<?>>();
        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
        return map.get(primitiveClass);
    }
}
