package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by i80448 on 11/24/2014.
 */
public class DataGridCustomizer extends AbstractDataGridCustomizer {
    public DataGridCustomizer(WebDriver driver, IColumnSpec columnSpec) {
        super(driver, columnSpec);
    }

    @Override
    public ImmutableList<Field> getFields() {

        // current implementation - all outer level rows uses class "rich-table-row", any other nested rows does not use this class,
        // in that case this implementation does not work.
        List<WebElement> trs = compRootElm.findElements(By.className("rich-table-row"));

        // get columns from each row
        // current implementation : rows are divided into columns, each column representing a COLUMN OPTION.
        Collection<List<WebElement>> tdss = Lists.transform(trs, new Function<WebElement, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebElement aTrElm) {
                List<WebElement> tds = aTrElm.findElements(By.xpath("./td[contains(@id, \"d2FormCustomizationPanel:customizeGrid\")]"));

                return tds;
            }
        });

        // Flatten all cols into single list.
        Iterable<WebElement> concat = Iterables.concat(tdss);

        List<Field> fields = new ArrayList<>();
        for (WebElement elm : concat) {
            String colId = elm.getAttribute("id");

            // current implementation : XPATH
            // checkbox - //*[@id="d2FormCustomizationPanel:customizeGrid:0"]/table/tbody/tr/td[1]/input
            // field label span - //*[@id="d2FormCustomizationPanel:customizeGrid:0:label"]
            // field label desc. span - //*[@id="d2FormCustomizationPanel:customizeGrid:0"]/table/tbody/tr/td[2]/span[2]

            String inputXpath = "//*[@id='" + colId + "']/table/tbody/tr/td[1]/input|//*[@id='" + colId + "']/input";
            String labelSpanXpath = "//*[@id=\"" + colId + ":label\"]|//*[@id=\"" + colId +":label_1\"]";
            String descSpanXpath = "//*[@id=\"" + colId + "\"]/table/tbody/tr/td[2]/span[2]|//*[@id=\"" + colId +"\"]/span[2]";

            WebElement checkboxElm = elm.findElement(By.xpath(inputXpath));
            WebElement labelElm = elm.findElement(By.xpath(labelSpanXpath));
            WebElement descElm = elm.findElement(By.xpath(descSpanXpath));

            Field field = new Field(getDriver(),checkboxElm, labelElm, descElm);
            fields.add(field);
        }

        return ImmutableList.copyOf(fields);
    }
}
