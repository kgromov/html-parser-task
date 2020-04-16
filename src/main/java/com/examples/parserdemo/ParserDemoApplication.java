package com.examples.parserdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ParserDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParserDemoApplication.class, args);
    }

}
