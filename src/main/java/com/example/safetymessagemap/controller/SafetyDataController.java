package com.example.safetymessagemap.controller;

import com.example.safetymessagemap.service.SafetyMessageService;
import com.example.safetymessagemap.vo.SafetyMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class SafetyDataController {

    @Autowired
    private SafetyMessageService safetyMessageService;

    /**
     * 지역명을 문자열로 받아 해당 지역에 발령된 안전 재난 메시지 return
     * @param region : html에서 받아온 지역명
     * @return : List<SafetyMessageVO>
     */
    @GetMapping("/message")
    public @ResponseBody Map<String, Object> getMessageByRegion(@RequestParam(required = false) String region) {
        log.info("Controller로 전달된 지역값 : " + region);
        Map<String, Object> response = new HashMap<>();
        List<SafetyMessageVO> messageList = safetyMessageService.getSafetyMessageByRegion(region);
        log.info(messageList.toString());

        if (messageList == null || messageList.isEmpty()) {
            response.put("errorMessage","현재" + region + " 지역에 발송된 메시지가 없습니다.");
            response.put("hasMessages", false);
        } else {
            response.put("messageList", messageList);
            response.put("hasMessages", true);
        }
        log.info(response.toString());
        return response;
    }


}
