package com.example.safetymessagemap.service;

import com.example.safetymessagemap.mapper.SafetyMessageMapper;
import com.example.safetymessagemap.util.ApiUrlBuilder;
import com.example.safetymessagemap.vo.SafetyMessageVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
// @RequiredArgsConstructor // final 또는 @NotNull이 붙은 필드의 생성자를 자동으로 생성
public class SafetyMessageService {

    @Autowired
    private SafetyMessageMapper safetyMessageMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    // 안전재난문자 api 키값과 현재 날짜로 조립한 전체 url 반환하는 객체 생성
    private final ApiUrlBuilder apiUrlBuilder = new ApiUrlBuilder();

    // application-API-KEY.properties에서 api.key 값을 주입받음
    @Value("${safetyData-serviceKey}")
    private String safetyDataServiceKey;

    // 24시 00분 00초가 되면 db 테이블 초기화
    @Scheduled(cron = "59 59 23 * * *", zone = "Asia/Seoul")
    public void resetSafetyMessageTable() {
        safetyMessageMapper.resetMessage();
    }

    // Api를 호출해 Json 데이터를 가져오는 메서드
    //@Scheduled(fixedDelay = 2000)
    public void fetchSafetyDataFromApi(){
        String safetyRequestUrl = apiUrlBuilder.getSafetyServiceURL(safetyDataServiceKey);
        log.info(safetyRequestUrl);
        try {
            // 반환된 Json body 배열 데이터를 저장
            ResponseEntity<String> response = restTemplate.getForEntity(safetyRequestUrl, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // 응답의 상태코드가 OK이고 Json 바디가 null이 아닌 경우
                parseSafetyData(response.getBody());
            } else if (response.getBody() == null) {
                // Json body가 null인 경우
                log.error("조회된 결과가 없습니다.");
            } else {
                log.error("API 호출 실패: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("API 처리 중 오류 발생", e);
        }

    }

    // 반환된 Json 데이터를 VO 형식에 맞게 파싱하는 메서드
    public void parseSafetyData(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode bodyNode = rootNode.get("body");
        log.info(bodyNode.toString());

        if (bodyNode.isArray()) {
            int processedCount = 0;
            for (JsonNode messageNode : bodyNode) {
                SafetyMessageVO message = jsonArrayToVO(messageNode);
                log.info(message.toString());
                upsertMessage(message);
                processedCount++;
            }
            log.info("처리된 안전 메시지 수: {}", processedCount);
        }
    }
    // 추출된 Json 데이터 body 배열의 원소를 SafetyMessageVO로 매핑해 객체 생성 후 리턴
    private SafetyMessageVO jsonArrayToVO(JsonNode node) {
        return SafetyMessageVO.builder()
                .sn(node.get("SN").asLong())
                .msgCn(node.get("MSG_CN").asText())
                .rcptnRgnNm(node.get("RCPTN_RGN_NM").asText())
                .crtDt(parseDateTime(node.get("CRT_DT").asText()))
                .regYmd(parseDateTime(node.get("REG_YMD").asText()))
                .emegstepNm(node.get("EMRG_STEP_NM").asText())
                .dstSeNm(node.get("DST_SE_NM").asText())
                .mdfcnYmd(parseDateTime(node.get("MDFCN_YMD").asText()))
                .build();
    }
    // VO 저장 시 날짜 형식 변환 메서드
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter;
        if (dateTimeStr.contains(".")) {
            // REG_YMD, MDFCN_YMD 형식: "2024/10/01 17:13:00.000000000"
            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSSSSSSSS");
        } else {
            // CRT_DT 형식: "2024/10/01 17:12:54"
            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        }

        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    @Transactional
    public void upsertMessage(SafetyMessageVO message) {
        SafetyMessageVO existingMessage = safetyMessageMapper.findBySn(message.getSn());

        if (existingMessage != null) {
            // 기존 메시지가 있는 경우, 수정 시간 비교 후 업데이트
            if (message.getMdfcnYmd().isAfter(existingMessage.getMdfcnYmd())) {
                log.info("안전 재난 메시지 업데이트 - SN: {}", message.getSn());
                safetyMessageMapper.updateMessage(message);
            }
        } else {
            // 새로운 메시지인 경우 insert
            log.info("새로운 안전 메시지 저장 - SN: {}", message.getSn());
            safetyMessageMapper.insertMessage(message);
        }
    }
    // 지역명으로 메시지 DB 조회 후 반환
    public List<SafetyMessageVO> getSafetyMessageByRegion(String region) {
        return safetyMessageMapper.findMessageByRegion(region);
    }


    // Json 데이터 DB 조회
    private void findExistingSafetyData(){}

    // 신규 데이터와 수정할 데이터로 분류
    private void classifyNewAndUpdateSafetyData(){}

    // 신규 데이터 삽입
    private void insertSafetyData(){}

    // 기존 데이터 수정
    private void updateSafetyData(){}
}
