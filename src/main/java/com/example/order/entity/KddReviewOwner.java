package com.example.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "kdd_review_owner")
public class KddReviewOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "owner_seq_id")
    private Long ownerSeqId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerSeqId() {
        return ownerSeqId;
    }

    public void setOwnerSeqId(Long ownerSeqId) {
        this.ownerSeqId = ownerSeqId;
    }

    @Override
    public String toString() {
        return "KddReviewOwner{" +
                "ownerId=" + ownerId +
                ", ownerSeqId=" + ownerSeqId +
                '}';
    }
}
