package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gardenlog.servlet.dao.CropDataDAO;
import com.gardenlog.servlet.dto.CropDataDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/detailcrop.do")
public class DetailCropController extends HttpServlet {

    RequestDispatcher dispatcher = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cropid = Integer.parseInt(request.getParameter("cropid"));

        CropDataDAO dao = new CropDataDAO();
        CropDataDTO dto = dao.getCropDataById(cropid);

        if (dto != null && dto.getInfo_json() != null) {

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
            Map<String, List<String>> infoMap = gson.fromJson(dto.getInfo_json(), type);
            dto.setInfoDetail(infoMap);

            /* ==============================
               1) 온도 정보 추출
            ============================== */
            String growTemp = "-";

            for (String key : infoMap.keySet()) {
                if (key.contains("온도")) {
                    List<String> lines = infoMap.get(key);

                    for (String line : lines) {
                        if (line.contains("℃") && !line.startsWith("※")) {
                            growTemp = line.replace("❍", "").trim();
                            break;
                        }
                    }
                }
            }
            dto.setGrowTemp(growTemp);

            /* ==============================
               2) 자동 팁 추출 (온도/수분/일조/토양)
            ============================== */
            List<String> tips = new ArrayList<>();

            for (String key : infoMap.keySet()) {

                List<String> lines = infoMap.get(key);

                // 🌡 온도 팁
                if (key.contains("온도")) {
                    for (String line : lines) {
                        if (line.contains("℃") && !line.startsWith("※") && line.length() < 40) {
                            tips.add("🌡️ " + line.replace("❍", "").trim());
                            break;
                        }
                    }
                }

                // 💧 수분 / 습해
                if (key.contains("수분") || key.contains("습해") || key.contains("물")) {
                    for (String line : lines) {
                        if (!line.startsWith("※")) {
                            tips.add("💧 " + line.replace("❍", "").trim());
                            break;
                        }
                    }
                }

                // ☀️ 햇빛 / 일조 / 광
                if (key.contains("일장") || key.contains("햇빛") || key.contains("광")) {
                    for (String line : lines) {
                        tips.add("☀️ " + line.replace("❍", "").trim());
                        break;
                    }
                }

                // 🌱 토양
                if (key.contains("토양") || key.contains("pH")) {
                    for (String line : lines) {
                        tips.add("🌱 " + line.replace("❍", "").trim());
                        break;
                    }
                }
            }

            // 팁 없으면 기본 안내
            if (tips.isEmpty()) {
                tips.add("ℹ️ 해당 작물은 기본 관리 정보만 제공됩니다.");
            }

            dto.setTips(tips);
        }

        request.setAttribute("crop", dto);

        dispatcher = request.getRequestDispatcher("/JSP/detailCrop.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { }
}
