package com.vh.mi.automation.api.comp.dataGrid.drill;

import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 12/16/2014.
 */
public interface IDrillPageFactory {

    public IDrillPage initElements(WebDriver webDriver, String drillMenu);

}
