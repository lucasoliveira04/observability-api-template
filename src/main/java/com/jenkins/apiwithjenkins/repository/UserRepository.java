package com.jenkins.apiwithjenkins.repository;

import com.jenkins.apiwithjenkins.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> { ;
    @Query("SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.address")
    List<Users> findAllWithAddresses();

    @Query("SELECT COUNT(u) FROM Users u")
    Integer countSizeDB();

    @Query(
            value = "SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.address",
            countQuery = "SELECT COUNT(u) FROM Users u"
    )
    Page<Users> findAllWithAddresses(Pageable pageable);


}
