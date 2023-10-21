package com.reno.reno.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.ImageEntity;
import com.reno.reno.repository.ImageRepository;

@Component
public class ImageBusiness {
    private @Autowired ImageRepository imageRepository;

    public ImageEntity saveImage(String key, Long customerId) {
        ImageEntity image = new ImageEntity();
        image.setKey(key);
        image.setCreatedAt(new Date());
        image.setCreatedBy(customerId);
        return imageRepository.save(image);
    }
}
