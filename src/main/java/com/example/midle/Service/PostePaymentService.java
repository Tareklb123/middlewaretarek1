package com.example.midle.Service;

import com.example.midle.api.PostePaymentApi;
import com.example.midle.api.SatimPaymentApi;
import com.example.midle.dto.FillFormResponseDto;
import com.example.midle.dto.RequestOtpResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.droidsonroids.retrofit2.JspoonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"resource", "DataFlowIssue"})
@Service
public class PostePaymentService {
    private static PostePaymentApi posteAcs = null;
    private final SatimPaymentService satimPaymentService;
    private String currentRequestId;
    private String javax;
    private String currentPares;
    private String currentMdOrder;

    public PostePaymentService(@Lazy SatimPaymentService satimPaymentService) {
        this.satimPaymentService = satimPaymentService;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .followRedirects(false)
                .build();

        this.posteAcs = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://epay.poste.dz")
                .addConverterFactory(JspoonConverterFactory.create())
                .build()
                .create(PostePaymentApi.class);
    }

    public static final Logger log = LoggerFactory.getLogger(PostePaymentService.class);

    public static String extractRequestIdFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        String paramName = "request_id=";
        int startIndex = url.indexOf(paramName);

        if (startIndex == -1) {
            return null;
        }

        startIndex += paramName.length();

        int endIndex = url.indexOf('&', startIndex);
        if (endIndex == -1) {
            endIndex = url.length();
        }

        return url.substring(startIndex, endIndex);
    }

    public static Response<FillFormResponseDto> getposteRequestId(String mdOrder, FillFormResponseDto responseFillFormDto) {
        try {
            Response<FillFormResponseDto> response = posteAcs.getRequestId(
                    "MASTERCARD",
                    mdOrder,
                    responseFillFormDto.getPaReq(),
                    responseFillFormDto.getTermUrl()
            ).execute();

            if (response.code() == 301 || response.code() == 302) {
                return Response.success(null, response.headers());
            } else {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




