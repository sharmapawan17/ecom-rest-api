package com.ecommerce.service;

import com.ecommerce.entity.AddressEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.model.AddressDTO;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    public AddressEntity saveAddress(AddressDTO addressDTO, long userId){
        AddressEntity entity = getAddressEntity(addressDTO, userId);
        return addressRepository.save(entity);
    }

    private AddressEntity getAddressEntity(AddressDTO addressDTO, long userId) {
        AddressEntity entity = new AddressEntity();
        entity.setUserId(userId);
        entity.setCountryCode(addressDTO.getCountryCode());
        entity.setState(addressDTO.getState());
        entity.setCity(addressDTO.getCity());
        entity.setLine1(addressDTO.getLine1());
        entity.setLine2(addressDTO.getLine2());
        entity.setLine3(addressDTO.getLine3());
        return entity;
    }

    public void deleteAddress(AddressDTO addressDTO, long userId) {
        AddressEntity entity = getAddressEntity(addressDTO, userId);
        addressRepository.delete(entity);
    }

    public AddressEntity getAddress(long userId, long addressId) {
        return addressRepository.findById(addressId).get();
    }

    public List<AddressEntity> getAllAddress(long userId) {
        return addressRepository.findAllByUserId(userId);
    }
}
