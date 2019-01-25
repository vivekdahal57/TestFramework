package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;

/**
 * Created by i81247 on 11/15/2016.
 */
public class EIDashboardDummyPage extends AbstractWebComponent {
    public EIDashboardDummyPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }
}
