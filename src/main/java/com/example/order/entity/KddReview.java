package com.example.order.entity;

import javax.persistence.*;

@Entity
@Table(name="kdd_review")
public class KddReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "ownerSeqId")
    private Long ownerSeqId;

    @Column(name = "remark")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
