package ru.itis.chat_bot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.chat_bot.dto.MessageDto;
import ru.itis.chat_bot.models.Message;
import ru.itis.chat_bot.models.enums.ApplicationType;
import ru.itis.chat_bot.repositories.MessageRepository;
import ru.itis.chat_bot.utils.WordsParser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MessageHandlerImpl implements MessageHandler {

    private static final List<String> words = WordsParser.getWords();

    private String user;

    private final MessageRepository messageRepository;

    public MessageHandlerImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public String handleMessage(MessageDto dto) {
        if (user == null) {
            user = "";
        } else if (user.equals(dto.getOwner())) {
            return "Сейчас не ваша очередь";
        }

        String text = dto.getText().toLowerCase();
        List<Message> messages = messageRepository.findAll();

        Optional<Message> messageOptional;
        if (!messages.isEmpty()){
        messageOptional = messages.stream()
                .filter(message1 ->
                        message1.getText().equals(text)
                ).findAny();
        } else {
            messageOptional = Optional.empty();
        }

        if (!messageOptional.isPresent()) {
            char firstLetter = text.toCharArray()[0];
            char lastLetter = text.toCharArray()[text.length() - 1];

            char lastWordLetter;
            if (messages.size() > 0) {
                Message lastMessage = messages.get(messages.size() - 1);
                lastWordLetter = lastMessage.getText().charAt(lastMessage.getText().length() - 1);
                if (lastWordLetter == 'ы' || lastWordLetter == 'ъ' || lastWordLetter == 'ь') {
                    lastWordLetter = lastMessage.getText().charAt(lastMessage.getText().length() - 2);
                }
                if (lastLetter == 'ы' || lastLetter == 'ъ' || lastLetter == 'ь') {
                    lastLetter = text.toCharArray()[text.length() - 2];
                }
            } else {
                lastWordLetter = firstLetter;
            }
            if (lastWordLetter == firstLetter) {
                if (words.stream().anyMatch(text::equals)) {
                    messageRepository.save(Message.builder()
                            .owner(dto.getOwner())
                            .text(text)
                            .applicationType(ApplicationType.DISCORD)
                            .creationDate(LocalDateTime.now())
                            .build());

                    log.info("New word saved: " + text + " by " + dto.getOwner());

                    user = dto.getOwner();

                    return "Игрок " + dto.getOwner() + " назвал слово " + "\"" + text + "\"" + '\n' +
                            "Следующее слово на букву " + "\"" + lastLetter + "\"";

                } else return "Слово некорректно! Выберите другое слово на букву " + lastWordLetter;
            } else return "Буквы не совпадают! Выберите другое слово на букву " + lastWordLetter;
        } else return "Данное слово уже есть! Выберите другое слово";
    }
}
