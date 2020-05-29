package ru.itis.chat_bot.services;

import ru.itis.chat_bot.dto.MessageDto;

public interface MessageHandler {
    String handleMessage(MessageDto messageDto);
}
