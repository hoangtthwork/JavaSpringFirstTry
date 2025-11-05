package com.example.learn_java_spring.dto;

public class UserCreationDTO {
    private String username;
    private String password;
    private String email;
    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String u) { username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { password = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { email = e; }
}