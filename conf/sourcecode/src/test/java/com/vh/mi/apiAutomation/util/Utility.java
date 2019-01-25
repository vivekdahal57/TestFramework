package com.vh.mi.apiAutomation.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by i82718 on 3/6/2018.
 */
public class Utility {
    private static final String TEST_SQL_BASEPATH = "sql/";

    public static String readFileAsString(String path) {
        URL url = Resources.getResource(TEST_SQL_BASEPATH + path);
        try {
            return normalizeSQL(Resources.toString(url, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String normalizeSQL(String in) {
        return in.replaceAll(" (\\S+sfw\\S+) ", "co_sfw_table").replaceAll("\\s", "").toLowerCase();
    }

    public static List<String> findQueryParams(String input, String toFind) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(toFind+"\\S+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
