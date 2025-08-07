package com.example.midle.Service;

import com.example.midle.api.SatimPaymentApi;
import com.example.midle.dto.*;
import jakarta.annotation.Nullable;
import okhttp3.OkHttpClient;
import org.apache.tomcat.util.json.JSONFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Nullable
@Service
@Component
public class SatimPaymentService {
    private final SatimPaymentApi satimPaymentApi;


    public SatimPaymentService() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .followRedirects(false)
                .build();

        this.satimPaymentApi = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://cib.satim.dz")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SatimPaymentApi.class);
    }

    public ResponseInitDto initPayment(RequestInitDto requestDto) {
        try {
            Response<ResponseInitDto> response = satimPaymentApi.initPayment(
                    "teletic784515",
                    "teletic84!451d8",
                    requestDto.getAmount(),
                    requestDto.getOrderNumber(),
                    "fr",
                    "012",
                    "https://mpayback.satim.dz/oauth2_resource/order/success/return/" + requestDto.getOrderNumber(),
                    "https://mpayback.satim.dz/oauth2_resource/order/error/return/" + requestDto.getOrderNumber(),
                    "{\"orderNumber\":\"" + requestDto.getOrderNumber() + "\",\"force_terminal_id\":\"E006000001\"}"
            ).execute();
            if (response.isSuccessful() && response.body() != null) {
                  return response.body();
                } else {
                throw new RuntimeException(response.errorBody().string());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FillFormResponseDto fillForm(FillFormRequestDto requestDto) {
        try {
            Response<FillFormResponseDto> response = satimPaymentApi.fillForm(
                    requestDto.getMdorder(),
                    requestDto.getExpiry(),
                    requestDto.getPan(),
                    requestDto.getMm(),
                    requestDto.getYyyy(),
                    requestDto.getText(),
                    requestDto.getCvv(),
                    "fr"
            ).execute();

              if (response.isSuccessful() && response.body() != null) {
                  FillFormResponseDto fillFormResponseDto = response.body();
                return fillFormResponseDto;
                } else {
                    throw new RuntimeException(response.errorBody().string());
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }}
