package com.mybizcopilot.config;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class OkHttpConfig {


    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(30))
                .callTimeout(Duration.ofSeconds(30))
                .addInterceptor(new HeaderInterceptor())
                .readTimeout(Duration.ofSeconds(20))
                .build();
    }

    //Ajout automatique des en-têtes
    public static class HeaderInterceptor implements Interceptor {

        /**
         * @param chain
         * @return
         * @throws IOException
         */
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
           Request original = chain.request();
           Request request = original.newBuilder()
                   .header("content-type", "application/x-www-form-urlencoded")
                   .build();
           return chain.proceed(request);
        }
    }
}
