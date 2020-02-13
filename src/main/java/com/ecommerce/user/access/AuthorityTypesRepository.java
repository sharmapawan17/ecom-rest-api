package com.ecommerce.user.access;

import com.ecommerce.user.access.AuthorityTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("authorityTypesRepository")
public interface AuthorityTypesRepository extends JpaRepository<AuthorityTypes, Long> {
    AuthorityTypes findByName(String name);
}
