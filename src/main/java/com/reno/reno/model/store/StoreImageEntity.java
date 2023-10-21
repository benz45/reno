package com.reno.reno.model.store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "store_image", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreImageEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "store_image_type_id", referencedColumnName = "id")
    private StoreImageTypeEntity storeImageType;

}
