package com.ecommerce.service;

import com.ecommerce.entity.AuthorityTypes;
import com.ecommerce.entity.UserAuthority;
import com.ecommerce.entity.UserAuthorityRequest;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.exceotion.DatabaseException;
import com.ecommerce.repository.AuthorityTypesRepository;
import com.ecommerce.repository.UserAuthorityRepository;
import com.ecommerce.repository.UserRepository;
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
        }catch (Exception e){
            throw new DatabaseException("DB_001", e.getMessage());
        }
        return out;
    }

    public Object saveRole(UserAuthorityRequest userAuthorityRequest) {
        UserEntity userEntity = userRepository.findByEmail(userAuthorityRequest.getUsername());
        if (userEntity == null){
            throw new DatabaseException("DB_002","Invalid User");
        }
        AuthorityTypes authorityTypes = authorityTypesRepository.findByName(userAuthorityRequest.getRolename());
        if (authorityTypes == null){
            throw new DatabaseException("DB_003","Invalid Role");
        }
        return userAuthorityRepository.save(new UserAuthority(userEntity.getId(), authorityTypes.getId()));
    }
}
