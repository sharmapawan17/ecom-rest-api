package com.ecommerce.user.account;

import com.ecommerce.exception.DatabaseException;
import com.ecommerce.jwt.UserAuthorityRepository;
import com.ecommerce.user.access.AuthorityTypes;
import com.ecommerce.user.access.AuthorityTypesRepository;
import com.ecommerce.user.access.UserAuthority;
import com.ecommerce.user.access.UserAuthorityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthorityTypesRepository authorityTypesRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private UserRepository userRepository;

    public Object save(AuthorityTypes authorityTypes) {
        AuthorityTypes out = null;
        try {
            out = authorityTypesRepository.save(authorityTypes);
        } catch (Exception e) {
            throw new DatabaseException("DB_001", e.getMessage());
        }
        return out;
    }

    public Object saveRole(UserAuthorityRequest userAuthorityRequest) {
        UserEntity userEntity = userRepository.findByEmail(userAuthorityRequest.getUsername());
        if (userEntity == null) {
            throw new DatabaseException("DB_002", "Invalid User");
        }
        AuthorityTypes authorityTypes = authorityTypesRepository.findByName(userAuthorityRequest.getRolename());
        if (authorityTypes == null) {
            throw new DatabaseException("DB_003", "Invalid Role");
        }
        return userAuthorityRepository.save(new UserAuthority(userEntity.getId(), authorityTypes.getName()));
    }
}
