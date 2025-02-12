package com.hruiworks.usercheck.support;

import com.hruiworks.usercheck.enums.ReflectCacheExceptionEnum;
import com.hruiworks.usercheck.exception.UserCheckException;
import com.hruiworks.usercheck.pojo.reflect.ReflectEntity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReflectCache {

    /**
     * key: 类全限定名
     * value: 缓存的类方法
     */
    private static final HashMap<String, ReflectEntity<?>> reflectCacheHashMap = new HashMap<>();


    public static <T> ReflectEntity<T> get(Class<T> targetClass) {
        String className = targetClass.getName();

        ReflectEntity<?> originalReflectEntity = reflectCacheHashMap.get(className);

        // 查看有没有缓存
        if (Objects.isNull(originalReflectEntity)) {
            ReflectEntity<T> reflectEntity = new ReflectEntity<>();
            // 全限定名
            reflectEntity.setName(className);
            // 无参构造器
            try {
                reflectEntity.setNonArgConstructor(targetClass.getDeclaredConstructor());
            } catch (NoSuchMethodException e) {
                throw new UserCheckException(ReflectCacheExceptionEnum.NO_NON_ARG_CONSTRUCTOR.getMsg());
            }
            // setter
            List<Method> setterList = Arrays.stream(targetClass.getMethods()).filter(method -> method.getName().startsWith("set")).toList();
            for (Method method : setterList) {
                String substring = method.getName().substring("set".length());
                String fieldName = Character.toLowerCase(substring.charAt(0)) + substring.substring(1);
                reflectEntity.setFieldSetter(fieldName, method);
            }

            // getter
            List<Method> getterList = Arrays.stream(targetClass.getMethods()).filter(method -> method.getName().startsWith("get")).toList();
            for (Method method : getterList) {
                String substring = method.getName().substring("get".length());
                String fieldName = Character.toLowerCase(substring.charAt(0)) + substring.substring(1);
                reflectEntity.setFieldGetter(fieldName, method);
            }

            reflectCacheHashMap.put(className, reflectEntity);

            return reflectEntity;

        } else {
            // 有缓存强转
            try {
                return (ReflectEntity<T>) originalReflectEntity;
            } catch (Exception e) {
                throw new UserCheckException(ReflectCacheExceptionEnum.WRONG_TYPE.getMsg());
            }
        }
    }

    /**
     * 将值转换为目标类型
     *
     * @param value      原始值
     * @param targetType 目标类型
     * @return 转换后的值
     */
    public static Object convertValueToType(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        // 如果值已经是目标类型，直接返回
        if (targetType.isInstance(value)) {
            return value;
        }

        // 根据目标类型进行转换
        if (targetType == String.class) {
            return value.toString();
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value.toString());
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(value.toString());
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(value.toString());
        } else if (targetType == Short.class || targetType == short.class) {
            return Short.parseShort(value.toString());
        } else if (targetType == Byte.class || targetType == byte.class) {
            return Byte.parseByte(value.toString());
        } else if (targetType == Character.class || targetType == char.class) {
            return value.toString().charAt(0);
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
        }
    }
}
