package com.gardenlog.servlet.batch;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;

public class HwpxDownloader {

    public static Path download(String hwpxUrl, String cropCode) {
        try {
            URL url = new URL(hwpxUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Path dir = Paths.get("downloads");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path target = dir.resolve("schedule_" + cropCode + ".hwpx");

            try (InputStream in = conn.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            return target;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

