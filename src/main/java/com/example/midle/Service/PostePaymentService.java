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
    private static final PostePaymentApi posteAcs ;
    private final SatimPaymentService satimPaymentService ;
    private String currentRequestId;
    private String javax;
    private String currentPares;
    private String currentMdOrder;

    public PostePaymentService(@Lazy SatimPaymentService satimPaymentService)  {
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
    private static final Logger log = LoggerFactory.getLogger(PostePaymentService.class);

    private static String extractRequestIdFromUrl(String url) {
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

    private static String fetchHtmlFromUrl(String url) {
        try {

            Response<ResponseBody> htmlResponse = posteAcs.getHtmlContent(url).execute();

            if (htmlResponse.isSuccessful() && htmlResponse.body() != null) {
                return htmlResponse.body().string();
            } else {
                throw new RuntimeException("Failed to fetch HTML content from: " + url);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error fetching HTML content: " + e.getMessage(), e);
        }
    }

    private static String extractJavaxViewState(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return "";
        }

        try {
            Document doc = Jsoup.parse(htmlContent);
            Element viewStateElement = doc.select("[id=javax.faces.ViewState]").first();
            return viewStateElement != null ? viewStateElement.val() : "";
        } catch (Exception e) {
            log.error("Error parsing javax.faces.ViewState: ", e);
            return "";
        }
    }

    public static RequestOtpResponseDto getPosteRequestId(String mdOrder, FillFormResponseDto responseFillFormDto) {
        try {
            Response<Void> response = posteAcs.getRequestId(
                    "MASTERCARD",
                    mdOrder,
                    responseFillFormDto.getPaReq(),
                    responseFillFormDto.getTermUrl()
            ).execute();

            if (response.code() == 301 || response.code() == 302) {
                String location = response.headers().get("Location");
                log.info("Redirecting to: {}", location);

                if (location != null) {
                    String requestIdFromUrl = extractRequestIdFromUrl(location);

                    if (requestIdFromUrl != null) {
                        String htmlContent = fetchHtmlFromUrl(location);
                        String javax = extractJavaxViewState(htmlContent);
                        this.currentRequestId = requestIdFromUrl;
                        this.javax = javax;

                        return requestPosteOtp(mdOrder);
                    } else {
                        throw new RuntimeException("Could not extract requestId from Location header: " + location);
                    }
                } else {
                    throw new RuntimeException("No Location header found in redirect response");
                }
            } else {
                throw new RuntimeException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private RequestOtpResponseDto requestPosteOtp(String mdOrder) {
        try {
            Response<ResponseBody> response = posteAcs.requestOtpSms(
                    "authForm",
                    this.currentRequestId,
                    "Send Password",
                    this.javax,
                    "j_id_id56").execute();

            if (response.isSuccessful() && response.body() != null) {
                return new RequestOtpResponseDto(mdOrder, this.currentRequestId, this.javax, "0", "Poste OTP sent successfully", "Poste");
            } else {
                throw new RuntimeException(response.errorBody().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


