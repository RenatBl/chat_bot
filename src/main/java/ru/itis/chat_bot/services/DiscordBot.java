package ru.itis.chat_bot.services;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.springframework.stereotype.Service;
import ru.itis.chat_bot.listeners.DiscordBotListener;

import javax.security.auth.login.LoginException;

@Service
public class DiscordBot implements Bot {

    private static final String token = "ТВОЙ ТОКЕН";

    private JDA jda;

    public DiscordBot(DiscordBotListener listener) {
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setGame(Game.watching("Words_War"))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .addEventListener(listener)
                    .buildAsync();
            System.out.println("Login");
        } catch (LoginException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void sendMessage(String guild, String channel, String message) {
        jda
                .getGuildById(guild)
                .getTextChannelById(channel)
                .sendMessage(message)
                .queue();
    }
}
