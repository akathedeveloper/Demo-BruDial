package com.loginSignupJWT.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
// Twilio
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwilioCustomMessage {
    private String id;
    private int statusCode;
    private String from;
    private String to;
    private String body;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime timeStamp;
    private String status;

}
