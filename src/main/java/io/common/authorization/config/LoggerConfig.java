package io.common.authorization.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import log.munzi.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class LoggerConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor(objectMapper);
    }

}
