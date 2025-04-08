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
public class EmployeeShiftId implements java.io.Serializable {
    private static final long serialVersionUID = -6855857925016913728L;
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "shift_id", nullable = false)
    private Integer shiftId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeShiftId entity = (EmployeeShiftId) o;
        return Objects.equals(this.shiftId, entity.shiftId) &&
                Objects.equals(this.employeeId, entity.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftId, employeeId);
    }

}