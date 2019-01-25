package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.BiometricsORLabs;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.BiometricsORLabs.IBiometricsORLabs;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/10/2017.
 */
public class BiometricsORLabs extends AbstractWebComponent implements IBiometricsORLabs {
    private static final String ROW_NUMBER_PLACE_HOLDER = "${row}";
    WebElements webElements;
    public BiometricsORLabs(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public String getPageLink() {
        return "Biometrics/Labs";
    }

    public void includeNSelectionsInTheFirstPage(Integer selection){
        for (int n = 1; n<=selection; n++){
            String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[4]/input[1]";
            WebElement allCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(ROW_NUMBER_PLACE_HOLDER, Integer.toString(n)));
            SeleniumUtils.click(allCheckbox);
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
