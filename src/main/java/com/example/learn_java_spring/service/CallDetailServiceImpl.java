package com.example.learn_java_spring.service;

import com.example.learn_java_spring.model.CallDetail;
import com.example.learn_java_spring.repository.CallDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class CallDetailServiceImpl implements CallDetailService {
    private final CallDetailRepository repo;

    public CallDetail firstOrDefault(String callUserName) {
        Specification<CallDetail> spec = Specification.where(null);

        if (callUserName != null)
            spec = spec.and((root, query, cb) -> cb.equal(root.get("callUserName"), callUserName));


        return repo.findAll(spec).get(0);
    }

    @Transactional(readOnly = true)
    public long countRead() {return repo.count();}


    public CallDetail create(CallDetail x) { return repo.save(x); }

}
