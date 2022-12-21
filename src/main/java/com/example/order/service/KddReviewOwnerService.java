package com.example.order.service;

import com.example.order.entity.KddReviewOwner;
import com.example.order.repository.KddReviewOwnerRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KddReviewOwnerService {

    private final KddReviewOwnerRepository kddReviewOwnerRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KddReviewOwnerService(KddReviewOwnerRepository kddReviewOwnerRepository) {
        this.kddReviewOwnerRepository = kddReviewOwnerRepository;
    }

    public KddReviewOwner findByKddReviewOwnerId(Long ownerId){

        Optional<KddReviewOwner> kddReviewOwner = kddReviewOwnerRepository.findById(ownerId);
        if(kddReviewOwner.isPresent())
            return kddReviewOwner.get();
        else
            return null;

    }
}
