package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.store.CreateStoreImageRequest;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreImageEntity;
import com.reno.reno.model.store.StoreImageTypeEntity;
import com.reno.reno.repository.store.StoreImageRepository;
import com.reno.reno.repository.store.StoreImageTypeRepository;
import com.reno.reno.util.Util;

@Component
public class StoreImageBusiness {

    private @Autowired StoreImageRepository storeImageRepository;
    private @Autowired StoreImageTypeRepository storeImageTypeRepository;
    private @Autowired ImageBusiness imageBusiness;

    public List<StoreImageEntity> shouldSaveStoreImage(CreateStoreRequest request,
            StoreEntity store, Long createdById) {
        List<StoreImageEntity> storeImages = new ArrayList<>();
        if (!Util.isNullOrEmpty(request.getStoreImages())) {
            List<StoreImageTypeEntity> storeImageTypes = storeImageTypeRepository.findAll();
            HashMap<Integer, StoreImageTypeEntity> storeImageTypesMap = new HashMap<Integer, StoreImageTypeEntity>();
            storeImageTypes.forEach(e -> storeImageTypesMap.put(e.getId(), e));
            for (CreateStoreImageRequest storeImageRequest : request.getStoreImages()) {
                StoreImageTypeEntity storeImageType = storeImageTypesMap.get(storeImageRequest.getStoreImageTypeId());
                if (storeImageType != null) {
                    ImageEntity image = imageBusiness.saveImage(storeImageRequest.getKey(), createdById);
                    StoreImageEntity storeImage = saveStoreImage(image, store, storeImageType);
                    storeImages.add(storeImage);
                }
            }
            storeImages = storeImageRepository.saveAll(storeImages);
        }
        return storeImages;
    }

    public StoreImageEntity saveStoreImage(ImageEntity image, StoreEntity store, StoreImageTypeEntity storeImageType) {
        StoreImageEntity storeImage = new StoreImageEntity();
        storeImage.setStoreImageType(storeImageType);
        storeImage.setImage(image);
        storeImage.setStore(store);
        storeImage.setCreatedAt(new Date());
        return storeImageRepository.save(storeImage);
    }
}
