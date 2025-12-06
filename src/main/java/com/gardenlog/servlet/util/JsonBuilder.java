package com.gardenlog.servlet.util;

import java.util.*;
import com.gardenlog.servlet.dto.CropInfoDTO;
import com.google.gson.Gson;

public class JsonBuilder {

    public static String build(List<CropInfoDTO> list) {

        Map<String, List<String>> map = new LinkedHashMap<>();
        String currentSection = "기타";

        for (CropInfoDTO dto : list) {
            String text = dto.getContent().trim();

            // ■ 로 시작하면 섹션
            if (text.startsWith("■")) {
                currentSection = text.replace("■", "").trim();
                map.putIfAbsent(currentSection, new ArrayList<>());
                continue;
            }

            // 내용 추가
            map.putIfAbsent(currentSection, new ArrayList<>());
            map.get(currentSection).add(text);
        }

        return new Gson().toJson(map);
    }
}
