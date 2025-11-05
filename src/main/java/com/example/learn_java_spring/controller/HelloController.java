package com.example.learn_java_spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-rest")
    public String SayHello() {
        return "Hellop from Rest";
    }
}

