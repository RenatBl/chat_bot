package ru.itis.chat_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.chat_bot.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
