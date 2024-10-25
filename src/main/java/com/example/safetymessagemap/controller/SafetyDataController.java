package com.example.safetymessagemap.controller;

import com.example.safetymessagemap.service.SafetyMessageService;
import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SafetyDataController {

    @Autowired
    private SafetyMessageService safetyMessageService;

    /**
     * 지역명을 문자열로 받아 해당 지역에 발령된 안전 재난 메시지 return
     * @param region : html에서 받아온 지역명
     * @return : List<SafetyMessageVO>
     */
    @GetMapping("/alerts") // 추후 수정
    public List<SafetyMessageVO> getMessageByRegion(@RequestParam(required = false, defaultValue = "경기도 김포시") String region) {
        // defaultValue는 동작 테스트를 하기 위해 넣어둔 값 추후 삭제
        return safetyMessageService.getSafetyMessageByRegion(region);
    }
}
