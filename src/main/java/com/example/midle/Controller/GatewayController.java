package com.example.midle.Controller;

import com.example.midle.Service.SatimPaymentService;
import com.example.midle.dto.FillFormRequestDto;
import com.example.midle.dto.FillFormResponseDto;
import com.example.midle.dto.RequestInitDto;
import com.example.midle.dto.ResponseInitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

public class GatewayController {
    private final SatimPaymentService SatimPaymentService;


    @PostMapping("/init")
    public ResponseInitDto initpayment(@RequestBody RequestInitDto request ){
        return SatimPaymentService.initPayment(request);
    };
    @PostMapping("/fill form")
    public FillFormResponseDto fillform(@RequestBody FillFormRequestDto request ){
        return SatimPaymentService.fillForm(request);
    }
}

