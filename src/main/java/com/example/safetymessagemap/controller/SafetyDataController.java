package com.example.safetymessagemap.controller;

import com.example.safetymessagemap.service.SafetyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SafetyDataController {

    @Autowired
    private SafetyMessageService safetyMessageService;

    @RequestMapping("/")
    // 2초마다 api 호출 트리거
    public void scheduleSafetyDataFetch(){
        safetyMessageService.fetchSafetyDataFromApi();
    }
}
