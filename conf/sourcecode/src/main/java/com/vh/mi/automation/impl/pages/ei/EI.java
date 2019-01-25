package com.vh.mi.automation.impl.pages.ei;

import com.vh.mi.automation.api.pages.ei.IEIDashboardLoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by i81306 on 5/30/2017.
 */
public class EI extends AbstractWebComponent {

    public EI(WebDriver driver) {
        super(driver);
    }

    public IEIDashboardLoginPage open(String eiDashboardUrl) {
        checkArgument(!isNullOrEmpty(eiDashboardUrl));
        logger.info("Opening {}", eiDashboardUrl);
        getDriver().get(eiDashboardUrl);
        return PageFactory.initElements(getDriver(), EIDashboardLoginPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }
}
