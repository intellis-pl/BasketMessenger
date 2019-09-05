package com.intellis.messagequeue.basketmessage.repository;

import com.intellis.messagequeue.basketmessage.entity.BasketMessengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketMessengerRepository extends JpaRepository<BasketMessengerEntity, Long> {
}
