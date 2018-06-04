package com.example.datacollector.model.request_object;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("fatherName")
    private String fatherName;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("phone")
    private String phone;

    @SerializedName("age")
    private String age;

    @SerializedName("hobby")
    private String hobby;

    @SerializedName("profession")
    private String profession;


    public User(String firstName, String lastName, String fatherName, String email, String gender, String phone, String age, String hobby, String profession) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.age = age;
        this.hobby = hobby;
        this.profession = profession;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}