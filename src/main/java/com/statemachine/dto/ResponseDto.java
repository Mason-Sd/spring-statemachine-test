package com.statemachine.dto;

import com.statemachine.model.ResponseModel;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseDto {
    private final int insertedCents;
    private final String beverage;
    private final String currentState;

    public static ResponseDto from(final ResponseModel response) {
        return ResponseDto.builder()
                .insertedCents(response.getInsertedCents())
                .beverage(response.getBeverage())
                .currentState(response.getCurrentState())
                .build();
    }
}