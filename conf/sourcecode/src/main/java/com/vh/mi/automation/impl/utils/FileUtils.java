package com.vh.mi.automation.impl.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nimanandhar on 11/6/2014.
 */
public class FileUtils {

    public static List<String> getLinesFromResourceFile(String fileName) {
        return getLines(FileUtils.class.getClassLoader().getResource(fileName).getFile());
    }

    private static List<String> getLines(String fileName) {
        List<String> lines = new ArrayList<>();
        String s;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while ((s = bufferedReader.readLine()) != null) {
                String line = s.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading resource file " + fileName);
        }
        return lines;
    }
}
