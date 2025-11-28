package com.gardenlog.servlet.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.gardenlog.servlet.dto.CropDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class WorkScheduleGrpListApi {
	private static final String API_KEY = "20251127ZOT87QTHZVYR52XYFGMIG";
	private static final String URL_STR = "http://api.nongsaro.go.kr/service/farmWorkingPlan/workScheduleGrpList";
	
	public List<CropDTO> fetchCrops() throws Exception{
		String requestUrl = URL_STR + "?apiKey=" + API_KEY;
		
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		
		while((line=br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		conn.disconnect();
		
		return parseXml(sb.toString());
	}

	private List<CropDTO> parseXml(String xml) throws Exception {
		List<CropDTO> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes("UTF-8")));

        doc.getDocumentElement().normalize();

        NodeList items = doc.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);

            String codeNm = item.getElementsByTagName("codeNm").item(0).getTextContent();
            String cropCode = item.getElementsByTagName("kidofcomdtySeCode").item(0).getTextContent();
            int sort = Integer.parseInt(item.getElementsByTagName("sort").item(0).getTextContent());

            CropDTO cdto = new CropDTO();
            cdto.setCrop_nm(codeNm);
            cdto.setCrop_code(cropCode);
            cdto.setSort_order(sort);

            list.add(cdto);
        }

        return list;
	}
}
