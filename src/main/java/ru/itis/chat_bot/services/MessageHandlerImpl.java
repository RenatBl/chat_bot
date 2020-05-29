package ru.itis.chat_bot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.chat_bot.dto.MessageDto;
import ru.itis.chat_bot.models.Message;
import ru.itis.chat_bot.models.enums.ApplicationType;
import ru.itis.chat_bot.repositories.MessageRepository;
import ru.itis.chat_bot.utils.WordsParser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MessageHandlerImpl implements MessageHandler {

    private static List<String> words = WordsParser.getWords();

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public String handleMessage(MessageDto dto) {
        String text = dto.getText().toLowerCase();
        List<Message> messages = messageRepository.findAll();
        Optional<Message> messageOptional = messages.stream()
                .filter(message1 ->
                        message1.getText().equals(text)
                ).findAny();

        String lastWord = messages.get(messages.size() - 1).getText();
        char lastWordLetter = lastWord.charAt(lastWord.length() - 1);
        char lastLetter = text.toCharArray()[text.toCharArray().length - 1];

        if (!messageOptional.isPresent()) {
            if (lastWordLetter == lastLetter) {
                if (words.stream().anyMatch(text::equals)) {
                    messageRepository.save(Message.builder()
                            .owner(dto.getOwner())
                            .text(text)
                            .applicationType(ApplicationType.DISCORD)
                            .creationDate(LocalDateTime.now())
                            .build());

                    log.info("New word saved: " + text + "by" + dto.getOwner());

                    return "Игрок " + dto.getOwner() + " назвал слово " + "\"" + text + "\"" + '\n' +
                            "Следующее слово на букву " + "\"" + lastLetter + "\"";

                } else return "Слово некорректно! Выберите другое слово";
            } else return "Буквы не совпдают! Выберите другое слово";
        } else return "Данное слово уже есть! Выберите другое слово";
    }
}
