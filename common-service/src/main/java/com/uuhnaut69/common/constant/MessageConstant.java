package com.uuhnaut69.common.constant;

/**
 * @author uuhnaut
 * @project mall
 */
public final class MessageConstant {

    /**
     * Already exception messages
     */
    public static final String USER_NAME_ALREADY_EXIST = "User name's already existed !!!";

    public static final String USER_EMAIL_ALREADY_EXIST = "User email's already existed !!!";
    public static final String COUPON_ALREADY_EXIST = "Coupon already existed !!!";
    public static final String TAG_ALREADY_EXIST = "Tag already existed !!!";

    /**
     * Not found exception messages
     */
    public static final String USER_NOT_FOUND = "User doesn't exists !!!";

    public static final String TOKEN_NOT_FOUND = "Token doesn't exists !!!";
    public static final String PRODUCT_NOT_FOUND = "Product doesn't exists !!!";
    public static final String COUPON_NOT_FOUND = "Coupon doesn't exists !!!";
    public static final String CART_NOT_FOUND = "Cart doesn't exists !!!";
    public static final String TAG_NOT_FOUND = "Tag doesn't exists !!!";
    public static final String CATEGORY_NOT_FOUND = "Category doesn't exists !!!";

    /**
     * System messages
     */
    public static final String ACTIVATE_YOUR_ACCOUNT =
            "An email has been sent to your email. Please check it out to activate your account !!!";

    public static final String ACTIVATE_YOUR_ACCOUNT_MAIL_CONTENT =
            "One step last to register successfully, click button below to activate your account !!!";

    /**
     * Payment messages
     */
    public static final String PAID_CART = "Cart has been paid !!!";

    public static final String PAYMENT_METHOD_IS_NOT_STRIPE = "Payment method is not stripe !!!";

    private MessageConstant() {
    }
}
