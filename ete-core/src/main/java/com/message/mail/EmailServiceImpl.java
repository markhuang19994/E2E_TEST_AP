package com.message.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.message.mail.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * This class methods can send email text format or Html format<br>
 *  with attachment
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/8, MarkHuang,new
 * </ul>
 * @since 2018/2/8
 * @see EmailService
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendMessage(
            String to, String subject, String text, String pathToAttachment, boolean isHtml) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            if (pathToAttachment != null) {
                FileSystemResource file
                        = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment("Invoice", file);
            }
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(
            String to, String subject, String text, String pathToAttachment) {
        sendMessage(to, subject, text, pathToAttachment, false);
    }

    public void sendMessage(
            String to, String subject, String text) {
        sendMessage(to, subject, text, null, false);
    }

    @Override
    public void sendHtmlMessage(
            String templateName, Map<String, String> args, String to
            , String subject, String pathToAttachment) {
        Context context = new Context();
        context.setVariables(args);
        String html = templateEngine.process(templateName, context);
        if (pathToAttachment == null) {
            sendMessage(to, subject, html, null, true);
        } else {
            sendMessage(to, subject, html, pathToAttachment, true);
        }
    }

    public void sendHtmlMessage(
            String templateName, Map<String, String> args, String to, String subject) {
        sendHtmlMessage(templateName, args, to, subject, null);
    }
}
