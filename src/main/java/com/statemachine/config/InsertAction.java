package com.statemachine.config;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import com.statemachine.constants.Events;
import com.statemachine.constants.States;
import com.statemachine.service.VendingMachineService;

public class InsertAction implements Action<States, Events> {
    private static final int NICKEL = 5;
    private static final int DIME = 10;
    private static final int QUARTER = 25;

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        final int currentCents = VendingMachineService.getInsertedCents();
        switch (stateContext.getEvent()) {
            case InsertNickel:
                VendingMachineService.setInsertedCents(currentCents + NICKEL); // InsertNickel 발생 시 Nickel 투입
                VendingMachineService.setBeverage(null);
                break;
            case InsertDime:
                VendingMachineService.setInsertedCents(currentCents + DIME); // InsertDime 발생 시 DIME 투입
                VendingMachineService.setBeverage(null);
                break;
            case InsertQuarter:
                VendingMachineService.setInsertedCents(currentCents + QUARTER); // InsertQuarter 발생 시 QUARTER 투입
                VendingMachineService.setBeverage(null);
                break;
        }
    }

}
