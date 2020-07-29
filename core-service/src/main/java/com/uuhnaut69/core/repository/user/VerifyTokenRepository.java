package com.uuhnaut69.core.repository.user;

import com.uuhnaut69.core.domain.model.VerifyToken;
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

    Optional<VerifyToken> findByToken(String token);
}
