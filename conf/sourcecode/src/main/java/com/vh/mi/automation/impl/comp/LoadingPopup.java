package com.vh.mi.automation.impl.comp;

import org.openqa.selenium.WebDriver;

/**
 * @author i80448
 */
@Deprecated
public class LoadingPopup extends AbstractLoadingComp {

    private static final String COMP_ID = "_Loading";

    public LoadingPopup(WebDriver driver) {
        super(driver, COMP_ID);
    }
}
