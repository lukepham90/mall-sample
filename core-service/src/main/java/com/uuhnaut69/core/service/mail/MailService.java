package com.uuhnaut69.core.service.mail;

import com.uuhnaut69.core.domain.model.User;

/**
 * @author uuhnaut
 * @project mall
 */
public interface MailService {

  void sendMail(User user) throws Exception;
}
