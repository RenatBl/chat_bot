package ru.itis.chat_bot.listeners;


import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.chat_bot.dto.MessageDto;
import ru.itis.chat_bot.services.MessageHandler;

public class DiscordBotListener extends ListenerAdapter {

    @Autowired
    private MessageHandler messageHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();

        String response = messageHandler.handleMessage(MessageDto.builder()
                .text(message.getContentRaw())
                .owner(event.getAuthor().getName())
                .build());

        channel.sendMessage(response).queue();
    }
}
