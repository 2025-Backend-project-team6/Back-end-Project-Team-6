package com.gardenlog.servlet.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.gardenlog.servlet.dto.CropInfoDTO;

public class HwpxParser {

    private static final String PARA_NS  = "http://www.hancom.co.kr/hwpml/2011/paragraph";
    private static final String TABLE_NS = "http://www.hancom.co.kr/hwpml/2011/table";

    public static List<CropInfoDTO> parse(InputStream xml, String cropCode, int section) {

        List<CropInfoDTO> list = new ArrayList<>();
        int order = 0;

        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(xml);

            /* ===========================
               1) 문단 <p> 파싱
               =========================== */
            NodeList pList = doc.getElementsByTagNameNS(PARA_NS, "p");
            for (int i = 0; i < pList.getLength(); i++) {
                String text = extract(pList.item(i), PARA_NS);
                if (!text.isBlank()) {
                    CropInfoDTO dto = new CropInfoDTO();
                    dto.setCropCode(cropCode);
                    dto.setSection(section);
                    dto.setOrderNo(++order);
                    dto.setContent(text);
                    list.add(dto);
                }
            }

            /* ===========================
               2) 표 <tbl> 파싱
               =========================== */
            NodeList tblList = doc.getElementsByTagNameNS(TABLE_NS, "tbl");
            for (int t = 0; t < tblList.getLength(); t++) {

                Node tbl = tblList.item(t);
                NodeList rows = tbl.getChildNodes();

                for (int r = 0; r < rows.getLength(); r++) {
                    Node tr = rows.item(r);
                    if (!is(tr, "tr", TABLE_NS)) continue;

                    NodeList cells = tr.getChildNodes();
                    for (int c = 0; c < cells.getLength(); c++) {
                        Node tc = cells.item(c);
                        if (!is(tc, "tc", TABLE_NS)) continue;

                        String text = extract(tc, PARA_NS);
                        if (!text.isBlank()) {
                            CropInfoDTO dto = new CropInfoDTO();
                            dto.setCropCode(cropCode);
                            dto.setSection(section);
                            dto.setOrderNo(++order);
                            dto.setContent(text);
                            list.add(dto);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static boolean is(Node n, String name, String ns) {
        return n.getNodeType() == Node.ELEMENT_NODE &&
               name.equals(n.getLocalName()) &&
               ns.equals(n.getNamespaceURI());
    }

    private static String extract(Node n, String paraNs) {
        StringBuilder sb = new StringBuilder();

        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node x = children.item(i);

            if (x.getNodeType() == Node.ELEMENT_NODE &&
                "t".equals(x.getLocalName()) &&
                paraNs.equals(x.getNamespaceURI())) {

                sb.append(x.getTextContent()).append(" ");
            }

            if (x.getNodeType() == Node.ELEMENT_NODE &&
               ( "run".equals(x.getLocalName()) ||
                 "p".equals(x.getLocalName())   )) {

                sb.append(extract(x, paraNs)).append(" ");
            }
        }

        return sb.toString().trim();
    }
}
