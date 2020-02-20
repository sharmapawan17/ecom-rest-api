package com.ecommerce.user.account;

import com.ecommerce.user.access.AuthorityTypes;
import com.ecommerce.user.access.JwtUserDetailsService;
import com.ecommerce.user.access.UserAuthorityRequest;
import com.ecommerce.user.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userDetailsService.save(userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/authority")
    public ResponseEntity<?> createAuthority(@RequestBody AuthorityTypes authorityTypes) {
        return ResponseEntity.ok(userService.save(authorityTypes));
    }

    @PostMapping("/role")
    public ResponseEntity<?> createRole(@RequestBody UserAuthorityRequest userAuthorityRequest) {
        return ResponseEntity.ok(userService.saveRole(userAuthorityRequest));
    }
}
