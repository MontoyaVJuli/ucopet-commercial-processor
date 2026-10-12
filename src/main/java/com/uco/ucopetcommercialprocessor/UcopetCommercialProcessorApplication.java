package com.uco.ucopetcommercialprocessor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UcopetCommercialProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                UcopetCommercialProcessorApplication.class,
                args
        );
    }
}
