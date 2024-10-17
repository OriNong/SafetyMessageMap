package com.example.safetymessagemap.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getCurrentDate() {
        // 날짜 형식 지정
        final String dateFormat = "yyyyMMdd";

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        // 포맷된 날짜 반환
        return currentDate.format(formatter);
    }
}
