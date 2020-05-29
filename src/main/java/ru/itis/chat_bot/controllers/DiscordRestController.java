package ru.itis.chat_bot.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.chat_bot.models.CustomMessage;
import ru.itis.chat_bot.services.Bot;

@RestController
public class DiscordRestController {

    private final Bot bot;

    public DiscordRestController(Bot bot) {
        this.bot = bot;
    }

    @RequestMapping(value="/testmsg", method = RequestMethod.POST)
    public void msgDiscord(@RequestBody CustomMessage message) {
        System.out.println(message);
        bot.sendMessage(message.getGuild(), message.getChannel(), message.getMessage());
    }
}
