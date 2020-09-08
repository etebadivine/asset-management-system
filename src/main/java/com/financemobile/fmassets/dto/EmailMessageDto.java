package com.financemobile.fmassets.dto;


import lombok.Data;

@Data
public class EmailMessageDto {

    private String to;
    private String from;
    private String subject;
    private String content;

    public EmailMessageDto(String to, String from, String subject, String content) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.content = content;
    }
}

