package com.uuhnaut69.mall.payment.service.impl;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.uuhnaut69.mall.domain.enums.PaymentStatus;
import com.uuhnaut69.mall.domain.model.Cart;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payment.payload.request.CreditCard;
import com.uuhnaut69.mall.payment.service.StripeService;
import com.uuhnaut69.mall.repository.CartRepository;
import com.uuhnaut69.mall.security.user.UserPrinciple;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
public class StripeServiceImpl implements StripeService {

    private final CartRepository cartRepository;
    private final UserService userService;
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    public StripeServiceImpl(CartRepository cartRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public void charge(CreditCard creditCard, Cart cart, UserPrinciple userPrinciple) throws Exception {
        String stripeToken = createTokenToCharge(creditCard);
        User user = userService.findById(userPrinciple.getId());
        log.info("User has id {} is being process payemnt !!!", user.getId());
        if (user.getCustomerStripeId() == null) {
            Customer customer = createCustomer(stripeToken, userPrinciple.getEmail());
            user.setCustomerStripeId(customer.getId());
            userService.save(user);
        }
        chargeCustomerCard(user.getCustomerStripeId(), cart);
    }

    /**
     * Attach card info to customer
     *
     * @param token
     * @param email
     * @return Customer
     * @throws Exception
     */
    private Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    /**
     * Process charge
     *
     * @param customerId
     * @param cart
     * @throws Exception
     */
    private void chargeCustomerCard(String customerId, Cart cart) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", cart.getPriceToPay().intValue() * 100);
        chargeParams.put("currency", cart.getCurrency().name());
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        log.info(charge.getStatus());
        if (charge.getStatus().equals("succeeded")) {
            cart.setPaymentStatus(PaymentStatus.SUCCEED);
            cart.getOrderItems().stream().forEach(e -> {
                e.getProduct().setStocks(e.getProduct().getStocks() - e.getQuantity());
                e.getProduct().setSoldsCnt(e.getProduct().getSoldsCnt() + e.getQuantity());
            });
            cartRepository.save(cart);
        } else if (charge.getStatus().equals("failed")) {
            cart.setPaymentStatus(PaymentStatus.FAILED);
            cartRepository.save(cart);
        }
    }

    /**
     * Create token to charge
     *
     * @param creditCard
     * @return Stripe token
     * @throws Exception
     */
    private String createTokenToCharge(CreditCard creditCard) throws Exception {
        Map<String, Object> card = new HashMap<>();
        card.put("number", creditCard.getCardNumber());
        card.put("exp_month", creditCard.getExpMonth());
        card.put("exp_year", creditCard.getExpYear());
        card.put("cvc", creditCard.getCvc());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        Token token = Token.create(params);
        return token.getId();
    }

    /**
     * Get customer stripe id
     *
     * @param id
     * @return Customer
     * @throws Exception
     */
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

}
