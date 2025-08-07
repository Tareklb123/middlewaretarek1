package com.example.midle.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FillFormRequestDto {
    private String mdorder;
    private String expiry;
    private String pan;
    private String mm;
    private String yyyy;
    private String text;
    private String cvv;

}
