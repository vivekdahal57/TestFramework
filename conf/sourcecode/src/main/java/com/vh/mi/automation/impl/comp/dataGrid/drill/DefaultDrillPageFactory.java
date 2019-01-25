package com.vh.mi.automation.impl.comp.dataGrid.drill;

import com.vh.mi.automation.api.comp.dataGrid.drill.IDrillPageFactory;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by nimanandhar on 12/16/2014.
 */
public class DefaultDrillPageFactory implements IDrillPageFactory {
    public static final IDrillPageFactory INSTANCE = new DefaultDrillPageFactory();

    private DefaultDrillPageFactory(){
        //singleton
    }


    @Override

    public IDrillPage initElements(WebDriver webDriver, String drillMenu) {
        throw new AutomationException(
                "Please override AbstractDataGrid.getDrillPageFactory()"
                        + "\nAnd provide a page specific implementation"
        );
    }

}
