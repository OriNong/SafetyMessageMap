package com.example.safetymessagemap.controller;

import com.example.safetymessagemap.util.SvgLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@RequestMapping("/map")
@Slf4j
public class MapController {

    private final SvgLoader svgLoader = new SvgLoader();

    /**
     * 클라이언트로 부터 시/도를 입력받아 해당 지역의 시/군/구 svg return
     * @param region : 시/도 영어 소문자
     * @return : svg -> String
     */
    @GetMapping("/map")
    @ResponseBody
    public String getMap(@RequestParam("region") String region) {
        log.info("컨트롤러 : " + region);
        try {
            String result = svgLoader.getSvgContent(region);
            log.info(result);
            return result;
        } catch (IOException e) {
            return "Error occurred while loading SVG content: " + e.getMessage();
        }
    }
}
