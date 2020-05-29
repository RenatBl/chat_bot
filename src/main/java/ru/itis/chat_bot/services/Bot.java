package ru.itis.chat_bot.services;

public interface Bot {
    void sendMessage(String guild, String channel, String message);
}
