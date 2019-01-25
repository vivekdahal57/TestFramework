package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.ap.ITrendCustomPeriodComp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80448 on 9/3/2014.
 */
public class TrendCustomPeriodComp extends AbstractPeriodComp implements ITrendCustomPeriodComp {

    public static abstract class IDs {
        public static final String HEADER = "d2Form:trendPeriod_provContainer_header";
    }


    private WebElement headerElm;
    private WebElement fromElm;
    private WebElement toElm;

    public TrendCustomPeriodComp(WebDriver driver, String fromId, String toId) {
        super(driver);

        PageFactory.initElements(getDriver(), this);

        headerElm = getDriver().findElement(By.id(IDs.HEADER));
        fromElm = driver.findElement(By.id(fromId));
        toElm = driver.findElement(By.id(toId));

    }

    @Override
    protected WebElement getHeaderElement() {
        return headerElm;
    }

    @Override
    protected WebElement getFromElement() {
        return fromElm;
    }

    @Override
    protected WebElement getToElement() {
        return toElm;
    }
}
