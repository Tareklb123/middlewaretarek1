package com.example.midle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestOtpResponseDto {
    private String mdOrder;
    private String requestId;
    private String javax;
    private String errorCode;
    private String Message;
    private String bankType;
}

