package com.example.safetymessagemap.service;

import com.example.safetymessagemap.mapper.SafetyMessageMapper;
import com.example.safetymessagemap.util.ApiUrlBuilder;
import com.example.safetymessagemap.vo.SafetyMessageListVO;
import com.example.safetymessagemap.vo.SafetyMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor // final 또는 @NotNull이 붙은 필드의 생성자를 자동으로 생성
public class SafetyMessageService {

    private final SafetyMessageMapper safetyMessageMapper;
    private RestTemplate restTemplate;
    // 안전재난문자 api 키값과 현재 날짜로 조립한 전체 url 반환하는 객체 생성
    private ApiUrlBuilder apiUrlBuilder;

    // Api를 호출해 Json 데이터를 가져오는 메서드
    private void fetchSafetyDataFromApi(){
        String safetyRequestUrl = apiUrlBuilder.getSafetyServiceURL();
        try {
            // 반환된 Json body 배열 데이터를 저장
            SafetyMessageListVO response = restTemplate.getForObject(safetyRequestUrl, SafetyMessageListVO.class);

            // body 데이처 처리 loop
            for (SafetyMessageVO safetyMessageVO : response.getApiResponseBody()){

            }
        } catch (Exception e) {
            //to do something
        }

    }

    // 반환된 Json 데이터를 VO 형식에 맞게 파싱하는 메서드
    private void parseSafetyDate(){}

    // Json 데이터 DB 조회
    private void findExistingSafetyData(){}

    // 신규 데이터와 수정할 데이터로 분류
    private void classifyNewAndUpdateSafetyData(){}

    // 신규 데이터 삽입
    private void insertSafetyData(){}

    // 기존 데이터 수정
    private void updateSafetyData(){}
}
