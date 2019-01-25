package com.vh.mi.automation.impl.comp.dataGrid.customizer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.impl.comp.LoadingCustomization;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.filter;

/**
 * Created by i80448 on 11/7/2014.
 */
public abstract class AbstractDataGridCustomizer extends AbstractWebComponent implements IDataGridCustomizer {

    public static abstract class IDs {
        public static final String CUSTOMIZE_TABLE = "d2FormCustomizationPanel:customizeGrid";
        public static final String CANCEL = "d2FormCustomizationPanel:formCustomize_panel_cancel";
        public static final String RESET = "d2FormCustomizationPanel:formCustomize_panel_reset";
        public static final String APPLY = "d2FormCustomizationPanel:formCustomize_panel_apply";
        public static final String SAVE = "d2FormCustomizationPanel:formCustomize_panel_save";
    }

    @FindAll({@FindBy(id = IDs.CUSTOMIZE_TABLE), @FindBy(id = "d2FormMDTCustomizationPanel:customizeGrid")})
    protected WebElement compRootElm;

    @FindBy(id = IDs.CANCEL)
    private WebElement cancelBtn;

    @FindBy(id = IDs.RESET)
    private WebElement resetBtn;

    @FindAll({@FindBy(id = IDs.APPLY), @FindBy(id = "d2FormMDTCustomizationPanel:formCustomize_panel_apply")})
    private WebElement applyBtn;

    @FindBy(id = IDs.SAVE)
    private WebElement saveBtn;

    private IColumnSpec columnSpec;

    public AbstractDataGridCustomizer(WebDriver driver, IColumnSpec columnSpec) {
        super(driver);

        checkArgument(columnSpec != null);

        this.columnSpec = columnSpec;
        PageFactory.initElements(driver, this);
    }

    public AbstractDataGridCustomizer(WebDriver driver, IColumnSpec columnSpec, WebElement compRootElm) {
        this(driver, columnSpec);

        checkArgument(compRootElm != null);

        this.compRootElm = compRootElm;
    }

    @Override
    public boolean isSelected(IDataGridColumn col) {
        checkArgument(col != null);

        Field field = getFieldFor(col);
        if (field != null) {
            return field.isChecked();
        }
        return false;
    }

    @Override
    public void doSelect(IDataGridColumn col) {
        checkArgument(col != null);

        Field field = getFieldFor(col);
        if (field != null && !field.isChecked()) {
            field.doCheck();
            return;
        }

        logger.warn("Already selected OR field is not available = " + (field == null));
    }

    @Override
    public void doSelectAll() {
        Collection<Field> fields = getFields();
        for (Field field : fields) {
            if (field != null && !field.isChecked()) {
                field.doCheck();
            }
        }
    }

    @Override
    public void doUnselectAll() {
        Collection<Field> fields = getFields();
        for (Field field : fields) {
            if (field != null && field.isChecked()) {
                field.doUncheck();
            }
        }
    }

    @Override
    public void doUnselect(IDataGridColumn col) {
        checkArgument(col != null);

        Field field = getFieldFor(col);
        if (field != null && field.isChecked()) {
            field.doUncheck();
            return;
        }

        logger.warn("Already in unchecked state OR field is not available = " + (field == null));
    }

    @Override
    public void doApplySelection() {

        SeleniumUtils.click(applyBtn,getDriver());
    }

    @Override
    public void doSaveAndApplySelection() {
        saveBtn.click();
    }

    @Override
    public void doResetSelections() {
        SeleniumUtils.click(resetBtn,getDriver());
        new LoadingCustomization(getDriver()).waitTillDisappears();
    }

    @Override
    public void doCancelAndClose() {
        SeleniumUtils.click(cancelBtn,getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    @Override
    public ImmutableCollection<IDataGridColumn> getAvailaleOptions() {
        ImmutableCollection<Field> fields = getFields();
        checkNotNull(fields);

        return transformIntoIColumns(fields);
    }

    @Override
    public ImmutableList<IDataGridColumn> getSelectedOptions() {
        ImmutableList<Field> fields = getFields();
        checkNotNull(fields);


        Collection<Field> selections = filter(fields, new Predicate<Field>() {
            @Override
            public boolean apply(Field input) {
                return input.isChecked();
            }
        });

        return transformIntoIColumns(ImmutableList.copyOf(selections));

    }

    private Field getFieldFor(IDataGridColumn col) {
        Collection<Field> fields = getFields();
        checkNotNull(fields);

        for (Field f : fields) {
            if (f.getFieldLabel().equalsIgnoreCase(col.getLabel())) {
                return f;
            }
        }

        return null;
    }

    private ImmutableList<IDataGridColumn> transformIntoIColumns(ImmutableCollection<Field> selections) {
        List<IDataGridColumn> transform = Lists.transform((List<Field>) selections, new Function<Field, IDataGridColumn>() {
            @Override
            public IDataGridColumn apply(Field input) {
                String name = input.getFieldLabel();
                IDataGridColumn columnByName = columnSpec.getColumnByLabel(name);
                return columnByName;
            }
        });

        Collection<IDataGridColumn> filter = Collections2.filter(transform, new Predicate<IDataGridColumn>() {
            @Override
            public boolean apply(IDataGridColumn input) {
                return input != null;
            }
        });

        return ImmutableList.copyOf(filter);
    }

    public abstract ImmutableList<Field> getFields();
}
