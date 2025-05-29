package com.lukianchykov.сontroller.handler;

import brave.Tracer;
import brave.Tracing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder().build();
    }

    @Bean
    public Tracer tracer(Tracing tracing) {
        return tracing.tracer();
    }
}

