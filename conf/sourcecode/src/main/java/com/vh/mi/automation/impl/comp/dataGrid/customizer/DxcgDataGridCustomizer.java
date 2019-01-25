package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.ICustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDxcgDataGridCustomizer;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Maps.transformValues;

/**
 * Created by i80448 on 11/14/2014.
 */

public class DxcgDataGridCustomizer extends AbstractWebComponent implements IDxcgDataGridCustomizer {

    public static final String COMP_ROOT_ID = "d2FormCustomizationPanel:floatDivGroups";

    @FindBy(id = COMP_ROOT_ID)
    private WebElement compRootElm;

    @FindBy(id = AbstractDataGridCustomizer.IDs.CANCEL)
    private WebElement cancelBtn;

    @FindBy(id = AbstractDataGridCustomizer.IDs.RESET)
    private WebElement resetBtn;

    @FindBy(id = AbstractDataGridCustomizer.IDs.APPLY)
    private WebElement applyBtn;

    @FindBy(id = AbstractDataGridCustomizer.IDs.SAVE)
    private WebElement saveBtn;

    private IColumnSpec columnSpec;

    public DxcgDataGridCustomizer(WebDriver driver, IColumnSpec columnSpec) {
        super(driver);
        this.columnSpec = columnSpec;
        PageFactory.initElements(driver, this);
    }

    @Override
    public ImmutableCollection<IDataGridColumn> getAvailaleOptions() {
        ImmutableMap<String, ICustomizer> secCustomizers = getSectionCustomizers();
        ImmutableCollection<ICustomizer> values = secCustomizers.values();

        Collection<ImmutableCollection<IDataGridColumn>> transform = Collections2.transform(values, new Function<ICustomizer, ImmutableCollection<IDataGridColumn>>() {
            @Override
            public ImmutableCollection<IDataGridColumn> apply(ICustomizer input) {
                return input.getAvailaleOptions();
            }
        });

        Iterable<IDataGridColumn> concat = Iterables.concat(transform);

        return ImmutableList.copyOf(concat);
    }

    @Override
    public ImmutableCollection<IDataGridColumn> getSelectedOptions() {
        ImmutableMap<String, ICustomizer> secCustomizers = getSectionCustomizers();
        ImmutableCollection<ICustomizer> values = secCustomizers.values();

        Collection<ImmutableCollection<IDataGridColumn>> transform = Collections2.transform(values, new Function<ICustomizer, ImmutableCollection<IDataGridColumn>>() {
            @Override
            public ImmutableCollection<IDataGridColumn> apply(ICustomizer input) {
                return input.getSelectedOptions();
            }
        });

        Iterable<IDataGridColumn> concat = Iterables.concat(transform);

        return ImmutableList.copyOf(concat);
    }

    @Override
    public ImmutableCollection<String> getSections() {
        // current implementation : each section is a <div>, there are nested <div>s this won't work
        List<WebElement> secElms = compRootElm.findElements(By.tagName("div"));

        checkState(secElms != null);

        Collection<String> transform = Collections2.transform(secElms, new Function<WebElement, String>() {
            @Override
            public String apply(WebElement input) {
                WebElement element = input.findElement(By.xpath("./span"));
                String sectionLabel = element.getText().trim();
                return sectionLabel;
            }
        });

        return ImmutableList.copyOf(transform);
    }

    @Override
    public ImmutableMap<String, ImmutableCollection<IDataGridColumn>> getDxcgAvailableOptions() {
        // All sections and their customizers
        ImmutableMap<String, ICustomizer> secCustomizers = getSectionCustomizers();

        checkState(secCustomizers != null);

        // transform into sections and their options
        Map<String, ImmutableCollection<IDataGridColumn>> options = transformValues(secCustomizers,
                new Function<ICustomizer, ImmutableCollection<IDataGridColumn>>() {
                    @Override
                    public ImmutableCollection<IDataGridColumn> apply(ICustomizer customizer) {
                        return customizer.getAvailaleOptions();
                    }
                });

        return ImmutableMap.copyOf(options);
    }

    @Override
    public ImmutableMap<String, ImmutableCollection<IDataGridColumn>> getDxcgSelectedOptions() {
        // All sections and their customizers
        ImmutableMap<String, ICustomizer> secCustomizers = getSectionCustomizers();

        checkState(secCustomizers != null);

        // transform into sections and their selected options.
        Map<String, ImmutableCollection<IDataGridColumn>> selectedOptions = transformValues(secCustomizers,
                new Function<ICustomizer, ImmutableCollection<IDataGridColumn>>() {
                    @Override
                    public ImmutableCollection<IDataGridColumn> apply(ICustomizer customizer) {
                        return customizer.getSelectedOptions();
                    }
                });

        return ImmutableMap.copyOf(selectedOptions);
    }

    @Override
    public void doSelect(String section, IDataGridColumn column) {
        checkArgument(!isNullOrEmpty(section));
        checkArgument(column != null);

        ICustomizer sectionCustomizer = getSectionCustomizer(section);

        checkState(sectionCustomizer != null, "Section = " + section + ", customizer = N/A");

        sectionCustomizer.doSelect(column);
    }

    @Override
    public void doSelectAll(String section) {
        checkArgument(!isNullOrEmpty(section));

        ICustomizer sectionCustomizer = getSectionCustomizer(section);

        checkState(sectionCustomizer != null, "Section = " + section + ", customizer = N/A");

        sectionCustomizer.doSelectAll();
    }

    @Override
    public void doUnselectAll(String section) {
        checkArgument(!isNullOrEmpty(section));

        ICustomizer sectionCustomizer = getSectionCustomizer(section);

        checkState(sectionCustomizer != null, "Section = " + section + ", customizer = N/A");

        sectionCustomizer.doUnselectAll();
    }

    @Override
    public void doSelectAll() {
        Map<String, ICustomizer> secCustomizers = getSectionCustomizers();

        checkState(secCustomizers != null);

        for (ICustomizer c : secCustomizers.values()) {
            c.doSelectAll();
        }
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void doApplySelection() {
        applyBtn.click();
    }

    @Override
    public void doSaveAndApplySelection() {
        saveBtn.click();
    }

    @Override
    public void doResetSelections() {
        resetBtn.click();
    }

    @Override
    public void doCancelAndClose() {
        cancelBtn.click();

    }


    private ImmutableMap<String, ICustomizer> getSectionCustomizers() {

        // current implementation : each section is a <div>, there are nested <div>s this won't work
        List<WebElement> secElms = compRootElm.findElements(By.tagName("div"));

        checkState(secElms != null);

        Map<String, ICustomizer> customizers = new HashMap<>();
        for (WebElement elm : secElms) {
            WebElement element = elm.findElement(By.xpath("./span"));
            String sectionLabel = element.getText().trim();
            customizers.put(sectionLabel, new Indv301DataGridCustomizer(getDriver(), columnSpec, elm));
        }

        return ImmutableMap.copyOf(customizers);
    }

    private ICustomizer getSectionCustomizer(String section) {
        checkArgument(!isNullOrEmpty(section));
        return getSectionCustomizers().get(section);
    }

    public static boolean isCustomizationAvailable(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.id(COMP_ROOT_ID));
        if (elements != null && elements.size() == 1) {
            return true;
        } else {
            return false;
        }
    }
}