package com.intellis.messagequeue.basketmessage.controller;

import com.intellis.messagequeue.basketmessage.service.BasketMessengerService;
import com.intellis.messagequeue.basketmessage.to.BasketMessengerTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/basketmessenger")
public class BasketMessengerController {
    private final BasketMessengerService basketMessengerService;

    @Autowired
    public BasketMessengerController(BasketMessengerService basketMessengerService) {
        this.basketMessengerService = basketMessengerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketMessengerTO> retrieve(@PathVariable Long id) {
        BasketMessengerTO message = basketMessengerService.getMessage(id);
        return ResponseEntity.ok(message);
    }
}
