package com.statemachine.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseModel {
    private final int insertedCents;
    private final String beverage;
    private final String currentState;

    public static ResponseModel from(
            final int insertedCents,
            final String beverage,
            final String currentState) {
        return ResponseModel.builder()
                .insertedCents(insertedCents)
                .beverage(beverage)
                .currentState(currentState)
                .build();
    }
}
