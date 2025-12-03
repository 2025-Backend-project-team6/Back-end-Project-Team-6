package com.gardenlog.servlet.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/api/farms")
public class FarmSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final String CLIENT_ID = "wPjSjpqQSNytJ4eJcQCV"; 
    private final String CLIENT_SECRET = "Uz8F8K3YOF";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String wgsLat = request.getParameter("lat");
        String wgsLng = request.getParameter("lng");
        
        String btmCoord = null;
        
        if (wgsLat != null && wgsLng != null) {
        	btmCoord = null;
        }

        String query = URLEncoder.encode("주말농장", "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + query + "&display=50";

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
        con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
        
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == 200) { 
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
        }
        
        String inputLine;
        StringBuffer responseData = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            responseData.append(inputLine);
        }
        br.close();

        request.setAttribute("farmDataJson", responseData.toString());

        request.setAttribute("currentLat", wgsLat); 
        request.setAttribute("currentLng", wgsLng);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/farmMapView.jsp");
        dispatcher.forward(request, response);
    }
}