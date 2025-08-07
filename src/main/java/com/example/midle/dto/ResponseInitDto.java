package com.example.midle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseInitDto {

        private String orderId;
        private String message;
        private String formUrl;
        private String errorMessage;

}

