package com.example.safetymessagemap.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiUrlBuilder {

    // numOfRows를 고정값으로 설정
    private static final int NUM_OF_ROWS = 1000;

    // API 기본 URL
    private static final String BASE_URL = "https://www.safetydata.go.kr/V2/api/DSSP-IF-00247";

    // service 레이어에서 @Value로 할당된 api key 값을 파라미터로 받는다.
    public String getSafetyServiceURL(String safetyDataServiceKey){
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
