package com.statemachine.controller;

import com.statemachine.dto.ResponseDto;
import com.statemachine.service.VendingMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VendingMachineController {
    private final VendingMachineService service;

    @GetMapping("/insert/nickel")
    public ResponseDto insertNickel() {
        return ResponseDto.from(service.insertNickel());
    }

    @GetMapping("/insert/dime")
    public ResponseDto insertDime() {
        return ResponseDto.from(service.insertDime());
    }

    @GetMapping("/insert/quarter")
    public ResponseDto insertQuarter() {
        return ResponseDto.from(service.insertQuarter());
    }

    @GetMapping("/push/orange")
    public ResponseDto pushOrangeJuice() {
        return ResponseDto.from(service.pushOrangeJuice());
    }

    @GetMapping("/push/apple")
    public ResponseDto pushAppleJuice() {
        return ResponseDto.from(service.pushAppleJuice());
    }
}
