package com.financemobile.fmassets.service;


import com.financemobile.fmassets.dto.EmailMessageDto;
import com.financemobile.fmassets.service.messaging.EmailComposer;
import com.financemobile.fmassets.service.messaging.SendEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SendEmailServiceTest {

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private EmailComposer emailComposer;

    @Test
    public void shouldSendEmail() throws MessagingException {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", "Angela");
        String content = emailComposer.composeMessageContent(params,"signup.vm");
        EmailMessageDto emailMessageDto =
                new EmailMessageDto("test@gmail.com", null, "Sign Up", content);
        Boolean actual = sendEmailService.send(emailMessageDto);
        Assertions.assertEquals(true, actual);
    }
}
