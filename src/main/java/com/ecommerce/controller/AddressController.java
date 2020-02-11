package com.ecommerce.controller;

import com.ecommerce.entity.AddressEntity;
import com.ecommerce.model.AddressDTO;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("user/{userId}/address")
    public List<AddressEntity> getAll(@PathVariable("userId") long userId) {
        return addressService.getAllAddress(userId);
    }

    @GetMapping("/address/{addressId}")
    public AddressEntity getOne(@PathVariable("userId") long userId, @PathVariable("addressId") long addressId) {
        return addressService.getAddress(userId, addressId);
    }

    @PostMapping("user/{userId}/address")
    public AddressEntity updateOne(@PathVariable("userId") long userId,
                                   @RequestBody AddressDTO addressDTO) {
        return addressService.saveAddress(addressDTO, userId);
    }
    @DeleteMapping("user/{userId}/address")
    public ResponseEntity deleteOne(@PathVariable("userId") long userId,
                                   @RequestBody AddressDTO addressDTO){
        addressService.deleteAddress(addressDTO, userId);
        return ResponseEntity.ok("Request completed successfully");
    }
}
