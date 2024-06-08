package com.yogesh.ecom.emailService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {
    private String to;
    private String subject;
    private String text;
}
