package com.loginSignupJWT.services.impl;


import com.loginSignupJWT.entities.TwilioCustomMessage;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TwilioService {

    private final String accountSid;
    private final String authToken;
    private final String fromPhoneNumber;
    private final List<TwilioCustomMessage> messages = new ArrayList<>();

    @Autowired
    public TwilioService(@Value("") String accountSid,
                         @Value("") String authToken,
                         @Value("") String fromPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhoneNumber = fromPhoneNumber;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendMessage(String to, String body) {
        try{
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromPhoneNumber),
                    body
            ).create();
            Integer errorCode = message.getErrorCode(); // Get error code
            int statusCode = errorCode != null ? errorCode.intValue() : 0;

            TwilioCustomMessage customMessage = new TwilioCustomMessage(
                    UUID.randomUUID().toString(),
                    statusCode,
                    fromPhoneNumber,
                    to,
                    body,
                    LocalDateTime.now(),
                    message.getStatus().toString()
            );
            messages.add(customMessage);
        }catch(ApiException e){
            throw new RuntimeException("Failed to send message: " + e.getMessage(), e);
        }
    }

    public void receiveMessage(String from, String body) {
        // Log or store the incoming message
        System.out.println("Received message from: " + from + " with body: " + body);
        // LocalDateTime timestamp = LocalDateTime.now();
//        System.out.println("Received message at: " + timestamp.format(DateTimeFormatter.ISO_DATE_TIME));
        messages.add(new TwilioCustomMessage(UUID.randomUUID().toString(),200,from,fromPhoneNumber, body,LocalDateTime.now(),"RECEIVED"));
    }

    public List<TwilioCustomMessage> getAllMessages() {
        return messages;
    }
}

