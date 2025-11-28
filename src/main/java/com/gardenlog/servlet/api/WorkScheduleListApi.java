package com.gardenlog.servlet.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gardenlog.servlet.dto.CropFileDTO;

public class WorkScheduleListApi {
	private static final String API_KEY = "20251127ZOT87QTHZVYR52XYFGMIG";
	private static final String BASE_URL = "http://api.nongsaro.go.kr/service/farmWorkingPlan/workScheduleLst";
	
	public List<CropFileDTO> fetchCropFile(String cropCode) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("?apiKey=").append(URLEncoder.encode(API_KEY, "UTF-8"));
        urlBuilder.append("&kidofcomdtySeCode=").append(URLEncoder.encode(cropCode, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();

        return parseXml(sb.toString(), cropCode);
    }

    private List<CropFileDTO> parseXml(String xml, String cropCode) throws Exception {

        List<CropFileDTO> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(
                new java.io.ByteArrayInputStream(xml.getBytes("UTF-8")));

        doc.getDocumentElement().normalize();

        NodeList items = doc.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {

            Element item = (Element) items.item(i);

            String contentsNo = getTagValue(item, "cntntsNo");
            String fileUrl = getTagValue(item, "fileDownUrlInfo");
            String fileName = getTagValue(item, "fileName");
            String fileSeCode = getTagValue(item, "fileSeCode");
            String originalFileName = getTagValue(item, "orginlFileNm");
            String title = getTagValue(item, "sj");

            CropFileDTO dto = new CropFileDTO();
            dto.setCrop_code(cropCode);          // 요청했던 작목 코드 그대로 저장
            dto.setContents_no(contentsNo);
            dto.setFile_url(fileUrl);
            dto.setFile_name(fileName);
            dto.setFile_se_code(fileSeCode);
            dto.setOriginal_file_name(originalFileName);
            dto.setTitle(title);

            list.add(dto);
        }

        return list;
    }

    private String getTagValue(Element element, String tagName) {

        if (element.getElementsByTagName(tagName).getLength() == 0)
            return null;

        if (element.getElementsByTagName(tagName).item(0).getFirstChild() == null)
            return null;

        return element.getElementsByTagName(tagName)
                    .item(0)
                    .getFirstChild()
                    .getNodeValue();
    }
}
