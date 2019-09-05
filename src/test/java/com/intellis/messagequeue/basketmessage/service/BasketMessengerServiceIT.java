package com.intellis.messagequeue.basketmessage.service;

import com.intellis.messagequeue.basketmessage.to.BasketMessengerTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static com.intellis.messagequeue.app.jms.config.ActiveMQConfig.QUEUE_MESSAGE;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketMessengerServiceIT {
	private static final Long PRODUCT_ID = 1001L;
	private static final Long USER_ID = 1L;
	private static final Long BASKET_MESSAGE_ID = 1L;
	private static final String PRODUCT_NAME = "Product";
	private static final int MILLIS = 1000;
	private static final Long INVALID_MESSAGE_ID = 0L;

	@Autowired
	private BasketMessengerService basketMessengerService;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Test
	public void shouldThrowExceptionAfterTryToSaveEmptyMassage() {
		//given
		BasketMessengerTO basketMessenger = new BasketMessengerTO();

		//then
		assertThrows(ExhaustedRetryException.class, () -> basketMessengerService.receiveMessage(basketMessenger));
	}

	@Test
	public void shouldThrowExceptionAfterTryToGetMassageWithInvalidId() {
		//given
		BasketMessengerTO basketMessenger = createBasketMessenger();

		//when
		basketMessengerService.receiveMessage(basketMessenger);

		//then
		assertThrows(EntityNotFoundException.class, () -> basketMessengerService.getMessage(INVALID_MESSAGE_ID));
	}

	@Test
	public void shouldThrowExceptionAfterTryToSaveEmptyEntity() {
		//given
		BasketMessengerTO basketMessenger = new BasketMessengerTO();

		//then
		assertThrows(ExhaustedRetryException.class, () -> basketMessengerService.receiveMessage(basketMessenger));
	}

	@Test
	public void shouldPublishRetrieveAndSaveMassageToDatabase() {
		//given
		BasketMessengerTO basketMessenger = createBasketMessenger();

		//when
		jmsTemplate.convertAndSend(QUEUE_MESSAGE, basketMessenger);
		doWait(3);

		//then
		BasketMessengerTO message = basketMessengerService.getMessage(BASKET_MESSAGE_ID);
		Assertions.assertThat(message.getProductId()).isEqualTo(PRODUCT_ID);
		Assertions.assertThat(message.getUserId()).isEqualTo(USER_ID);
	}

	private static void doWait(int seconds) {
		try {
			sleep(seconds * MILLIS);
		} catch (InterruptedException e) {
			throw new RuntimeException("The execution has been interrupted", e);
		}
	}

	private static BasketMessengerTO createBasketMessenger() {
		return BasketMessengerTO.builder()
				.productId(PRODUCT_ID)
				.userId(USER_ID)
				.productName(PRODUCT_NAME)
				.build();
	}

}
