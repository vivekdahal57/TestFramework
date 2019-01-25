package com.vh.mi.automation.impl.reporting;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by i10314 on 6/26/2017.
 */
public class HTMLReportWriter extends HTMLContents {

    private static final String HTML_FILE_PATH = "target";
    public static void generateHTMLReport(LinkedList<String[]> Table, String body, String fileName)
    {
        try {

            StringBuilder htmlStringBuilder = new StringBuilder();

            htmlStringBuilder.append(HTML_HEAD);

            htmlStringBuilder.append(body);
            for (String[] rows : Table){
                htmlStringBuilder.append("<tr>");
                for (String data : rows){
                    htmlStringBuilder.append("<td><small>"+data+"</small></td>");
                }
                htmlStringBuilder.append("</tr>");
            }

            htmlStringBuilder.append(HTML_CLOSING);
            WriteToFile(htmlStringBuilder.toString(), fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void WriteToFile(String fileContent, String fileName) throws IOException {

        String projectPath = HTML_FILE_PATH;
        String tempFile = projectPath + File.separator+fileName;
        File file = new File(tempFile);

        if (file.exists()) {
           file.delete();
        }

        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();
    }
}

