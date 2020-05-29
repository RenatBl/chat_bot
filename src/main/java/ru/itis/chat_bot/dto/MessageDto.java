package ru.itis.chat_bot.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
public class MessageDto {
    private String text;
    private String owner;
}
