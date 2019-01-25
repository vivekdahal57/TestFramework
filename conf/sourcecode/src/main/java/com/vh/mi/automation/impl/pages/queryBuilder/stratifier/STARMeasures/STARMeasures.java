package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.STARMeasures;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.STARMeasures.ISTARMeasures;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/10/2017.
 */
public class STARMeasures extends AbstractWebComponent implements ISTARMeasures {
    private static final String ROW_NUMBER_PLACE_HOLDER = "${row}";
    WebElements webElements;

    public STARMeasures(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public String getPageLink() {
        return "STAR Measures";
    }

    public void includeNSelections(Integer selection){
        for (int n = 1; n<=selection; n++){
            String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[4]/input[1]";
            WebElement includeAllCheckBoxes = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(ROW_NUMBER_PLACE_HOLDER, Integer.toString(n)));
            SeleniumUtils.click(includeAllCheckBoxes);
        }

    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }
        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

    }
}
