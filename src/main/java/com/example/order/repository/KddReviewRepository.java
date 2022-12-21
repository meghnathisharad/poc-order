package com.example.order.repository;

import com.example.order.entity.KddReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KddReviewRepository extends JpaRepository<KddReview, Long> {

    public KddReview findByOwnerSeqId(Long ownerSeqId);
}
