package com.uuhnaut69.security.config;

import com.uuhnaut69.mall.core.constant.AppConstant;
import com.uuhnaut69.security.user.UserPrinciple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }

    public static class SpringSecurityAuditAwareImpl implements AuditorAware<UUID> {

        @Override
        public Optional<UUID> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || authentication instanceof AnonymousAuthenticationToken) {
                return Optional.of(AppConstant.SYSTEM);
            }

            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

            return Optional.ofNullable(userPrincipal.getId());
        }
    }
}
