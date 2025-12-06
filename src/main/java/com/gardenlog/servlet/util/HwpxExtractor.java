package com.gardenlog.servlet.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

public class HwpxExtractor {

    public static List<InputStream> extractXmlStreams(Path hwpxPath) {
        List<InputStream> xmlList = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(hwpxPath.toFile()))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory() &&
                        entry.getName().startsWith("Contents/") &&
                        entry.getName().endsWith(".xml")) {

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    zis.transferTo(bos);
                    xmlList.add(new ByteArrayInputStream(bos.toByteArray()));
                }
                zis.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlList;
    }
}
