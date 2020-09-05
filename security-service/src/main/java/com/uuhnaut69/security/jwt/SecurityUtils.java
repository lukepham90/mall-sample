package com.uuhnaut69.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public class SecurityUtils {

  private SecurityUtils() {}

  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .noneMatch("ROLE_ANONYMOUS"::equals);
  }

  public static Optional<UUID> getCurrentUserId() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
  }

  private static UUID extractPrincipal(Authentication authentication) {
    if (authentication == null) {
      return null;
    } else if (authentication.getPrincipal() instanceof CustomUserdetails) {
      CustomUserdetails currentUserLogin = (CustomUserdetails) authentication.getPrincipal();
      return currentUserLogin.getId();
    } else if (authentication.getPrincipal() instanceof UUID) {
      return (UUID) authentication.getPrincipal();
    }
    return null;
  }

  public static boolean isCurrentUserInRole(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(grantedAuthority -> grantedAuthority.equals(authority));
  }
}
