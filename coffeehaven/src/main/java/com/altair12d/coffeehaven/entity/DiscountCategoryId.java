package com.altair12d.coffeehaven.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DiscountCategoryId implements java.io.Serializable {
    private static final long serialVersionUID = 1717136337905589423L;
    @Column(name = "discount_id", nullable = false)
    private Integer discountId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DiscountCategoryId entity = (DiscountCategoryId) o;
        return Objects.equals(this.discountId, entity.discountId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, categoryId);
    }

}