package com.uuhnaut69.mall.repository.user;

import com.uuhnaut69.mall.domain.model.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface VerifyTokenRepository extends JpaRepository<VerifyToken, UUID> {

    /**
     * Find by token
     *
     * @param token Token
     * @return Optional VerifyToken
     */
    Optional<VerifyToken> findByToken(String token);
}
