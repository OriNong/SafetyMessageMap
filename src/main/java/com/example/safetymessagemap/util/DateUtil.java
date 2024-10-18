package com.example.safetymessagemap.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j

public class DateUtil {
    /**
     * 현재 날짜를 yyyyMMdd 형식으로 반환하는 메서드
     * @return
     */
    public static String getCurrentDate() {
        // 날짜 형식 지정
        final String dateFormat = "yyyyMMdd";

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        log.info(currentDate.format(formatter));
        // 포맷된 날짜 반환 -> ex) 20241018
        return currentDate.format(formatter);
    }
}
