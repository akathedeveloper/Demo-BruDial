package com.loginSignupJWT.controller;

import com.loginSignupJWT.entities.TwilioCustomMessage;
import com.loginSignupJWT.entities.TwilioCustomResponse;
import com.loginSignupJWT.services.impl.TwilioCustomResponseUtil;
import com.loginSignupJWT.services.impl.TwilioService;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class TwilioController {
    @Autowired
    private TwilioService twilioService;

    @PostMapping("/send")
    public ResponseEntity<TwilioCustomResponse<String>> sendMessage(@RequestParam String to, @RequestParam String body) {
        try {
            twilioService.sendMessage(to, body);
            return TwilioCustomResponseUtil.createSuccessResponse("Message sent successfully", "Message sent");
        } catch (Exception e) {
            return TwilioCustomResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Failed to send message");
        }
    }

    @PostMapping(value = "/receive", produces = "application/xml")
    public String receiveMessage(@RequestBody MultiValueMap<String, String> formData) {
        String from = formData.getFirst("From");
        String body = formData.getFirst("Body");

        // Log received message
        twilioService.receiveMessage(from, body);

        // Create a TwiML response
        Message message = new Message.Builder()
                .body(new Body.Builder("Your message has been received.").build())
                .build();
        MessagingResponse response = new MessagingResponse.Builder()
                .message(message)
                .build();

        return response.toXml();
    }
    @GetMapping("/all")
    public ResponseEntity<TwilioCustomResponse<List<TwilioCustomMessage>>> getAllMessages() {
        try {
            List<TwilioCustomMessage> messages = twilioService.getAllMessages();
            return TwilioCustomResponseUtil.createSuccessResponse(messages, "Messages retrieved successfully");
        } catch (Exception e) {
            return TwilioCustomResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Failed to retrieve messages");
        }
    }
}
