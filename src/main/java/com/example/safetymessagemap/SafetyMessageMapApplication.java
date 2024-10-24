package com.example.safetymessagemap;

import com.example.safetymessagemap.service.SafetyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling // 스케줄링 활성화
public class SafetyMessageMapApplication implements CommandLineRunner {

    @Autowired
    private SafetyMessageService safetyMessageService;

    public static void main(String[] args) {
        SpringApplication.run(SafetyMessageMapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        safetyMessageService.resetSafetyMessageTable();
        safetyMessageService.fetchSafetyDataFromApi();
    }
}
