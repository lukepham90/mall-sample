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

    Optional<User> findByUsernameAndIsEnabled(String username, Boolean isEnabled);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
