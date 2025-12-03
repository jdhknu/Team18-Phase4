package com.team18.user;

public class User {
	private String userId;
    private String password;
    private String name;
    private String gender;
    private Integer age;
    private Double height;
    private Double weight;

    public User() {}

    public User(String userId, String password, String name,
            String gender, Integer age, Double height, Double weight) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.height = height;
    this.weight = weight;
    }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
}
