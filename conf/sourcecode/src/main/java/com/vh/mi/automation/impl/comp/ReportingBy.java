package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author i80448
 */
public class ReportingBy extends AbstractWebComponent implements IReportingBy {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static abstract class XPath {
        public static final String DROP_DOWN_BTN = "	//*[@id=\"d2Form:reportingBy_header\"]/table/tbody/tr/td/div/div[2]";
        public static final String RB_HEADER = "//*[@id=\"d2Form:reportingBy_header\"]/table/tbody/tr/td/div/div[1]";
        public static final String RB_BODY = "//*[@id=\"d2Form:reportingBy_body\"]";
    }

    public static abstract class IDs {
        public static final String BODY = "d2Form:reportingBy_body";
        public static final String MENU = "reportingBy_menu";
        public static final String PAID_DATE = "d2Form:link1";
        public static final String INCURRED_DATE = "d2Form:link2";
        public static final String INCURREDPAID_DATE = "d2Form:link3";
    }

    @FindBy(xpath = XPath.DROP_DOWN_BTN)
    private WebElement rbMenuButtonElm;

    @FindBy(id = IDs.BODY)
    private WebElement bodyElm;

    @FindBy(id = IDs.MENU)
    private WebElement menuElm;

    @FindBy(id = IDs.PAID_DATE)
    private WebElement paidOptionElm;

    @FindBy(id = IDs.INCURRED_DATE)
    private WebElement incurredOptionElm;

    @FindBy(id = IDs.INCURREDPAID_DATE)
    private WebElement incurredpaidOptionElm;


    public ReportingBy(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return isDropDownButtonDisplayed();
    }

    private boolean isDropDownButtonDisplayed() {
        return rbMenuButtonElm.isDisplayed();
    }

    @Override
    public RBOption getSelectedOption() {
        String label = bodyElm.getText();
        return RBOption.get(label);
    }

    private boolean isCurrent(RBOption option) {
        return getSelectedOption() == option;
    }

    @Override
    public ImmutableCollection<RBOption> getAvailableOptions() {
        if (displayMenu()) {
            String options = menuElm.getText();
            String[] split = options.split("\\r?\\n");

            Collection<RBOption> transform = Collections2.transform(ImmutableList.copyOf(split),
                    new Function<String, RBOption>() {

                        @Override
                        public RBOption apply(String arg0) {
                            String op = arg0.trim().toUpperCase();
                            return RBOption.get(op);
                        }
                    });

            return ImmutableList.copyOf(transform);

        }

        return ImmutableList.of();
    }

    @Override
    public boolean isOptionAvailable(RBOption option) {
        if (displayMenu()) {
            switch (option) {
                case PAID:
                    return paidOptionElm.isDisplayed();
                case INCURRED:
                    return incurredOptionElm.isDisplayed();
                case INCURREDPAID:
                    try{
                        return incurredpaidOptionElm.isDisplayed();
                    }catch(NoSuchElementException e){
                        logger.info("Incurred Paid reporting by is not available");
                        return false;
                    }
                default:
                    return false;
            }
        } else {
            logger.warn("Could not display the menu.");
        }
        return false;
    }

    @Override
    public RBOption select(RBOption option) {
        boolean optionAvailable = isOptionAvailable(option);
        boolean currentlySelected = isCurrent(option);

        if (optionAvailable && !currentlySelected) {
            switch (option) {
                case PAID:
                    apply(paidOptionElm);
                    return getSelectedOption();
                case INCURRED:
                    apply(incurredOptionElm);
                    return getSelectedOption();
                case INCURREDPAID:
                    apply(incurredpaidOptionElm);
                    return getSelectedOption();
                default:
                    break;
            }
        } else {
            logger.debug(option + " :: optionAvailable? " + optionAvailable
                    + " :: currentlySelected? " + currentlySelected);
        }
        return null;
    }

    private void apply(WebElement periodElm) {
//        used jsClick for IE
        SeleniumUtils.click(periodElm, getDriver());
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    private boolean displayMenu() {
        if (!menuElm.isDisplayed()) {
            mouseOverMenuButton();
            return menuElm.isDisplayed();
        }

        // already in displayed status.
        return true;
    }

    private void mouseOverMenuButton() {
        if (rbMenuButtonElm != null && rbMenuButtonElm.isDisplayed()) {
            rbMenuButtonElm.click();
        } else {
            System.out
                    .println("Analysis period menu button is not displayed or not initialized or the XPath has changed.");
        }
    }



}
