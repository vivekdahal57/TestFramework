package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by i82298 on 2/16/2017.
 */
public class Indv301DrillPage extends Indv301 implements IDrillPage {

    public Indv301DrillPage(WebDriver driver) {
        super(driver);
    }

    @Override public void doClose() {
        getDriver().findElement(By.linkText("Close")).click();
    }

    @Override
    public boolean isDrillPageValid() {
        return true;
    }

    @Override
    public boolean isImageFullyLoaded() {
        return false;
    }

    @Override
    public void closeCurrentWindowAndGoBackToParentWindow() {

    }

    @Override
    public void doWaitTillPopUpDisappears() {

    }

}
