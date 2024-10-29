package com.example.safetymessagemap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DisasterController {

    @PostMapping("/disaster")
    @ResponseBody
    public String getDisasterMessage(@RequestParam("region") String region,
                                     @RequestParam("subregion") String subregion) {
        return findDisasterMessage(region, subregion);
    }

    private String findDisasterMessage(String region, String subregion) {
        return "재난문자 예시: " + region + " " + subregion + " 지역의 재난 안내";
    }
}