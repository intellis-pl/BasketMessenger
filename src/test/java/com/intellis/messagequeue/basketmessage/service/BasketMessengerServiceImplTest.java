package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.dto.BasketMessengerDTO;
import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import com.intellis.messagequeue.basketmessage.mapper.MessageMapper;
import com.intellis.messagequeue.basketmessage.repository.BasketMessengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketMessengerServiceImplTest {

    private static final Long PRODUCT_ID = 1001L;
    private static final Long USER_ID = 1L;
    private static final String PRODUCT_NAME = "Product";
    private static final Long BASKET_MESSENGER_ID = 1L;

    @InjectMocks
    private BasketMessengerServiceImpl basketMessengerService;
    @Mock
    private BasketMessengerRepository repository;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private MessageMapper mapper;

    @BeforeEach
    void init() {
        basketMessengerService = new BasketMessengerServiceImpl(repository, jmsTemplate, mapper);
    }

    @Test
    void shouldInitializeBasketMessengerService() {
        assertThat(basketMessengerService).isNotNull();
    }

    @Test
    void shouldSaveBasketMessenger() {
        //given
        BasketMessengerDTO basketMessenger = createBasketMessenger();
        BasketMessengerEntity basketMessengerEntity = createBasketMessengerEntity();

        //when
        when(mapper.map(basketMessenger)).thenReturn(basketMessengerEntity);
        basketMessengerService.receiveMessage(basketMessenger);

        //then
        verify(repository).save(basketMessengerEntity);
    }

    private BasketMessengerEntity createBasketMessengerEntity() {
        return BasketMessengerEntity.builder()
                .id(BASKET_MESSENGER_ID)
                .productId(PRODUCT_ID)
                .userId(USER_ID)
                .productName(PRODUCT_NAME)
                .build();
    }

    private BasketMessengerDTO createBasketMessenger() {
        return BasketMessengerDTO.builder()
                .productId(PRODUCT_ID)
                .userId(USER_ID)
                .productName(PRODUCT_NAME)
                .build();
    }

}