package com.myself.computerThinking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComputerThinkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerThinkingApplication.class, args);
    }

}

