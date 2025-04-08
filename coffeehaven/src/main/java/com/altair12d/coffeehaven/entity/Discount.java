package com.altair12d.coffeehaven.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @Column(name = "discount_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "branch_id")
    private Integer branchId;

    @Lob
    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "coupon_code", length = 50)
    private String couponCode;

}