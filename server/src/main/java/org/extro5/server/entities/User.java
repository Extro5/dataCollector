package org.extro5.server.entities;

import java.util.Objects;

public class User {

    private String userId;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String email;

    private String gender;

    private String phone;

    private String age;

    private String hobby;

    private String profession;

    private String activityName;

    public User() {
    }

    public User(String userId, String activityName) {
        this.userId = userId;
        this.activityName = activityName;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId);
    }
}
