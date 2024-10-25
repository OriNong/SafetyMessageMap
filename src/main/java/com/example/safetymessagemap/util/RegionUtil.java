package com.example.safetymessagemap.util;

import java.util.*;
        import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class RegionUtil {
    private Map<String, List<String>> regionMap;

    public RegionUtil() {
        regionMap = new HashMap<>();
        initializeRegionMap();
    }

    private void initializeRegionMap() {
        // 여기에 모든 시/도와 시/군/구 데이터를 추가합니다.
        addRegion("서울특별시", Arrays.asList("종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"));
        addRegion("부산광역시", Arrays.asList("중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군"));
        addRegion("대구광역시", Arrays.asList("중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"));
        addRegion("인천광역시", Arrays.asList("중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"));
        addRegion("광주광역시", Arrays.asList("동구", "서구", "남구", "북구", "광산구"));
        addRegion("대전광역시", Arrays.asList("동구", "중구", "서구", "유성구", "대덕구"));
        addRegion("울산광역시", Arrays.asList("중구", "남구", "동구", "북구", "울주군"));
        addRegion("세종특별자치시", new ArrayList<>());
        addRegion("경기도", Arrays.asList("수원시", "성남시", "의정부시", "안양시", "부천시", "광명시", "평택시", "동두천시", "안산시", "고양시", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군", "양평군"));
        // ... 나머지 지역 추가
    }

    private void addRegion(String city, List<String> districts) {
        regionMap.put(city, districts);
    }

    public List<String> searchRegion(String input) {
        List<String> results = new ArrayList<>();
        String[] regions = input.split(",");

        for (String region : regions) {
            region = region.trim();
            String[] parts = region.split("\\s+");

            if (parts.length >= 1) {
                String city = parts[0];
                if (regionMap.containsKey(city)) {
                    if (parts.length == 1) {
                        // 시/도만 입력한 경우
                        results.add(city);
                        results.addAll(regionMap.get(city));
                    } else {
                        // 시/도와 시/군/구를 입력한 경우
                        String district = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                        if (regionMap.get(city).contains(district)) {
                            results.add(city + " " + district);
                        }
                    }
                }
            }
        }

        return results;
    }

    public List<String> processJsonData(String jsonString) {
        List<String> processedRegions = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);

            String rcptnRgnNm = rootNode.get("RCPTN_RGN_NM").asText();
            processedRegions = searchRegion(rcptnRgnNm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processedRegions;
    }
}