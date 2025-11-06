package com.example.learn_java_spring.repository;

import com.example.learn_java_spring.model.CallDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallDetailRepository extends JpaRepository<CallDetail,Integer>, JpaSpecificationExecutor<CallDetail> {
    Optional<CallDetail> findByCallUserName(String username);
}
