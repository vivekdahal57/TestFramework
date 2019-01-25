package com.vh.mi.automation.impl.pages.ei;

import com.vh.mi.automation.api.pages.ei.IEIDashboard;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;

/**
 * Created by i81306 on 5/30/2017.
 */
public class EIDashboard extends AbstractWebComponent implements IEIDashboard {

    public EIDashboard(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    @Override
    public String getPageTitle() {
        return getDriver().getTitle();
    }

}
