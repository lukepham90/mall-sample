package com.uuhnaut69.mall.repository.user;

import com.uuhnaut69.mall.domain.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    String USERS_LOGIN_CACHE = "userLoginCache";

    @Cacheable(cacheNames = USERS_LOGIN_CACHE)
    Optional<User> findByUsernameAndIsEnabled(String username, Boolean isEnabled);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
