package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import com.intellis.messagequeue.basketmessage.mapper.MessageMapper;
import com.intellis.messagequeue.basketmessage.repository.BasketMessengerRepository;
import com.intellis.messagequeue.basketmessage.to.BasketMessengerTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

import static com.intellis.messagequeue.app.jms.config.ActiveMQConfig.QUEUE_FACTORY;
import static com.intellis.messagequeue.app.jms.config.ActiveMQConfig.QUEUE_MESSAGE;

@Slf4j
@Service
public class BasketMessengerServiceImpl implements BasketMessengerService {
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
  public BasketMessengerTO receiveMessage(BasketMessengerTO basketMessenger) {
    BasketMessengerEntity basketMessengerEntity = repository.save(mapper.map(basketMessenger));
    log.info("Retrieved and saved basket message for User Id: {}, and Product Id: {}", basketMessenger.getUserId(), basketMessenger.getProductId());
    return mapper.map(basketMessengerEntity);
  }

  @Override
  public BasketMessengerTO getMessage(Long id) {
    BasketMessengerEntity basketMessage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    return mapper.map(basketMessage);
  }

  public BasketMessengerTO recover(SQLException exception, BasketMessengerTO basketMessenger) {
    log.error("Service recovering", exception);
    log.debug("Recovering save message for User Id: {}, and Product Id: {}", basketMessenger.getUserId(), basketMessenger.getProductId());
    jmsTemplate.convertAndSend(QUEUE_MESSAGE, basketMessenger);
    return basketMessenger;
  }


}
