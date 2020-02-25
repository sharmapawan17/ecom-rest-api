package com.ecommerce.jwt;

import com.ecommerce.user.access.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userAuthorityRepository")
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    UserAuthority findByUserId(Long id);
}
