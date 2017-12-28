package com.cong.chenchong.bean;

public class Girl {
    private String name;
    private Integer age;
    private Integer photo;

    public Girl(String name, Integer age, Integer photo) {
        this.name = name;
        this.age = age;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }
}
