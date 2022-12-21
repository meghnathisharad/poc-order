package com.example.order.repository;

import com.example.order.entity.KddReviewOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KddReviewOwnerRepository extends JpaRepository <KddReviewOwner, Long> {
}
