package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.pages.analytics.demography.drill.ID20MMDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 3/2/18.
 */
public class D20MMDrillPage  extends AbstractDrillPage implements ID20MMDrillPage{
    private WebElements webElements;

    public D20MMDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    public static class WebElements{
        private  WebElements(WebDriver driver){
            PageFactory.initElements(driver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
