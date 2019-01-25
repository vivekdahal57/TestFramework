package com.vh.mi.automation.impl.pages.admin;

import com.vh.mi.automation.api.pages.admin.IAdminLoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by i81306 on 5/11/2017.
 */
public class MIAdmin extends AbstractWebComponent {

    public MIAdmin(WebDriver driver) {
        super(driver);
    }

    public IAdminLoginPage open(String adminURL) {
        checkArgument(!isNullOrEmpty(adminURL));
        logger.info("Opening {}", adminURL);
        getDriver().get(adminURL);
        return PageFactory.initElements(getDriver(), AdminLoginPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    public void quit() {
        getDriver().quit();
    }
}
