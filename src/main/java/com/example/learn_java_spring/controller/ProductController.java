package com.example.learn_java_spring.controller;

import com.example.learn_java_spring.exeption.HttpStatusException;
import com.example.learn_java_spring.model.Product;
import com.example.learn_java_spring.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository; // Controller gọi trực tiếp Repository để rút gọn

    // GET: Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET: Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST: Tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // PUT: Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(id); // Đảm bảo cập nhật đúng sản phẩm
        Product updatedProduct = productRepository.save(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // DELETE: Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}