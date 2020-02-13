package com.ecommerce.user.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("addressRepository")
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserId(long userId);
}
