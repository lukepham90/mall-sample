package com.uuhnaut69.payment.service.impl;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.uuhnaut69.core.domain.enums.PaymentStatus;
import com.uuhnaut69.core.domain.model.Cart;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.payment.payload.request.CreditCard;
import com.uuhnaut69.payment.service.StripeService;
import com.uuhnaut69.core.repository.cart.CartRepository;
import com.uuhnaut69.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    public void charge(CreditCard creditCard, Cart cart, UUID userId) throws Exception {
        String stripeToken = createTokenToCharge(creditCard);
        User user = userService.findById(userId);
        log.info("User has id {} is being process payment !!!", user.getId());
        if (user.getCustomerStripeId() == null || user.getCustomerStripeId().isEmpty()) {
            Customer customer = createCustomer(stripeToken, user.getEmail());
            user.setCustomerStripeId(customer.getId());
            userService.save(user);
        }
        chargeCustomerCard(user.getCustomerStripeId(), cart);
    }

    private Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

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
            cart.getOrderItems().forEach(e -> {
                e.getProduct().setStocks(e.getProduct().getStocks() - e.getQuantity());
                e.getProduct().setSoldCount(e.getProduct().getSoldCount() + e.getQuantity());
            });
            cartRepository.save(cart);
        } else if (charge.getStatus().equals("failed")) {
            cart.setPaymentStatus(PaymentStatus.FAILED);
            cartRepository.save(cart);
        }
    }

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

    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

}
