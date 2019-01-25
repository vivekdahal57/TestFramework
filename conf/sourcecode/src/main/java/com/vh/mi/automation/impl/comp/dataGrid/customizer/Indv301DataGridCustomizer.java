package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

/**
 * Created by i80448 on 11/24/2014.
 */
public class Indv301DataGridCustomizer extends AbstractDataGridCustomizer {
    public Indv301DataGridCustomizer(WebDriver driver, IColumnSpec columnSpec) {
        super(driver, columnSpec);
    }

    public Indv301DataGridCustomizer(WebDriver driver, IColumnSpec columnSpec, WebElement compRootElm) {
        super(driver, columnSpec, compRootElm);
    }

    @Override
    public ImmutableList<Field> getFields() {
        // customization table rows
        List<WebElement> trs = compRootElm.findElements(By.tagName("tr"));

        // get columns from each row
        // current implementation : rows are divided into columns, each column representing a COLUMN OPTION.
        Collection<List<WebElement>> tdss = Lists.transform(trs, new Function<WebElement, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebElement aTrElm) {
                List<WebElement> tds = aTrElm.findElements(By.tagName("td"));
                return tds;
            }
        });

        // combine all cols into single list.
        Iterable<WebElement> concat = Iterables.concat(tdss);

        // Map each COLUMN OPTION web element into Field
        Iterable<Field> columnOptions = Iterables.transform(concat, new Function<WebElement, Field>() {
            @Override
            public Field apply(WebElement colOptionElm) {
                Preconditions.checkArgument(colOptionElm != null);
                Field field = Fields.createField(getDriver(), colOptionElm);
                return field;
            }
        });

        // filter out nulls - FieldFactory.createField(...) could return null.
        Iterable<Field> filteredColOptions = Iterables.filter(columnOptions, new Predicate<Field>() {
            @Override
            public boolean apply(Field input) {
                return input != null;
            }
        });
        System.out.print(".....................Test..............................");
        System.out.print(".....................Test..............................");
        System.out.print(".....................Test..............................");
        return ImmutableList.copyOf(filteredColOptions);
    }
}
