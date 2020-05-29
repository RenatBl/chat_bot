package ru.itis.chat_bot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private String text;
    private String owner;
}
