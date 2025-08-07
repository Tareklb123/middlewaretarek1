package com.example.midle.api;


import com.example.midle.dto.FillFormResponseDto;
import com.example.midle.dto.ResponseInitDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface SatimPaymentApi {
        @GET("/payment/rest/register.do")
        Call<ResponseInitDto> initPayment(
                @Query("userName") String username,
                @Query("password") String password,
                @Query("amount") String amount,
                @Query("orderNumber") String orderNumber,
                @Query("language") String language,
                @Query("currency") String currency,
                @Query("returnUrl") String returnUrl,
                @Query("failUrl") String failUrl,
                @Query("jsonParams") String jsonParams
        );

        @FormUrlEncoded
        @POST("/payment/rest/processform.do")
        Call<FillFormResponseDto> fillForm(
                @Query("MDORDER") String mdorder,
                @Query("$EXPIRY") String expiry,
                @Query("$PAN") String pan,
                @Query("MM") String expiryMonth,
                @Query("YYYY") String expiryYear,
                @Query("TEXT") String text,
                @Query("$CVC") String cvv,
                @Query("language") String language
        );


}
