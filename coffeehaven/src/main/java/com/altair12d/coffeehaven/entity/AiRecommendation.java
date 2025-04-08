package com.altair12d.coffeehaven.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ai_recommendations")
public class AiRecommendation {
    @Id
    @Column(name = "recommendation_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private com.altair12d.coffeehaven.entity.Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommended_item_id")
    private com.altair12d.coffeehaven.entity.MenuItem recommendedItem;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private Instant createdAt;

}