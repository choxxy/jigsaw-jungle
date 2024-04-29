package com.ninjabyte.puzzle.integration;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JPAHandler extends AbstractMessageHandler {

    public JPAHandler() {

    }

    private Message<?> lastMessage;

    public Message<?> getLastMessage() {
        return this.lastMessage;
    }


    @Override
    protected void handleMessageInternal(Message<?> message) {
        if (message.getPayload() instanceof File) {
            String name = ((File) message.getPayload()).getName();
        } else {
            throw new IllegalArgumentException("File expected as payload.");
        }
    }
}
