package com.cardservice.command.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefundEvent {

    private String transactionId;
    private String cardId;

}
