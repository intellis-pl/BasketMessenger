package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.dto.BasketMessengerDTO;
import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import com.intellis.messagequeue.basketmessage.mapper.MessageMapper;
import com.intellis.messagequeue.basketmessage.repository.BasketMessengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Slf4j
@Service
public class BasketMessengerServiceImpl implements BasketMessengerService {
  private static final String QUEUE_MESSAGE = "basket-message-queue";
  private static final String QUEUE_FACTORY = "queueListenerFactory";

  private BasketMessengerRepository repository;
  private JmsTemplate jmsTemplate;
  private MessageMapper mapper;

  @Autowired
  public BasketMessengerServiceImpl(BasketMessengerRepository repository, JmsTemplate jmsTemplate, MessageMapper mapper) {
    this.repository = repository;
    this.jmsTemplate = jmsTemplate;
    this.mapper = mapper;
  }

  @JmsListener(destination = QUEUE_MESSAGE, containerFactory = QUEUE_FACTORY)
  public void receiveMessage(BasketMessengerDTO basketMessenger) {
    if(basketMessenger == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    repository.save(mapper.map(basketMessenger));
    log.info("Retrieved and saved basket message for User Id: {}, and Product Id: {}", basketMessenger.getUserId(), basketMessenger.getProductId());
  }

  @Override
  public BasketMessengerDTO getMessage(Long id) {
    BasketMessengerEntity basketMessage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    return mapper.map(basketMessage);
  }

  public void registerBasketMessengerToJms(SQLException exception, BasketMessengerDTO basketMessenger) {
    log.error("Service recovering", exception);
    log.debug("Recovering save message for User Id: {}, and Product Id: {}", basketMessenger.getUserId(), basketMessenger.getProductId());
    //TODO: the one of possible ways is to register object in one queue, and reuse it in different instances for this micro service, or call other service that will restart database connection etc.
    jmsTemplate.convertAndSend(QUEUE_MESSAGE, basketMessenger);
  }


}
