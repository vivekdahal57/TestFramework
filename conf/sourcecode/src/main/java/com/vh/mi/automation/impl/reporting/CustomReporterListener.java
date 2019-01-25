package com.vh.mi.automation.impl.reporting;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by i10314 on 6/23/2017.
 */
public class CustomReporterListener implements IReporter {

    Collection<ITestNGMethod> passedTests;
    Collection<ITestNGMethod> failedTests;
    Collection<ITestNGMethod> skippedTests;

    public void generateReport(List< XmlSuite > suiteList, List< ISuite > testList, String outputDirectory) {

        HTMLReportWriter htmlWriter = new HTMLReportWriter();
        LinkedList<String[]> tableHTMLData = new LinkedList<String[]>();

        for(ISuite suite : testList){
            Map<String, ISuiteResult> tmpResults = suite.getResults();
            for (String tests : tmpResults.keySet())
            {
                ISuiteResult suiteResult = tmpResults.get(tests);
                ITestContext testContext = suiteResult.getTestContext();

                passedTests = testContext.getPassedTests().getAllMethods();
                failedTests = testContext.getFailedTests().getAllMethods();
                skippedTests = testContext.getSkippedTests().getAllMethods();

                for ( ITestNGMethod testMethod : testContext.getAllTestMethods()){
                    String className = testMethod.getTestClass().getName();
                    //String newClassName = className;
                    String newClassName = className.substring(className.lastIndexOf(".")+1);
                    String lastRun = getTestMethodStatus(testMethod);
                    tableHTMLData.add(new String[]{newClassName, testMethod.getMethodName(), testMethod.getDescription(), lastRun});
                }
            }
            htmlWriter.generateHTMLReport(tableHTMLData, HTMLContents.HTML_BODY, HTMLContents.REPORT_FILE_NAME);
        }
    }

    public String getTestMethodStatus(ITestNGMethod method){
        if (passedTests.contains(method)){
            return "Pass";
        }else if(failedTests.contains(method)){
            return "Fail";
        }else{
            return "Skip";
        }
    }
}
