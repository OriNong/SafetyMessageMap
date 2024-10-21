package com.example.safetymessagemap.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiUrlBuilder {

    // application-API-KEY.properties에서 api.key 값을 주입받음
    @Value("${safetyData-serviceKey}")
    private String safetyDataServiceKey;

    // numOfRows를 고정값으로 설정
    private static final int NUM_OF_ROWS = 1000;

    // API 기본 URL
    private static final String BASE_URL = "https://www.safetydata.go.kr/V2/api/DSSP-IF-00247";

    public String getSafetyServiceURL(){
        // 현재 날짜를 yyyyMMdd 형식으로 가져옴
        String crtDt = DateUtil.getCurrentDate();

        // 완성된 URL 생성
        String fullUrl = BASE_URL
                + "?serviceKey=" + safetyDataServiceKey
                + "&crtDt=" + crtDt
                + "&numOfRows=" + NUM_OF_ROWS;
        return fullUrl;
    }
}
