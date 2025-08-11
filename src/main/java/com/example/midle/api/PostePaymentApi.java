 package com.example.midle.api;

import com.example.midle.dto.FillFormResponseDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface PostePaymentApi {
    @FormUrlEncoded
    @POST("acs/pareq")
    Call<FillFormResponseDto> getRequestId(
            @Query("network") String mastercard,
            @Field("MD") String mdOrder,
            @Query("PaReq") String paReq,
            @Query("TermUrl") String termUrl);

}