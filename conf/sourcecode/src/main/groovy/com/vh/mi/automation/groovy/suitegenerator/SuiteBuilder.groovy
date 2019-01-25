package com.vh.mi.automation.groovy.suitegenerator

import com.google.common.base.Preconditions
import com.vh.mi.automation.api.exceptions.NotImplementedException
import groovy.xml.MarkupBuilder
import groovy.xml.MarkupBuilderHelper
import org.apache.logging.log4j.core.util.FileUtils

/**
 * @author nimanandhar
 */

class TestClass {
    String qualifiedName
    String type
    List<String> excludeIn
    List<String> includeOnlyIn
    List<String> testGroups


    String getPackage() {
        throw new NotImplementedException()
    }

    public String getName() {
        String testName = null
        qualifiedName.eachMatch(/.*\.(.*)/) { fullMatch, name ->
            testName = name
        }
        return testName
    }

}

class SuiteBuilder extends BuilderSupport {
    String part = "1"
    Integer suites = 1
    String parallel = "instances"
    Integer threads = 1

    TestClass currentTest = null
    String currentType = null
    List<TestClass> allTests = new LinkedList<>()

    /**
     * To handle cases like excludeIn that takes arbitrary number of arguments
     */
    @Override
    Object invokeMethod(String methodName, Object args) {
        if (methodName == "excludeIn") {
            def excludeList = []
            args.each { arg ->
                excludeList.add(arg)
            }
            currentTest.excludeIn = excludeList
            return null
        }else if (methodName == "includeOnlyIn") {
            def includeOnlyList = []
            args.each { arg ->
                includeOnlyList.add(arg)
            }
            currentTest.includeOnlyIn = includeOnlyList
            return null
        } else if (methodName == "testGroups") {
            def groups = []
            args.each { arg ->
                groups.add(arg)
            }
            currentTest.testGroups = groups
            return null
        } else {
            return super.invokeMethod(methodName, args)
        }
    }


    @Override
    protected Object createNode(Object name) {
        switch (name) {
            case "test":
                currentTest = new TestClass(type: currentType);
                return name
            case ["smokeTest", "uiTest", "unitTest"]:
                currentType = name
                return null
            case ['tests', 'testConfiguration', 'configuration']:
                return null
            case ['defaultTests', 'specialTests', 'appSpecificTests']:
                return null
            default:
                throw new Error("Unknown tag $name")
        }
        return null
    }

    @Override
    protected Object createNode(Object name, Object value) {
        switch (name) {
            case 'testClass':
                currentTest.qualifiedName = value
                return currentTest
            default:
                throw new Error("Unexpected tag $name with value $value")
        }
    }


    @Override
    protected void nodeCompleted(Object parent, Object node) {
        switch (node) {
            case "test":
                allTests.add(currentTest)
                currentTest = null;
                break
            case ["smokeTest", "uiTest", "unitTest"]:
                currentType = null;
                break
        }
    }

    public boolean createSuiteXML() {
        def parts = part.split("to")
        def partsRange
        if (parts.size() == 1) {
            partsRange = (Integer.valueOf(parts[0])..Integer.valueOf(parts[0]))
        } else if (parts.size() == 2) {
            partsRange = (Integer.valueOf(parts[0])..Integer.valueOf(parts[1]))
        } else {
            throw new RuntimeException("Unexpected ${part}")
        }

        partsRange.each {
            createSuiteXMLForPart(it)
        }
    }

    private void createSuiteXMLForPart(int part) {
        Preconditions.checkState(part <= suites)

        List<TestClass> filteredTests = getTestsToInclude()
        List<TestClass> testsInThisPart = []
        filteredTests.eachWithIndex { test, index ->
            if (index % suites == (part - 1))
                testsInThisPart.add(test)
        }

        String separator = File.separatorChar;
        def suiteName = "part${part}of${suites}"
        def targetFolderPath = "${System.getProperty("user.dir")}${separator}target${separator}suites${separator}"
        FileUtils.mkdir(new File(targetFolderPath), true)

        String suiteNamePrefix = System.getProperty("suiteNamePrefix")
        if (!suiteNamePrefix) {
            suiteNamePrefix = "suite"
        }
        File suiteFile = new File("${targetFolderPath}${suiteNamePrefix}_${suiteName}.xml")
        buildXml(suiteName, suiteFile, parallel, threads, testsInThisPart)

        println suiteFile
        println suiteFile.getText()

    }

