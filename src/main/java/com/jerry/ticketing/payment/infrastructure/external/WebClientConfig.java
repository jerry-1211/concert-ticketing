//package com.jerry.ticketing.payment.infrastructure.external;
//
//import io.netty.channel.ChannelOption;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.resources.ConnectionProvider;
//
//import java.time.Duration;
//
//@Configuration
//public class WebClientConfig {
//
//    @Bean
//    public WebClient tossPaymentWebClient() {
//        ConnectionProvider provider = ConnectionProvider.builder("toss-payment")
//                .maxConnections(50)
//                .maxIdleTime(Duration.ofSeconds(20))
//                .maxLifeTime(Duration.ofSeconds(60))
//                .evictInBackground(Duration.ofSeconds(30))
//                .build();
//
//        HttpClient httpClient = HttpClient.create(provider)
//                .option(ChannelOption.SO_KEEPALIVE, true)
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
//                .responseTimeout(Duration.ofSeconds(30));
//
//        return WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//    }
//}
