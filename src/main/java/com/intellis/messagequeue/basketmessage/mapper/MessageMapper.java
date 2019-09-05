package com.intellis.messagequeue.basketmessage.mapper;

import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import com.intellis.messagequeue.basketmessage.to.BasketMessengerTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    BasketMessengerEntity map(BasketMessengerTO basketMessengerTO);
    BasketMessengerTO map(BasketMessengerEntity basketMessengerEntity);

}
