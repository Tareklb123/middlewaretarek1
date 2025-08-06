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
                @Field("MDORDER") String mdOrder,
                @Field("$PAN") String pan,
                @Field("MM") String expiryMonth,
                @Field("YYYY") String expiryYear,
                @Field("$CVC") String cvc,
                @Field("TEXT") String text,
                @Field("language") String language);


}