    List<TestClass> getTestsToInclude() {
        def appId = System.getProperty("appId")

        List<TestClass> filteredTests = []
        boolean skipTest = true
        for (TestClass test : allTests) {
            if (null != test.excludeIn && (appId && test.excludeIn.contains(appId))) {
                continue
            }

            if (null != test.includeOnlyIn) {
                if(appId && test.includeOnlyIn.contains(appId)){
                    filteredTests.add(test)
                }
                continue
            }

            if (testNameContainsFilter().size() > 0) {
                skipTest = true
                for (String filter : testNameContainsFilter()) {
                    if (test.name.toLowerCase().contains(filter.toLowerCase())) {
                        skipTest = false
                        break
                    }
                }
                if (skipTest) continue
            }

            if (testNameStartsWithFilter().size() > 0) {
                skipTest = true
                for (String filter : testNameStartsWithFilter()) {
                    if (test.getName().toLowerCase().startsWith(filter.toLowerCase())) {
                        skipTest = false
                        break
                    }
                }
                if (skipTest) continue
            }


            if (testNameEndsWithFilter().size() > 0) {
                skipTest = true
                for (String filter : testNameEndsWithFilter()) {
                    if (test.getName().toLowerCase().endsWith(filter.toLowerCase())) {
                        skipTest = false
                        break
                    }
                }
                if (skipTest) continue
            }

            if (testGroupsFilter().size() > 0) {
                skipTest = true
                for (String filter : testGroupsFilter()) {
                    if (test.testGroups != null && test.testGroups.contains(filter)) {
                        skipTest = false
                        break
                    }
                }
                if (skipTest) continue
            }


            if (testNameExcludesFilter().size() > 0) {
                skipTest = false
                for (String filter : testNameExcludesFilter()) {
                    if (test.getName().equals(filter)) {
                        skipTest = true
                        break
                    }
                }
                if (skipTest) continue
            }

            filteredTests.add(test)
        }
        return filteredTests
    }

    private List<String> testNameContainsFilter() {
        String property = System.getProperty("testName.contains")
        if (!property)
            return Collections.emptyList()

        if (property.contains("&")) throw new Error("anding is not currently supported for testName.contains")

        return property.split("\\|").toList()
    }


    private List<String> testNameStartsWithFilter() {
        String property = System.getProperty("testName.startsWith")
        if (!property)
            return Collections.emptyList()
        if (property.contains("&"))
            throw new Error("anding is not supported for testName.startsWith")

        return property.split("\\|").toList()
    }

    private List<String> testNameExcludesFilter() {
        String property = System.getProperty("testName.excludes")
        if (!property)
            return Collections.emptyList()
        if (property.contains("|"))
            throw new Error("oring is not supported for testName.excludes. Use &")

        return property.split("&").toList()
    }

    private ArrayList<String> testNameEndsWithFilter() {
        String property = System.getProperty("testName.endsWith")
        if (!property)
            return Collections.emptyList()

        if (property.contains("&")) throw new Error("anding is not supported for testName.endsWith")
        return property.split("\\|").toList()
    }

    private List<String> testGroupsFilter() {
        String property = System.getProperty("testGroups")
        if (!property)
            return Collections.emptyList()

        return  property.split("\\|").toList()
    }


    private static buildXml(String suiteName, suiteFile, String parallel, Integer threads, List tests) {
        def suiteParam = [name: suiteName]
        if (parallel != "false") {
            suiteParam["parallel"] = parallel
            suiteParam["thread-count"] = threads
        }

        def xml = new MarkupBuilder(new FileWriter(suiteFile))
        def helper = new MarkupBuilderHelper(xml)

        helper.xmlDeclaration([version: '1.0', encoding: 'UTF-8'])
        helper.yieldUnescaped """<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" > \n"""
        xml.suite(name: suiteName) {
            listeners(){
                    listener("class-name": "com.vh.mi.automation.impl.reporting.CustomReporterListener")
            }
            test(suiteParam) {
                classes {
                    tests.each { TestClass test ->
                        "class"(name: test.qualifiedName) {
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void setParent(Object parent, Object child) {

    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        throw new NotImplementedException("Not Needed")
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        throw new NotImplementedException("Not Needed")
    }

}


