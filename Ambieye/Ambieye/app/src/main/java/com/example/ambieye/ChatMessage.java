package com.example.ambieye;

public class ChatMessage {
    public String message;
    public String senderEmail;
    public long timestamp;

    public ChatMessage(String message, String senderEmail) {
        this.message = message;
        this.senderEmail = senderEmail;
        this.timestamp = System.currentTimeMillis();
    }
}
