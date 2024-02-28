package com.reno.reno.model.store;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "store_code_discount", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = false)
public class StoreCodeDiscountEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_amount")
    private Integer codeAmount;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "used_code_amount")
    private Integer usedCodeAmount = 0;

    @Column(name = "code_text")
    private String codeText;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "code_discount_type_id", referencedColumnName = "id")
    private CodeDiscountTypeEntity codeDiscountType;

    @Column(name = "min_amount")
    private Integer minAmount;

    @Column(name = "max_discount")
    private Integer maxDiscount;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "exprid_date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date expridDate;

}
