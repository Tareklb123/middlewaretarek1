package com.example.midle.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {

    @Configuration
    public class RetrofitConfig {
        @Bean
        public Retrofit retrofit() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .followRedirects(false).build();
            return new Retrofit.Builder()
                    .baseUrl("https://cib.satim.dz")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

}
