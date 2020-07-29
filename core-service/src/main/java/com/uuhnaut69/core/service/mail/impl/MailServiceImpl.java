package com.uuhnaut69.core.service.mail.impl;

import com.uuhnaut69.common.constant.AppConstant;
import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.utils.MailTemplateUtils;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.service.mail.MailService;
import com.uuhnaut69.core.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@EnableAsync
@EnableScheduling
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final TokenService tokenService;

    @Async
    @Override
    public void sendMail(User user) throws Exception {
        String subject = "Registration Confirmation";
        String receiveMailAddress = user.getEmail();
        String verifyToken = tokenService.generateToken(user);
        String confirmUrl =
                AppConstant.URL_BASE
                        + UrlConstants.PUBLIC_URL
                        + UrlConstants.AUTH_URL
                        + "/confirm?token="
                        + verifyToken;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(receiveMailAddress);
        helper.setSubject(subject);
        String text =
                MailTemplateUtils.makeHtmlActiveAccountMail(
                        MessageConstant.ACTIVATE_YOUR_ACCOUNT_MAIL_CONTENT, confirmUrl, user.getUsername());
        helper.setText(text, true);

        mailSender.send(message);
    }
}
