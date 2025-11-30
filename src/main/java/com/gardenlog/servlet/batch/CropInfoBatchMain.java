package com.gardenlog.servlet.batch;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dao.CropFileDAO;
import com.gardenlog.servlet.dao.CropInfoDAO;
import com.gardenlog.servlet.dto.CropFileDTO;
import com.gardenlog.servlet.dto.CropInfoDTO;
import com.gardenlog.servlet.batch.HwpxDownloader;
import com.gardenlog.servlet.util.HwpxExtractor;
import com.gardenlog.servlet.util.HwpxParser;
import com.gardenlog.servlet.util.JsonBuilder;

public class CropInfoBatchMain {

    public static void main(String[] args) {

        System.out.println("=== BATCH START ===");

        List<CropFileDTO> files = new CropFileDAO().getAll();

        for (CropFileDTO file : files) {

            System.out.println("Processing: " + file.getTitle());

            // 1. HWPX 다운로드
            Path hwpx = HwpxDownloader.download(file.getFile_url(), file.getCrop_code());
            if (hwpx == null) continue;

            // 2. XML 파일 추출
            List<InputStream> xmlSections = HwpxExtractor.extractXmlStreams(hwpx);

            // 3. XML 파싱 → 텍스트 리스트(CropInfoDTO)
            int section = 0;
            List<CropInfoDTO> all = new ArrayList<>();

            for (InputStream xml : xmlSections) {
                List<CropInfoDTO> list = HwpxParser.parse(xml, file.getCrop_code(), section);
                all.addAll(list);
                section++;
            }

            // 4. JSON 생성
            String json = JsonBuilder.build(all);

            // 5. DB 저장
            new CropInfoDAO().saveJson(file.getTitle(), json);

            System.out.println(file.getCrop_code() + " 저장 완료");
        }
    }
}
