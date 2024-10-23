package com.example.safetymessagemap.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SafetyMessageListVO {
    private List<SafetyMessageVO> apiResponseBody;
}
