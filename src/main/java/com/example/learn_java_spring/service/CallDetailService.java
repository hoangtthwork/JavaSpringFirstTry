package com.example.learn_java_spring.service;

import com.example.learn_java_spring.model.CallDetail;

public interface CallDetailService {
    public long countRead();
    public CallDetail create(CallDetail x);
    public CallDetail firstOrDefault(String name);
}
