package com.statemachine.config;

import com.statemachine.constants.Events;
import com.statemachine.constants.States;
import com.statemachine.service.VendingMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

@Slf4j
public class PushGuard implements Guard<States, Events> {
    @Override
    public boolean evaluate(StateContext<States, Events> stateContext) {
        int currentCents = VendingMachineService.getInsertedCents();
        if(currentCents < 30){
            log.info("Not enough money! {}", currentCents);
            VendingMachineService.setBeverage(null);
            return false; // 투입 금액이 30센트보다 적으면, guard 통과 불가
        }
        log.info("enough money~ {}", currentCents);
        return true; // guard 통과
    }
}