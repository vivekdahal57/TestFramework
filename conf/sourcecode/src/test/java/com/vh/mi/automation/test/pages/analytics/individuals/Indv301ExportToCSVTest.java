package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class Indv301ExportToCSVTest extends BaseTest{
    private IIndv301 indv301;
    private ICSVExtractPopUp csvExtractPopUp;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
    }
    public String randomValue() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Test
    public void test_export_To_CSV_Comma() {
        getWebDriver().get(getWebDriver().getCurrentUrl());// TODO: 2/13/2017 manage this properly to close the popup before creating new extract
        indv301.popupExists();
        csvExtractPopUp = indv301.getCSVExtractPopUp();
        csvExtractPopUp.doWaitTillFullyLoaded(defaultWaitTime);
        csvExtractPopUp.inputCSVName("job"+randomValue());
        csvExtractPopUp.selectDelimeterValue(ICSVExtractPopUp.DelimiterOption.COMMA);
        csvExtractPopUp.clickSendButton();
        indv301.doWaitTillFullyLoaded(defaultWaitTime);
        // TODO: 2/10/2017 verifying the rowcount, csv progress and completion of the csv generation
        getWebDriver().findElement(By.id("d2Form:_csvJobProgressPanelCDiv")).isDisplayed();
    }

    @Test
    public void test_export_To_CSV_Pipe() {
        getWebDriver().get(getWebDriver().getCurrentUrl());// TODO: 2/13/2017 manage this properly to close the popup before creating new extract
        indv301.popupExists();
        csvExtractPopUp = indv301.getCSVExtractPopUp();
        csvExtractPopUp.doWaitTillFullyLoaded(defaultWaitTime);
        csvExtractPopUp.inputCSVName("job"+randomValue());
        csvExtractPopUp.selectDelimeterValue(ICSVExtractPopUp.DelimiterOption.PIPE);
        csvExtractPopUp.clickSendButton();
        indv301.doWaitTillFullyLoaded(defaultWaitTime);
        // TODO: 2/10/2017 verifying the rowcount, csv progress and completion of the csv generation
        getWebDriver().findElement(By.id("d2Form:_csvJobProgressPanelCDiv")).isDisplayed();
    }

}
