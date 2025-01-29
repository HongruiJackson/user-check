package com.hruiworks.usercheck.testEntity;

/**
 * @author JacksonZHR
 */
public class User {

    private String nameOne;

    private Integer age;

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "nameOne='" + nameOne + '\'' +
                ", age=" + age +
                '}';
    }
}
