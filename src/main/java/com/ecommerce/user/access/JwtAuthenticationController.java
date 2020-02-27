package com.ecommerce.user.access;

import com.ecommerce.ResponseWithStatus;
import com.ecommerce.Status;
import com.ecommerce.aspect.Track;
import com.ecommerce.jwt.JwtRequest;
import com.ecommerce.jwt.JwtTokenUtil;
import com.ecommerce.user.account.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Track
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        LoggedInUser loggedInUser = userDetailsService.getUserDetails(authenticationRequest.getEmail());

        // In case user's fcmToken or deviceType is different than what we have in database, we need to update it
        if (authenticationRequest.getFcmToken() != loggedInUser.getFcmToken()
            || authenticationRequest.getDeviceType() != loggedInUser.getFcmToken()) {
            userDetailsService.updateByEmail(authenticationRequest);
        }

        final String generateToken = jwtTokenUtil.generateToken(userDetails);
        loggedInUser.setAccessToken(generateToken);
        return ResponseEntity.ok(
                new ResponseWithStatus(new Status(true, "Request completed successfully"), loggedInUser));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
