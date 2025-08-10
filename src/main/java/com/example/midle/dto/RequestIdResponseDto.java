package com.example.midle.dto;

import lombok.Getter;
import lombok.Setter;
import pl.droidsonroids.jspoon.annotation.Selector;

@Getter
@Setter
public class RequestIdResponseDto {


    @Selector(value = "#request_id", attr = "value", defValue = "")
    private String requestId;
}


