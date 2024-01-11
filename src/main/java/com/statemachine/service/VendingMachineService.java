package com.statemachine.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.statemachine.constants.Events;
import com.statemachine.constants.States;
import com.statemachine.model.ResponseModel;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class VendingMachineService {
    private final StateMachine<States, Events> stateMachine; // 자판기 statemachine

    @Getter
    @Setter
    public static int insertedCents = 0; // 투입된 금액

    @Getter
    @Setter
    public static String beverage; // 뽑은 음료수

    @PostConstruct
    private void init() {
        this.stateMachine.startReactively(); // start is deprecated
        log.info("vending machine created!");
    }

    public ResponseModel insertNicel() {
        this.stateMachine.sendEvent(Mono.just(createEvent(Events.InsertNickel))); // InsertNickel 이벤트 발생
        return ResponseModel.from(this.insertedCents, null, stateMachine.getState().getId().toString());
    }

    public ResponseModel insertDime() {
        this.stateMachine.sendEvent(Mono.just(createEvent(Events.InsertDime))); // InsertDime 이벤트 발생
        return ResponseModel.from(this.insertedCents, null, stateMachine.getState().getId().toString());
    }

    public ResponseModel insertQuarter() {
        this.stateMachine.sendEvent(Mono.just(createEvent(Events.InsertQuarter))); // InsertQuarter 이벤트 발생
        return ResponseModel.from(this.insertedCents, null, stateMachine.getState().getId().toString());
    }

    public ResponseModel pushOrangeJuice() {
        this.stateMachine.sendEvent(Mono.just(createEvent(Events.PushOrangeJuice))); // PushOrangeJuice 이벤트 발생
        return ResponseModel.from(this.insertedCents, this.beverage, stateMachine.getState().getId().toString());
    }

    public ResponseModel pushAppleJuice() {
        this.stateMachine.sendEvent(Mono.just(createEvent(Events.PushAppleJuice))); // PushAppleJuice 이벤트 발생
        return ResponseModel.from(this.insertedCents, this.beverage, stateMachine.getState().getId().toString());
    }

    private Message<Events> createEvent(
            // Header header,
            Events event) {
        MessageBuilder<Events> messageBuilder = MessageBuilder.withPayload(event);
        // messageBuilder.setHeader(header, messageBuilder); //header가 필요한 경우..
        return messageBuilder.build();
    }
}
