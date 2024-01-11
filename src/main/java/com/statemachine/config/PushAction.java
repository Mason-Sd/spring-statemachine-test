package com.statemachine.config;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import com.statemachine.constants.Events;
import com.statemachine.constants.States;
import com.statemachine.service.VendingMachineService;

public class PushAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> stateContext) {
        switch (stateContext.getEvent()) {
            case PushAppleJuice:
                VendingMachineService.setInsertedCents(0);
                VendingMachineService.setBeverage("Apple Juice");
                break;
            case PushOrangeJuice:
                VendingMachineService.setInsertedCents(0);
                VendingMachineService.setBeverage("Orange Juice");
                break;
        }
    }
}
