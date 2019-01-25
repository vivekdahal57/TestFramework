package com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.Drill;


import com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager.Drill.IProfilerDashboardDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilerDashBoardDrillPage extends AbstractDrillPage implements IProfilerDashboardDrillPage{

    private WebElements webElements;

    public ProfilerDashBoardDrillPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
       return  "(CP150) Profiler Dashboard".equals(webElements.pageTitle.getText());
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

    }
}
