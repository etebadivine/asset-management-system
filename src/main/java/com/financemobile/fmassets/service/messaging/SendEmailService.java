package com.financemobile.fmassets.service.messaging;

import com.financemobile.fmassets.dto.EmailMessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendEmailService {

    @Value("${fmassets.smtp.server}")
    private String host;
    @Value("${fmassets.smtp.port}")
    private String port;
    @Value("${fmassets.smtp.user}")
    private String username;
    @Value("${fmassets.smtp.password}")
    private String password;
    @Value("${fmassets.smtp.sendername}")
    private String senderName;

    private EmailComposer emailComposer;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);

    public boolean send(EmailMessageDto emailMessage) throws MessagingException {
        boolean success = false;
        if (null != emailMessage) {
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", this.host);
            properties.setProperty("mail.smtp.port", this.port);
            properties.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(properties, authenticator);

            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress(senderName));
            mimeMsg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailMessage.getTo()));
            mimeMsg.setSubject(emailMessage.getSubject());
            mimeMsg.setContent(emailMessage.getContent(), "text/html");
            Transport.send(mimeMsg);
            success = true;
        }
        return success;
    }

    Authenticator authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            passwordAuthentication = new PasswordAuthentication(username, password);
            return passwordAuthentication;
        }
    };
}
