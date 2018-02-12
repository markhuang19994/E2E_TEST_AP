package com.message.mail.service;

import java.util.Map;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/8, MarkHuang,new
 * </ul>
 * @since 2018/2/8
 */
public interface EmailService {

    /**
     * send email message with attachment
     *
     * @param to               target email
     * @param subject          the subject from email
     * @param text             content of email
     * @param pathToAttachment email attachment
     * @param isHtml           if this email text is html
     */
    public void sendMessage(String to, String subject, String text
            , String pathToAttachment, boolean isHtml);

    /**
     * use thymeleaf send  html message
     *
     * @param templateName the template file name  in path
     * @param args         template message key and value
     * @param to           target email
     * @param subject      the subject from email
     * @param pathToAttachment email attachment
     */
    public void sendHtmlMessage(String templateName, Map<String, String> args
            , String to, String subject, String pathToAttachment);
}
