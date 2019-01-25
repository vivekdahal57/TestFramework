package com.vh.mi.automation.impl.reporting;

/**
 * Created by i10314 on 6/27/2017.
 */
public class HTMLContents {
    public static String REPORT_FILE_NAME = "index.html";
    public static String BEFORE_AFTER_FILE_NAME = "beforeAfter.html";

    public static String HTML_HEAD ="" +
            "<html>" +
                "<head>" +
                    "<script src=\"https://code.jquery.com/jquery-1.12.4.js\">"+"</script>" +
                    "<script src=\"https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js\"></script>" +
                    "<link rel=\"stylesheet\" href=\"https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css\">" +
                    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">" +
                    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">" +
                    "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>\n" +

                    "<title>Html Report</title>" +
                "</head>";

    public static String HTML_BODY = "" +
            "<body>" +
                "<h1 align=\"center\">Test Reports</h1>" +
                "<div class=\"panel panel-info\">" +
                "<div class=\"panel-body\">" +
                "<table id=\"myTable\"  class=\"table table-striped table-bordered\">" +
                    "<thead>" +
                        "<tr class=\"info\">" +
                            "<td><b>CLass Name</b></td>" +
                            "<td><b>Method Name</b></td>" +
                            "<td><b>Description</b></td>" +
                            "<td><b>Last Run</b></td>" +
                        "</tr>" +
                    "</thead>" +
                    "<tbody style='font-size:14px'>" ;

    public static String BEFORE_AFTER_BODY = "" +
            "<body>" +
            "<h1 align=\"center\">Before After Test Reports</h1>" +
            "<div class=\"panel panel-info\">" +
            "<div class=\"panel-body\">" +
            "<table id=\"myTable\"  class=\"table table-striped table-bordered\">" +
            "<thead>" +
            "<tr class=\"info\">" +
            "<td><b>Module Name</b></td>" +
            "<td><b>Failed Tables</b></td>" +
            "</tr>" +
            "</thead>" +
            "<tbody style='font-size:14px'>" ;

    public static String HTML_CLOSING = "" +
                    "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>"+
            "</body>" +
           "</html>" +
            "<script>" +
                "$(document).ready(function(){" +
                    "$('#myTable').DataTable({\n" +
                    "\t \"columns\": [\n" +
                    "    null,\n" +
                    "    null,\n" +
                    "    { \"width\": \"60%\" },\n" +
                    "\tnull\n" +
                    "  ]\n" +
                    "});});" +
            "</script>";
}
