package com.vh.mi.automation.impl.comp;

import org.openqa.selenium.WebDriver;

/**
 * Created by i80448 on 11/19/2014.
 * Deprecated because a new instance need to be created every time before using it
 * Use {@link com.vh.mi.automation.impl.comp.loading.LoadingCustomization} instead
 */
@Deprecated
public class LoadingCustomization extends AbstractLoadingComp {
    private static final String ID = "_LoadingCustomization";

    public LoadingCustomization(WebDriver driver) {
        super(driver, ID);
    }
}
