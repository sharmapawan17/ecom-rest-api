package com.ecommerce.controller;

import com.ecommerce.entity.AuthorityTypes;
import com.ecommerce.entity.UserAuthority;
import com.ecommerce.entity.UserAuthorityRequest;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.model.UserDTO;
import com.ecommerce.repository.AuthorityTypesRepository;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.JwtUserDetailsService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        UserEntity userEntity = userDetailsService.save(userDTO);
        if (userDTO.getAddress() != null){
            addressService.saveAddress(userDTO.getAddress(), userEntity.getId());
        }
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/authority")
    public ResponseEntity<?> createAuthority(@RequestBody AuthorityTypes authorityTypes){
        return ResponseEntity.ok(userService.save(authorityTypes));
    }
    @PostMapping("/role")
    public ResponseEntity<?> createRole(@RequestBody UserAuthorityRequest userAuthorityRequest){
        return ResponseEntity.ok(userService.saveRole(userAuthorityRequest));
    }
}
