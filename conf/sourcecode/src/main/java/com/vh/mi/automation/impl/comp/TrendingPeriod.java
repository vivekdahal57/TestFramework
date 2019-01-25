package com.vh.mi.automation.impl.comp;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;
import com.vh.mi.automation.api.comp.ap.IPeriodComp;
import com.vh.mi.automation.api.comp.ap.ITrendingPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.impl.comp.state.Period;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i80448
 */
public class TrendingPeriod extends AbstractWebComponent implements
        ITrendingPeriod {
    // holds default options available
    private ImmutableList<APOption> defaultOptions;

    // holds current user selected option
    private APOption userSelectedOption;

    private IPeriodComp cPeriodOne;
    private IPeriodComp cPeriodTwo;

    public static abstract class XPath {
        public static final String HEADER = "//*[@id=\"d2Form:trendPeriod_provContainer_header\"]/span";
    }

    public static abstract class IDs {

        public static final String PERIOD1_FROM = "d2Form:trendPeriodTrend_hiddenIPfirstDate1";
        public static final String PERIOD1_TO = "d2Form:trendPeriodTrend_hiddenIPfirstDate2";

        public static final String PERIOD2_FROM = "d2Form:trendPeriodTrend_hiddenIPsecondDate1";
        public static final String PERIOD2_TO = "d2Form:trendPeriodTrend_hiddenIPsecondDate2";

        public static final String OK_BUTTON = "d2Form:trendPeriod_TrendOK";

        //Added ID for new claims module
        public static final String OK_BUTTON1 = "d2Form:trendPeriod_TrendOK_1";

        public static final String D_OPTIONS_BODY = "d2Form:backingBeanTimeTrend:tb";
    }

    @FindBy(xpath = XPath.HEADER)
    private WebElement headerElm;

    @FindAll({@FindBy(id = IDs.OK_BUTTON),@FindBy(id = IDs.OK_BUTTON1)})
    WebElement okButtonElm;

    @FindBy(id = IDs.D_OPTIONS_BODY)
    private WebElement defaultOptionsBodyElm;

    public TrendingPeriod(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return isHeaderDisplayed();
    }

    private boolean isHeaderDisplayed() {
        return headerElm.isDisplayed();
    }

    private IPeriodComp getCustomPeriod1() {
        if (cPeriodOne == null) {
            cPeriodOne = new TrendCustomPeriodComp(getDriver(), IDs.PERIOD1_FROM,
                    IDs.PERIOD1_TO);
        }
        return cPeriodOne;
    }

    private IPeriodComp getCustomPeriod2() {
        if (cPeriodTwo == null) {
            cPeriodTwo = new TrendCustomPeriodComp(getDriver(), IDs.PERIOD2_FROM,
                    IDs.PERIOD2_TO);
        }
        return cPeriodTwo;
    }

    private void applyAndWait(WebElement periodElm) {
        SeleniumUtils.click(periodElm, getDriver());
        if (isAlertPresent()) {
            logger.info("Invalid period selection. Alert ....");
            logger.info("Alert message {}", getDriver().switchTo().alert()
                    .getText());
        } else {
            new LoadingPopup(getDriver()).waitTillDisappears();
        }
    }

    private boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ImmutableList<APOption> getDefaultOptions() {
        if (defaultOptions != null) {
            return defaultOptions;
        }

        List<APOption> options = new ArrayList<>();

        List<WebElement> elements = defaultOptionsBodyElm.findElements(By.tagName("a"));
        for (WebElement e : elements) {
            String optionTxt = e.getText();
            APOption option = APOption.get(optionTxt);
            options.add(option);
        }

        defaultOptions = ImmutableList.copyOf(options);
        return defaultOptions;
    }

    @Override
    public APOption getCurrentUserSelection() {
        return userSelectedOption;
    }

    /**
     * @param option
     * @return WebElement or null
     */
    private WebElement getDefaultOptionsElms(APOption option) {
        List<WebElement> elements = defaultOptionsBodyElm.findElements(By.tagName("a"));
        for (WebElement elm : elements) {
            String optionTxt = elm.getText();
            if (option == APOption.get(optionTxt))
                return elm;
        }
        return null;
    }

    @Override
    public void doSelectDefaultOption(APOption option) {
        if (getDefaultOptions().contains(option)) {
            WebElement elm = getDefaultOptionsElms(option);
            applyAndWait(elm);

            userSelectedOption = option;
        } else {
            throw new RuntimeException(option + " N/A");
        }
    }

    @Override
    public IPeriod getSelectedPeriodOne() {
        DateTime fromDate = getCustomPeriod1().getSelectedFromDate();
        DateTime toDate = getCustomPeriod1().getSelectedToDate();
        return new Period(fromDate, toDate);
    }

    @Override
    public IPeriod getSelectedPeriodTwo() {
        DateTime fromDate = getCustomPeriod2().getSelectedFromDate();
        DateTime toDate = getCustomPeriod2().getSelectedToDate();
        return new Period(fromDate, toDate);
    }

    @Override
    public void doSelectPeriodOne(DateTime from, DateTime to) {
        getCustomPeriod1().doSelect(from, to);
    }

    @Override
    public void doSelectPeriodTwo(DateTime from, DateTime to) {
        getCustomPeriod2().doSelect(from, to);
    }

    @Override
    public void doApplyPeriodsSelection() {
        applySelection();
        userSelectedOption = APOption.CUSTOM_PERIOD;
    }

    private void applySelection() {
        applyAndWait(okButtonElm);
    }
}
