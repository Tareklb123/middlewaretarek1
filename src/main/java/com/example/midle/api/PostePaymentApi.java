package com.example.midle.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface PostePaymentApi {
    @FormUrlEncoded
    @POST("acs/pareq")
    Call<Void> getRequestId(
            @Query("network") String mastercard,
            @Field("MD") String mdOrder,
            @Field("PaReq") String paReq,
            @Field("TermUrl") String termUrl);

    @POST("acs/pages/enrollment/authentication.jsf")
    Call<ResponseBody> requestOtpSms(
            @Query("authForm") String authForm,
            @Query("request_id") String requestId,
            @Query("sendPasswordButton") String sendPasswordButton,
            @Query("javax.faces.ViewState") String javax,
            @Query("j_id_id56") String jIdId56);

    @GET
    Call<ResponseBody> getHtmlContent(@Url String url);

    @POST("acs/pages/enrollment/authentication.jsf")
    Call<ResponseBody> authenticateOtpPoste(
            @Query("authForm") String authForm,
            @Query("request_id") String requestId,
            @Query("pwdInputVisible") String otp,
            @Query("submitPasswordButton") String Submit,
            @Query("j_id_id58") String jIdId58,
            @Query("javax.faces.ViewState") String javax);


}
