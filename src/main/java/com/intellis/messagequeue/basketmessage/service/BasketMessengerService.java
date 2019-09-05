package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.to.BasketMessengerTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.sql.SQLException;

public interface BasketMessengerService {
    @Retryable(value = { SQLException.class },
            maxAttemptsExpression = "${basketmessage.app.maxAttempts}",
            backoff = @Backoff(delayExpression = "${basketmessage.app.backoff.delay}"))
    BasketMessengerTO receiveMessage(BasketMessengerTO basketMessenger);

    @Recover
    String recover(SQLException exception);

    BasketMessengerTO getMessage(Long id);

}
