package com.example.springjwtauthexample.repository;

import com.example.springjwtauthexample.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    boolean existsByEmailsContaining(String email);
    boolean existsByPhonesContaining(String phone);
    Page<User> findByBirthDateGreaterThan(Date birthDay, Pageable pageable);

}
