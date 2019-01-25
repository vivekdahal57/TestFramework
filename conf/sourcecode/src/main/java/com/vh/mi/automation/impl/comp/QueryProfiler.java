package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.IQueryProfiler;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i81306 on 1/10/2018.
 */
public class QueryProfiler extends AbstractWebComponent implements IQueryProfiler {

    private final WebElements webElements;

    public QueryProfiler(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public List<String> getSchemaQueries(){
        List<String> schemaQueries = new ArrayList<>();
        for(WebElement hoverOption  : webElements.schemaQuerries){
            schemaQueries.add(hoverOption.getText());
        }
        return schemaQueries;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.debugTable.isDisplayed();
    }


    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//*[@id='debugDiv']//table[@class='rich-table']//td[2][contains(text(),'VERTICA')]/following::td[1]")
        private List<WebElement> schemaQuerries;

        @FindBy(id = "debugDiv")
        private WebElement debugTable;
    }
}
