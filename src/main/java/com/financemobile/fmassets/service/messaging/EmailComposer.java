package com.financemobile.fmassets.service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Component
public class EmailComposer {

    private final VelocityEngine velocityEngine;

    private final VelocityContext velocityContext;

    @Value("${fmassets.email.template.location}")
    private String templateLocation;

    @Autowired
    public EmailComposer(VelocityEngine velocityEngine, VelocityContext velocityContext) {
        this.velocityEngine = velocityEngine;
        this.velocityContext = velocityContext;
    }

    public String composeMessageContent(Map<String, Object> details, String templateName) {
        StringWriter writer = new StringWriter();
        velocityContext.put("details", details);
        StringBuilder templateBuilder = new StringBuilder();
        templateBuilder.append(templateLocation);
        templateBuilder.append(templateName);
        velocityEngine.mergeTemplate(
                templateBuilder.toString(),
                "UTF-8",
                velocityContext,
                writer
        );

        return writer.toString();
    }
}
