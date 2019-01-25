package com.vh.mi.automation.impl.comp.favqm;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i82298 on 12/9/2016.
 */
public class FavQualityMeasuresDataGrid extends AbstractDataGrid {

    private final WebElements webElements;

    public FavQualityMeasuresDataGrid(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        PageFactory.initElements(driver, this);
    }



    public IDataGridRow doSelectRow(QualityMeasuresType type, int rowIndex) {
        String xpath = getCheckBoxXpath(type, rowIndex);
        WebElement checkBox = getDriver().findElement(By.xpath(xpath));
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
        return getRows().get(rowIndex-1);
    }

    private String getCheckBoxXpath(QualityMeasuresType type, int row) {
        return "//*[@id='d2FormQmSelect:_qmSelectSimpleGrid:tb']/tr[" + row
                + "]/td[1]/input";
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return FavQialityMeasuresColumns.fromId(id);
    }

    @Override
    protected List<WebElement> getRowsElement() {
        return webElements.dataGridBodyElement.findElements(By.tagName("tr"));
    }

    private static class WebElements {

        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "d2FormQmSelect:_qmSelectSimpleGrid:tb")
        WebElement dataGridBodyElement;

    }
}
