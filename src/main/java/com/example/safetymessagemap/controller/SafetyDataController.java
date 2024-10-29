package com.example.safetymessagemap.controller;

import com.example.safetymessagemap.service.SafetyMessageService;
import com.example.safetymessagemap.vo.SafetyMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/message") // 추후 수정
    public Model getMessageByRegion(@RequestParam(required = false, defaultValue = "경기도 김포시") String region, Model model) {
        // defaultValue는 동작 테스트를 하기 위해 넣어둔 값 추후 삭제
        List<SafetyMessageVO> messageList = safetyMessageService.getSafetyMessageByRegion(region);

        if (messageList == null || messageList.isEmpty()) {
            // 메시지가 없는 경우
            model.addAttribute("errorMessage", region + " 지역에 메시지가 존재하지 않습니다.");
            model.addAttribute("hasMessages", false);
        } else {
            // 메시지가 존재하는 경우
            model.addAttribute("messageList", messageList);
            model.addAttribute("hasMessages", true);
        }

        return model;
    }

}
