package com.uuhnaut69.mall.repository.user;

import com.uuhnaut69.mall.domain.model.User;
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

    /**
     * Find user by user name
     *
     * @param username
     * @return
     */
    Optional<User> findByUsernameAndIsEnabled(String username, Boolean isEnabled);

    /**
     * Check user name exist or not
     *
     * @param username
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * Check user email exist or not
     *
     * @param email
     * @return boolean
     */
    boolean existsByEmail(String email);
}
