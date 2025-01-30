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
                reflectEntity.setFieldSetter(fieldName,method);
            }

            // getter
            List<Method> getterList = Arrays.stream(targetClass.getMethods()).filter(method -> method.getName().startsWith("get")).toList();
            for (Method method : getterList) {
                String substring = method.getName().substring("get".length());
                String fieldName = Character.toLowerCase(substring.charAt(0)) + substring.substring(1);
                reflectEntity.setFieldGetter(fieldName,method);
            }

            reflectCacheHashMap.put(className, reflectEntity);

            return reflectEntity;

        } else {
            // 有缓存强转
            try {
                return  (ReflectEntity<T>) originalReflectEntity;
            } catch (Exception e) {
                throw new UserCheckException(ReflectCacheExceptionEnum.WRONG_TYPE.getMsg());
            }
        }
    }
}
