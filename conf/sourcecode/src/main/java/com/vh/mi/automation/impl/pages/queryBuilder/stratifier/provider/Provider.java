package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.provider;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.provider.IProvider;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 1/23/2017.
 */
public class Provider extends AbstractWebComponent implements IProvider {

    private WebElements webElements;

    public Provider(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    public void selectProviderTab()
    {
        webElements.btnProviderTab.click();
    }

    @Override
    public String getPageLink() {
        return "Provider";
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        //fully loaded element
        @FindBy(id = "d2Form:simpleGrid:column_PROVNAME_header_sortCommandLink")
        private WebElement fullyLoadedElement;

        //Specialty Tab
        @FindBy(id = "d2Form:Provider_lbl")
        private  WebElement btnProviderTab;


    }
}
