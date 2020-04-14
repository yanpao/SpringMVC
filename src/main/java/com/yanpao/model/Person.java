package com.yanpao.model;

import com.fasterxml.jackson.annotation.JsonView;

public class Person {

    public interface WithBaseInfo{};
    public interface WithSensitiveInfo{};
    public interface WithAllInfo extends WithSensitiveInfo,WithBaseInfo{};

    @JsonView(WithBaseInfo.class)
    Integer id;

    @JsonView(WithBaseInfo.class)
    String name;

    @JsonView(WithBaseInfo.class)
    String sex;

    @JsonView(WithSensitiveInfo.class)
    String identify;

    @JsonView(WithSensitiveInfo.class)
    String phonenumber;

    public Person(Integer id, String name, String sex, String identify, String phonenumber) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.identify = identify;
        this.phonenumber = phonenumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}