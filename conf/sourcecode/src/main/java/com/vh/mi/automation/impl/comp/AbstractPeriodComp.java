package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.vh.mi.automation.api.comp.ap.IPeriodComp;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;
import java.util.List;

/**
 * Created by i80448 on 9/3/2014.
 */
public abstract class AbstractPeriodComp extends AbstractWebComponent implements IPeriodComp {
    public static final String MI_DATE_FORMAT = "MMM YYYY";

    public AbstractPeriodComp(WebDriver driver) {
        super(driver);
    }

    protected abstract WebElement getHeaderElement();

    protected abstract WebElement getFromElement();

    protected abstract WebElement getToElement();

    @Override
    public boolean isFullyLoaded() {
        return isHeaderDisplayed();
    }

    private boolean isHeaderDisplayed() {
        return getHeaderElement().isDisplayed();
    }

    @Override
    public DateTime getSelectedFromDate() {
        return getCurrentlySelectedOption(getFromElement());
    }

    /**
     * Gets currently selected option from <select ... /> web element.
     *
     * @param selectElm
     * @return
     */
    private DateTime getCurrentlySelectedOption(WebElement selectElm) {
        Select select = new Select(selectElm);
        String selectedStr = select.getFirstSelectedOption().getText().trim();
        return DateTimeFormat.forPattern(MI_DATE_FORMAT).parseDateTime(
                selectedStr);
    }

    @Override
    public Collection<DateTime> getFromDates() {
        return getAvailableSelectOptions(getFromElement());
    }

    /**
     * Gets all available <option>'s from <select ... /> element.
     *
     * @param selectElm
     * @return
     */
    private Collection<DateTime> getAvailableSelectOptions(WebElement selectElm) {
        Select select = new Select(selectElm);
        List<WebElement> optElms = select.getOptions();

        Collection<String> options = Collections2.transform(optElms,
                new Function<WebElement, String>() {

                    @Override
                    public String apply(WebElement arg0) {
                        return arg0.getText().trim();
                    }
                });

        return Collections2.transform(options,
                new Function<String, DateTime>() {

                    @Override
                    public DateTime apply(String arg0) {
                        return DateTimeFormat.forPattern(MI_DATE_FORMAT)
                                .parseDateTime(arg0);
                    }
                });
    }

    private boolean isFromDateExists(DateTime from) {
        return getFromDates().contains(from);
    }

    @Override
    public DateTime getSelectedToDate() {
        return getCurrentlySelectedOption(getToElement());
    }

    @Override
    public Collection<DateTime> getToDates() {
        return getAvailableSelectOptions(getToElement());
    }


    private boolean isToDateExists(DateTime toDate) {
        return getToDates().contains(toDate);
    }

    @Override
    public void doSelect(DateTime from, DateTime to) {
        Preconditions.checkArgument(isFromDateExists(from), from
                + " option not available in drop-down.");
        Preconditions.checkArgument(isToDateExists(to), to
                + " option not available in drop-down.");

        // DateTime fromDate = Periods.fromDateFormatedString(from, "MMM YYYY");
        // DateTime toDate = Periods.fromDateFormatedString(to, "MMM YYYY");

        Preconditions.checkArgument(from.isBefore(to.getMillis()),
                "From date can not be grater than to date.");

        // first check if dates are available in drop-down list
        if (isFromDateExists(from) && isToDateExists(to)) {
            // select from date
            Select fromOptions = new Select(getFromElement());
            fromOptions.selectByVisibleText(toMIDateFormat(from));

            // select to date
            Select toOptions = new Select(getToElement());
            toOptions.selectByVisibleText(toMIDateFormat(to));
        } else {
            /*
             * User will never get a chance to select an option which is not
			 * available in the drop-down list, so throw run time exception to
			 * avoid any test cases which sends invalid and illegal options i.e.
			 * not available in the drop down list.
			 */
            throw new AutomationException("APOption '" + from + "' and/or '" + to
                    + "' does not exist in custom AP dron-down list.");
        }
    }

    private String toMIDateFormat(DateTime from) {
        return from.toString(MI_DATE_FORMAT).toUpperCase();
    }

    public boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
