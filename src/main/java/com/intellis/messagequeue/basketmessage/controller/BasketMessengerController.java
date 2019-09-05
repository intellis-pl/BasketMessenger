package com.intellis.messagequeue.basketmessage.controller;

import com.intellis.messagequeue.basketmessage.dto.BasketMessengerDTO;
import com.intellis.messagequeue.basketmessage.service.BasketMessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basketmessenger")
public class BasketMessengerController {
    private final BasketMessengerService basketMessengerService;

    @Autowired
    public BasketMessengerController(BasketMessengerService basketMessengerService) {
        this.basketMessengerService = basketMessengerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketMessengerDTO> retrieve(@PathVariable Long id) {
        BasketMessengerDTO message = basketMessengerService.getMessage(id);
        return ResponseEntity.ok(message);
    }
}
