package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Created by i80448 on 11/17/2014.
 */
final class Fields {

    /**
     * Creates a {@link Field} component.
     *
     * @param driver
     * @param fieldRootElm
     * @return {@link Field} instance or null.
     */
    static Field createField(WebDriver driver, WebElement fieldRootElm) {

        checkArgument(driver != null);
        checkArgument(fieldRootElm != null);

        // current implementation - there is only one check box.
        List<WebElement> checkBoxElm = fieldRootElm.findElements(By.tagName("input"));
        if (checkBoxElm == null || checkBoxElm.size() == 0) {
            return null;
        }

        // current implementation - two <span>s, first one for NAME label and 2nd one for DESC
        List<WebElement> labelElms = fieldRootElm.findElements(By.tagName("span"));
        if (labelElms == null || labelElms.size() == 0) {
            return null;
        }

        checkNotNull(labelElms);
        checkPositionIndexes(0, 1, labelElms.size());

        WebElement colNameElm = labelElms.get(0);
        WebElement descElm = labelElms.get(1);

        return new Field(driver, checkBoxElm.get(0), colNameElm, descElm);
    }
}
