package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.SubServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20841 on 12/07/18.
 */
public class SubServiceDrillPage extends AbstractDrillPage implements IDrillPage {
    private WebElements webElements;
    private SubServiceDrillPageDataGrid dataGrid;

    public SubServiceDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new SubServiceDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());
    }
    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }


    private class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver ,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
