package com.vh.mi.mxautomation.impl.pages.common;

import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.groovy.ModuleInfo;
import com.vh.mi.mxautomation.api.features.IAmLandingPage;
import org.openqa.selenium.WebDriver;

/**
 * @author i80448
 * @author nimanandhar
 */
public abstract class AbstractLandingPage extends AbstractMIPage implements IAmLandingPage {

    private Module module;

    public AbstractLandingPage(WebDriver driver, String moduleId) {
        super(driver);
        this.module = ModuleInfo.INSTANCE.getModule(moduleId);
    }

    @Override
    public String getPageId() {
        return module.getPageId();
    }

    @Override
    public String getPageTitle() {
        return module.getPageTitle();
    }
}
