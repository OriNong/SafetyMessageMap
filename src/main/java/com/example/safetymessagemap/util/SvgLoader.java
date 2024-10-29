package com.example.safetymessagemap.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * resource 폴더에서 svg 파일 컨트롤 Util 클래스
 */
@Slf4j
public class SvgLoader {
    // 문자열로 조합된 SVG 값을 담을 변수
    private String svgString;

    /**
     * 시/도에 해당하는 시/군/구 svg
     * @param region : 지역명을 영어 소문자로 받음
     * @return : String으로 조합된 SVG값
     * @throws IOException
     */
    public String getSvgContent(String region) throws IOException {
        svgString = "";

            log.info(region);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(SvgLoader.class.getClassLoader().getResourceAsStream("static/svg/" + region + ".txt")))) {
                svgString = br.lines().collect(Collectors.joining("\n"));
                return svgString;
            } catch (IOException e) {
                System.out.println("파일을 읽는 중 오류 발생: " + e.getMessage());

        }
            // 시/군/구 svg 파일 String으로 변환해서 return
        return svgString;
    }
}