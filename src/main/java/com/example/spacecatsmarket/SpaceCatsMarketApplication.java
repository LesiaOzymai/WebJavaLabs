package com.example.spacecatsmarket;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class SpaceCatsMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceCatsMarketApplication.class, args);
    }

}