package com.example.ambieye;

public class ChatMessage {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_FILE = 1;

    public String message;
    public String senderEmail;
    public long timestamp;
    public int messageType;
    public String attachmentUri;

    // Constructor for text messages
    public ChatMessage(String message, String senderEmail) {
        this.message = message;
        this.senderEmail = senderEmail;
        this.timestamp = System.currentTimeMillis();
        this.messageType = TYPE_TEXT;
    }

    // Constructor for file messages
    public ChatMessage(String message, String senderEmail, String attachmentUri) {
        this.message = message;
        this.senderEmail = senderEmail;
        this.timestamp = System.currentTimeMillis();
        this.messageType = TYPE_FILE;
        this.attachmentUri = attachmentUri;
    }
}
