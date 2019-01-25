package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.google.common.base.Preconditions.checkState;

/**
 * Represents a column option (check box, name, desc) component in a customization row.
 */
class Field {
    private WebDriver driver;
    private WebElement fieldRootElm;

    private WebElement checkBoxElm;
    private WebElement colLabelElm;
    private WebElement colDescElm;

    Field(WebDriver driver,WebElement checkBoxElm, WebElement colLabelElm, WebElement colDescElm) {
        this.driver = driver;
        this.checkBoxElm = checkBoxElm;
        this.colLabelElm = colLabelElm;
        this.colDescElm = colDescElm;
    }

   boolean isChecked() {
        return checkBoxElm.isSelected();
    }

    boolean isEnabled() {
        return checkBoxElm.isEnabled();
    }

    void doCheck() {
        checkState(checkBoxElm != null);
        if (!isChecked() && isEnabled()) {

         SeleniumUtils.click(checkBoxElm, driver);
        }
    }

    void doUncheck() {
        checkState(checkBoxElm != null);
        if (isChecked() && isEnabled()) {
            SeleniumUtils.click(checkBoxElm, driver);
        }
    }

    String getFieldLabel() {
        checkState(colLabelElm != null);
        return colLabelElm.getText().trim();
    }

    String getDescription() {
        checkState(colDescElm != null);
        return colDescElm.getText().trim();
    }
}
