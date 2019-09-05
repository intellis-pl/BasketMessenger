package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.dto.BasketMessengerDTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.sql.SQLException;

public interface BasketMessengerService {
    @Retryable(value = { SQLException.class },
            maxAttemptsExpression = "${basketmessage.app.maxAttempts}",
            backoff = @Backoff(delayExpression = "${basketmessage.app.backoff.delay}"))
    void receiveMessage(BasketMessengerDTO basketMessenger);

    @Recover
    void registerBasketMessengerToJms(SQLException exception, BasketMessengerDTO basketMessenger);

    BasketMessengerDTO getMessage(Long id);

}
