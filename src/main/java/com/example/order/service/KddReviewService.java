package com.example.order.service;

import com.example.order.entity.KddReview;
import com.example.order.repository.KddReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KddReviewService {

    private final KddReviewRepository kddReviewRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KddReviewService(KddReviewRepository kddReviewRepository) {
        this.kddReviewRepository = kddReviewRepository;
    }

    public KddReview findByOwnerSeqId(Long ownerId){
        KddReview kddReview = kddReviewRepository.findByOwnerSeqId(ownerId);

        if(kddReview != null){
            return kddReview;
        } else {
            return null;
        }
    }

    public void save(KddReview kddReview){
        kddReviewRepository.save(kddReview);
    }

}
