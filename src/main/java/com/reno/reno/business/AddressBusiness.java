package com.reno.reno.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.CreateAddressRequest;
import com.reno.reno.repository.AddressRepository;
import com.reno.reno.util.Util;

@Component
public class AddressBusiness {
    private @Autowired AddressRepository addressRepository;

    public AddressEntiry saveAddress(CreateAddressRequest addressRequest) {
        AddressEntiry address = shouldSetAddress(addressRequest);
        address = addressRepository.save(address);
        return address;
    }

    public AddressEntiry shouldSetAddress(CreateAddressRequest request) {
        AddressEntiry address = new AddressEntiry();
        Util.setIfNotNull(request.getTal(), address::setTal);
        Util.setIfNotNull(request.getHouseNumber(), address::setHouseNumber);
        Util.setIfNotNull(request.getSubdistrict(), address::setSubdistrict);
        Util.setIfNotNull(request.getDistrict(), address::setDistrict);
        Util.setIfNotNull(request.getProvince(), address::setProvince);
        Util.setIfNotNull(request.getPostalcode(), address::setPostalcode);
        address.setIs_active(Boolean.TRUE);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());
        return address;
    }
}
