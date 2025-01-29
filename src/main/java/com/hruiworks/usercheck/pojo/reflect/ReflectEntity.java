package com.hruiworks.usercheck.pojo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JacksonZHR
 */
public class ReflectEntity<T> {

    /**
     * 全限定名
     */
    private String name;

    /**
     * 无参构造器
     */
    private Constructor<T> nonArgConstructor;

    /**
     * setter
     */
    private final Map<String, Method> fieldSetter  = new HashMap<>();

    /**
     * getter
     */
    private final Map<String, Method> fieldGetter = new HashMap<>();

    public void setFieldSetter(String fieldName, Method setter) {
        this.fieldSetter.put(fieldName, setter);
    }

    public void setFieldGetter(String fieldName, Method getter) {
        this.fieldGetter.put(fieldName, getter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constructor<T> getNonArgConstructor() {
        return nonArgConstructor;
    }

    public void setNonArgConstructor(Constructor<T> nonArgConstructor) {
        this.nonArgConstructor = nonArgConstructor;
    }

    public Map<String, Method> getFieldSetter() {
        return fieldSetter;
    }

    public Map<String, Method> getFieldGetter() {
        return fieldGetter;
    }
}
