package com.example.midle.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FillFormResponseDto {
    private String acsUrl;
    private String paReq;
    private String termUrl;
    private String errorCode;
    private String error;

    public boolean isPoste() {
        return acsUrl != null && acsUrl.contains("poste.dz");
    }

}

