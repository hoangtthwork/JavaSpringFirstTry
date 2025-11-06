package com.example.learn_java_spring.controller;

import com.example.learn_java_spring.model.CallDetail;
import com.example.learn_java_spring.repository.CallDetailRepository;
//import com.example.learn_java_spring.repository.ProductRepository;
import com.example.learn_java_spring.service.CallDetailService;
import com.example.learn_java_spring.service.CallDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private CallDetailRepository callDetailRepository;

    @Autowired
    private CallDetailService callDetail;

    @GetMapping("/CallDetail/{id}")
    public ResponseEntity<CallDetail> getById(Integer id) {
        var countCallDetail = callDetail.firstOrDefault("Trần Thị Lệ Hà");
        return new ResponseEntity<>(countCallDetail,HttpStatus.OK);
    }
}