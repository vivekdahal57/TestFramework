package com.vh.mi.automation.impl.comp;

import org.openqa.selenium.WebDriver;

/**
 * Created by i80448 on 9/10/2014.
 * <p/>
 * Deprecated in favor of {@link com.vh.mi.automation.impl.comp.loading.LoadingPanelComponent}
 */
@Deprecated
public class LoadingPanelComp extends AbstractLoadingComp {

    private static final String COMP_ID = "_LoadingPanel";


    public LoadingPanelComp(WebDriver driver) {
        super(driver, COMP_ID);
    }
}
