package com.example.midle.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FillFormRequestDto {
    private String MDORDER;
    private String EXPIRY;
    private String PAN;
    private String MM;
    private String YYYY;
    private String TEXT;
    private String CVV;

}
