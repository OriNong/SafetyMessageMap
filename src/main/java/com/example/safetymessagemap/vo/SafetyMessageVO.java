package com.example.safetymessagemap.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SafetyMessageVO {
    private Long sn;                // 일련번호
    private String msgCn;           // 메시지 내용
    private String rcptnRgnNm;      // 수신 지역명
    private LocalDateTime crtDt;    // 생성 일시
    private LocalDateTime regYmd;   // 등록 일자
    private String emegstepNm;      // 긴급 단계 명
    private String dstSeNm;         // 재해 구분 명
    private LocalDateTime mdfcnYmd; // 수정 일자
}
