package com.cardservice.command.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisputeEvent {

    private String cardId;
    private String transactionId;
    private String reason;

}