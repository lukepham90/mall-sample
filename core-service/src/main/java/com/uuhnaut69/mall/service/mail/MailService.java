package com.uuhnaut69.mall.service.mail;

import com.uuhnaut69.mall.domain.model.User;

/**
 * @author uuhnaut
 * @project mall
 */
public interface MailService {

    void sendMail(User user) throws Exception;
}
