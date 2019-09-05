package com.intellis.messagequeue.basketmessage.mapper;

import com.intellis.messagequeue.basketmessage.dto.BasketMessengerDTO;
import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    BasketMessengerEntity map(BasketMessengerDTO basketMessengerDTO);
    BasketMessengerDTO map(BasketMessengerEntity basketMessengerEntity);

}
