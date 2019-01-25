package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyzerCohortAnalyzer;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PopulationAnalyzerCohortAnalyzer.ICohortAnalyzer;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyser;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i10314 on 7/20/2017.
 */
public class CohortAnalyzer extends QueryBuilderToolBar implements ICohortAnalyzer {
    private static final String ROW_PLACE_HOLDER = "${row}";
    PopulationAnalyser populationAnalyser;
    WebElements webElements;

    public CohortAnalyzer(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }
    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    public boolean checkWhetherTheseCohortRelatedDetailsAreShown(List<String> cohortNames){
        for (int i = 0; i < cohortNames.size(); i++){
            String xpath = "//*[@id=\"d2Form:cohortsSelectedTable:${row}:innertable\"]/tbody/tr[1]";
            String cohortName = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_PLACE_HOLDER,Integer.toString(i))).getText();
            if (!(cohortNames.contains(cohortName))){
                return false;
            }
        }
        return true;
    }

    public PopulationAnalyser goBackToPopulationAnalyser(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnBack);
        SeleniumUtils.click(webElements.btnBack);
        populationAnalyser = new PopulationAnalyser(getDriver());
        return populationAnalyser;
    }


    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td[1]/table/tbody/tr[2]/td/a")
        private WebElement btnBack;
    }
}
