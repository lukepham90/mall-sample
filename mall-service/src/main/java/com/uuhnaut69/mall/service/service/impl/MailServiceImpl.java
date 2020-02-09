package com.uuhnaut69.mall.service.service.impl;

import com.uuhnaut69.mall.core.constant.AppConstant;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.utils.MailTemplateUtils;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.service.service.MailService;
import com.uuhnaut69.mall.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final TokenService tokenService;

    @Override
    public void sendMail(User user) throws Exception {
        MimeMessage message = constructHtmlActiveAccountMail(user);
        mailSender.send(message);
    }

    /**
     * Construct simple mail to activate account after register account
     *
     * @param user
     * @return MimeMessage
     * @throws Exception
     */
    private MimeMessage constructHtmlActiveAccountMail(User user) throws Exception {
        String subject = "Registration Confirmation";
        String recieveMailAddress = user.getEmail();
        String verifyToken = tokenService.generateToken(user);
        String confirmUrl = AppConstant.URL_BASE + UrlConstants.PUBLIC_URL + UrlConstants.AUTH_URL + "/confirm?token="
                + verifyToken;
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recieveMailAddress);
        helper.setSubject(subject);
        String text = MailTemplateUtils.makeHtmlActiveAccountMail(MessageConstant.ACTIVATE_YOUR_ACCOUNT_MAIL_CONTENT,
                confirmUrl, user.getUsername());
        helper.setText(text, true);
        return message;
    }
}
