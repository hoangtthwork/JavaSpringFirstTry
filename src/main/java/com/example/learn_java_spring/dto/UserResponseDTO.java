package com.example.learn_java_spring.dto;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long i) { id = i; }
    public String getUsername() { return username; }
    public void setUsername(String u) { username = u; }
    public String getEmail() { return email; }
    public void setEmail(String e) { email = e; }
}