package com.example.safetymessagemap.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SafetyMessageVO {
    private Long sn;
    private String msgCn;
    private String rcptnRgnNm;
    private LocalDateTime crtDt;
    private LocalDateTime regYmd;
    private String emegstepNm;
    private String dstSeNm;
    private LocalDateTime mdfcnYmd;
}
