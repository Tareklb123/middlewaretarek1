package com.example.midle.Controller;

import com.example.midle.Service.SatimPaymentService;
import com.example.midle.dto.*;
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
    @PostMapping("/fillForm")
    public FillFormResponseDto fillForm(@RequestBody FillFormRequestDto request){
        return SatimPaymentService.fillForm(request);
    }
}

