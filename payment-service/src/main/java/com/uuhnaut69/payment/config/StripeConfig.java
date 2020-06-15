package com.uuhnaut69.payment.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class StripeConfig {

  @Value("${STRIPE_SECRET_KEY}")
  private String secretKey;

  @Bean
  public void init() {
    Stripe.apiKey = secretKey;
  }
}
