package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.HEDISClaimsBased;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.HEDISClaimsBased.IHEDISClaimsBased;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 7/7/2017.
 */
public class HEDISClaimsBased extends AbstractWebComponent implements IHEDISClaimsBased {
    private static final String ROW_NUMBER_PLACE_HOLDER = "${row}";
    WebElements webElements;

    public HEDISClaimsBased(WebDriver driver) {
        super(driver);
        webElements=new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public String getPageLink() {
        return "HEDIS-Claims Based";
    }

    public void includeNSelections(Integer selection){

        for (int n=1; n<=selection; n++){
            String xpath ="//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[6]/input[1]";
            WebElement includeCheckboxes = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(ROW_NUMBER_PLACE_HOLDER, Integer.toString(n)));
            SeleniumUtils.click(includeCheckboxes);
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
