package com.statemachine.config;

import com.statemachine.constants.Events;
import com.statemachine.constants.States;
import com.statemachine.service.VendingMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


// ref : https://dev.gmarket.com/52

@Configuration
@EnableStateMachine
@Slf4j
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Bean
    InsertAction insertAction() {
        return new InsertAction();
    }
//
//    @Bean
//    InsertGuard insertGuard() {
//        return new InsertGuard();
//    }

    @Bean
    PushAction pushAction() {
        return new PushAction();
    }

    @Bean
    PushGuard pushGuard() {
        return new PushGuard();
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.S0)
                .state(States.S5)
                .state(States.S10)
                .state(States.S15)
                .state(States.S20)
                .state(States.S25)
                .state(States.S30)
        // .end(States.End) //최종 상태가 존재하는 경우
        ;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        // state는 S30이 최대일 떄
        transitions
                // 1) InsertNickel transitions
                .withExternal()
                .source(States.S0).target(States.S5).event(Events.InsertNickel)
                .action(insertAction())
                // S0 상태에서 InsertNickel 입력(이벤트)이 주어지면 S5 상태로 transition
                .and()
                .withExternal()
                .source(States.S5).target(States.S10).event(Events.InsertNickel)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S10).target(States.S15).event(Events.InsertNickel)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S15).target(States.S20).event(Events.InsertNickel)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S20).target(States.S25).event(Events.InsertNickel)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S25).target(States.S30).event(Events.InsertNickel)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S30).event(Events.InsertNickel)
                // S30 상태에서 InsertNickel 입력이 주어진 경우, 다시 S30 상태로 transition

                // 2) Insert Dime transitions
                .and()
                .withExternal()
                .source(States.S0).target(States.S10).event(Events.InsertDime)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S5).target(States.S15).event(Events.InsertDime)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S10).target(States.S20).event(Events.InsertDime)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S15).target(States.S25).event(Events.InsertDime)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S20).target(States.S30).event(Events.InsertDime)
                .action(insertAction())
                // S25 상태부턴 InsertDime 입력이 주어진 경우, 자신 상태로 transition
                .and()
                .withExternal()
                .source(States.S25).event(Events.InsertDime)
                .and()
                .withExternal()
                .source(States.S30).event(Events.InsertDime)

                // 3) Insert Quarter transitions
                .and()
                .withExternal()
                .source(States.S0).target(States.S25).event(Events.InsertQuarter)
                .action(insertAction())
                .and()
                .withExternal()
                .source(States.S5).target(States.S30).event(Events.InsertQuarter)
                .action(insertAction())
                // S10 상태부턴 InsertDime 입력이 주어진 경우, 자신 상태로 transition
                .and()
                .withExternal()
                .source(States.S10).event(Events.InsertQuarter)
                .and()
                .withExternal()
                .source(States.S15).event(Events.InsertQuarter)
                .and()
                .withExternal()
                .source(States.S20).event(Events.InsertQuarter)
                .and()
                .withExternal()
                .source(States.S25).event(Events.InsertQuarter)
                .and()
                .withExternal()
                .source(States.S30).event(Events.InsertQuarter)

                // push juice
                // S30 상태에서 주스를 뽑으면 S0 상태로
                .and()
                .withExternal()
                .source(States.S30).target(States.S0).event(Events.PushAppleJuice)
                .guard(pushGuard()).action(pushAction())
                .and()
                .withExternal()
                .source(States.S30).target(States.S0).event(Events.PushOrangeJuice)
                .guard(pushGuard()).action(pushAction());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        StateMachineListenerAdapter<States, Events> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<States, Events> fromState, State<States, Events> toState) {
                // 리스너의 동작을 구현
                log.info("State changed from {} to {}. Current cents {}",
                        fromState == null ? "start": fromState.getId().toString(),
                        toState.getId().toString(),
                        VendingMachineService.getInsertedCents());
            }
        };
        config.withConfiguration().listener(adapter); // 리스너를 등록
    }
}