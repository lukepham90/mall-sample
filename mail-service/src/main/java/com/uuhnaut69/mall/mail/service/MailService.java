package com.uuhnaut69.mall.mail.service;

import com.uuhnaut69.mall.user.domain.User;

public interface MailService {

    /**
     * Send mail to activate account
     *
     * @param user
     * @throws Exception
     */
    void sendMail(User user) throws Exception;
}
