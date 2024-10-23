package com.example.spacecatsmarket;

import org.springframework.boot.SpringApplication;

public class TestSpaceCatsMarketApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpaceCatsMarketApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
