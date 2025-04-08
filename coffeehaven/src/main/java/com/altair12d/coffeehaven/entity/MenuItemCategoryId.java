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
public class MenuItemCategoryId implements java.io.Serializable {
    private static final long serialVersionUID = -3953065154250304523L;
    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuItemCategoryId entity = (MenuItemCategoryId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, categoryId);
    }

}