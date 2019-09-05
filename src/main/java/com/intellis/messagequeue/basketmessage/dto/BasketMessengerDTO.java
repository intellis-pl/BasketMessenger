package com.intellis.messagequeue.basketmessage.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasketMessengerDTO {
    private Long id;
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    @NotNull(message = "User id cannot be null")
    private Long userId;
    @NotEmpty(message = "Product name may not be empty")
    private String productName;
}
