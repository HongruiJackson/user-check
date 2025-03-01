package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.pojo.reflect.ReflectEntity;
import com.hruiworks.usercheck.support.ReflectCache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author JacksonZHR
 * 传参校验
 */
public class ParamUtils {

    /**
     * 校验传入参数，如果是字符串校验是否是空白，其他校验是否为null
     * @param params 传入的参数
     * @return 任一参数为空白或null返回true，否则为false
     */
    public static boolean isAnyBlank(Object... params) {
        for (Object param : params) {
            if (param instanceof String) {
                boolean blank = StringUtils.isBlank((String) param);
                if (BooleanUtils.isTrue(blank)) {
                    return true;
                }
            } else {
                boolean aNull = Objects.isNull(param);
                if (BooleanUtils.isTrue(aNull)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验传入参数，如果是字符串校验是否是空白，其他校验是否为null
     * @param object  传入的参数所封装成的类
     * @return 任一参数为空白或null返回true，否则为false
     */
    public static <T> boolean isAnyBlank(T object) {
        Class<T> aClass = (Class<T>) object.getClass();
        ReflectEntity<T> tReflectEntity = ReflectCache.get(aClass);
        Map<String, Method> fieldGetter = tReflectEntity.getFieldGetter();
        Set<String> fieldNameSet = fieldGetter.keySet();
        Object[] params = new Object[fieldGetter.size()];

        try {
            int index = 0;
            for (String fieldName : fieldNameSet) {
                Method getter = fieldGetter.get(fieldName);
                Object param = getter.invoke(object);
                params[index++] = param;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return isAnyBlank(params);
    }

    /**
     * 将对象当中的0长度字符串的字段转换为null，用于处理前端传空字符串的情况
     * @param object 传入的对象
     */
    public static <T> void toNull(T object) {
        Class<T> aClass = (Class<T>) object.getClass();
        ReflectEntity<T> tReflectEntity = ReflectCache.get(aClass);
        Map<String, Method> fieldGetter = tReflectEntity.getFieldGetter();
        Map<String, Method> fieldSetter = tReflectEntity.getFieldSetter();
        Set<String> fieldNameSet = fieldGetter.keySet();

        try {
            for (String fieldName : fieldNameSet) {
                Method getter = fieldGetter.get(fieldName);
                Object param = getter.invoke(object);
                if (param instanceof String) {
                    boolean blank = StringUtils.isBlank((String) param);
                    if (BooleanUtils.isTrue(blank)) {
                        fieldSetter.get(fieldName).invoke(object, (Object) null);
                    }
                }

            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
