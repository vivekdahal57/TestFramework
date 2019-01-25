package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.comp.state.ITrendPeriod;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.utils.Periods;
import com.vh.mi.automation.impl.comp.state.TrendPeriod;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.impl.utils.screenshot.ScreenShotTaker;
import org.fest.assertions.Assertions;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption.*;

/**
 * @author nimanandhar
 * @author i80448
 */
public class AnalysisPeriod extends AbstractWebComponent implements IAnalysisPeriod {
    private final WebElements webElements;
    private IPeriod fullCyclePeriod = null;// cache full cycle period
    private ImmutableList<APOption> availableOptions = null; //cached options
    public static final Pattern AP_OPTION_PATTERN = Pattern.compile("<a.*?>(.*?)</a>", Pattern.CASE_INSENSITIVE);

    public AnalysisPeriod(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.apBodyElm.isDisplayed();
    }

    private void displayMenu() {
        if (!webElements.apMenu.isDisplayed()) {
            SeleniumUtils.hoverOnElement(webElements.apMenuButtonElm, getDriver());
        }
        Assertions.assertThat(webElements.apMenu.isDisplayed()).as("APMenu should be displayed").isTrue();
    }

    @Override
    public Object doSelect(APOption option) {
        checkArgument(option != null, "Argument option can not be null.");

        if (!isOptionExists(option))
            throw new RuntimeException(option + " is not available in available list " + getAvailableOptions());

        if (isCurrent(option) && option != CUSTOM_PERIOD && option != TREND_PERIODS)
            return option; //if fullCycle/Rolling/Contract is already selected, can't reselect the same option

        displayMenu();//need to display menu so that getText works , try other alternative
        switch (option) {
            case FULL_CYCLE:
                applyAndWait(webElements.fullCycleElm);
                return getSelectedOption();
            case CONTRACT_YEAR:
                applyAndWait(webElements.contractElm);
                return getSelectedOption();
            case ROLLING_YEAR:
                applyAndWait(webElements.rollingElm);
                return getSelectedOption();
            case CUSTOM_PERIOD:
                SeleniumUtils.click(webElements.customElm, getDriver());
                CustomPeriodComp customPeriodComp = new CustomPeriodComp(getDriver());
                customPeriodComp.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
                return customPeriodComp;
            case TREND_PERIODS:
                SeleniumUtils.click(webElements.trendElm, getDriver());
                TrendingPeriod trendingPeriod = PageFactory.initElements(getDriver(), TrendingPeriod.class);
                trendingPeriod.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
                return trendingPeriod;
            default:
                throw new RuntimeException("Unknown option " + option);
        }
    }

    private void applyAndWait(WebElement periodElm) {
//        IE specific fix for webElement.click()
        SeleniumUtils.click(periodElm, getDriver());
        new LoadingPopup(getDriver()).waitTillDisappears(); //todo avoid creating new instance of Loading Popup everytime
    }

    @Override
    public APOption getSelectedOption() {
        String currentOption = null;
        try {
            currentOption = webElements.apBodyElm.getText();
        } catch (Exception ex) {
            logger.error("Error occurred while getting text for apBodyElm .Exception={}", ex);
            new ScreenShotTaker().takeScreenShot(getDriver(), "webElement_apBodyElm_exception.png");
            waitUntilElementIsDisplayed(webElements.apBodyElm);
            currentOption = webElements.apBodyElm.getText();
        }

        String[] split = currentOption.split("\\r?\\n");

        if (split.length > 0) {
            return APOption.get(split[0]);
        }
        return null;
    }


    /*
    TODO: Added because of StaleElementReferenceException
    Find the cause of StaleElementReferenceException
     */
    private void waitUntilElementIsDisplayed(final WebElement webElement) {
        new WebDriverWait(getDriver(), TimeOuts.TEN_SECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class)
                .until(new Function<WebDriver,Boolean>() {
                    @Override
                    public Boolean apply(WebDriver input) {
                        return webElement.isDisplayed();
                    }
                });
    }


    private boolean isCurrent(APOption option) {
        return option == getSelectedOption();
    }

    @Override
    public ImmutableCollection<APOption> getAvailableOptions() {
        if (availableOptions == null) {
            Matcher matcher = AP_OPTION_PATTERN.matcher(webElements.apMenu.getAttribute("innerHTML"));
            List<APOption> options = new ArrayList<>();
            while (matcher.find()) {
                String menu = matcher.group(1).toUpperCase();
                if (menu.equals("CUSTOM")) {
                    menu += " PERIOD";
                } else if (menu.equals("TREND")) {
                    menu += " PERIODS";
                }
                options.add(APOption.get(menu));
            }
            return ImmutableList.copyOf(options);
        }
        return availableOptions;
    }


    @Override
    public boolean isOptionExists(APOption option) {
        return getAvailableOptions().contains(option);
    }

    @Override
    public Optional<IPeriod> getSelectedPeriod() {
        APOption current = getSelectedOption();

        if (current == FULL_CYCLE || current == ROLLING_YEAR || current == CUSTOM_PERIOD) {

            String currentOption = webElements.apBodyElm.getText();
            String[] split = currentOption.split("\\r?\\n");

            if (split.length >= 2) {
                String periodStr = split[1];
                IPeriod period = Periods.fromMIAnalysisPeriodString(periodStr);
                return Optional.of(period);
            } else {
                throw new AutomationException("AP - selected period text parsing error.");
            }
        }

        return Optional.absent();
    }

    @Override
    public Optional<ITrendPeriod> getTrendPeriodIfSelected() {
        if (!isCurrent(TREND_PERIODS)) {
            return Optional.absent();
        }

        String currentOption = webElements.apBodyElm.getText();
        String[] split = currentOption.split("\\r?\\n");

        // [Trend Periods, P1: Oct 2011 To Oct 2011, P2: Oct 2011 To Oct 2011]
        // custom - Oct 2011 thru Oct 2011

        if (split.length >= 3) {
            String p1Str = split[1];
            String[] p1Splits = p1Str.split(":");
            IPeriod p1 = Periods.fromMIAnalysisPeriodString(p1Splits[1].trim());

            String p2Str = split[2];
            String[] p2Splits = p2Str.split(":");
            IPeriod p2 = Periods.fromMIAnalysisPeriodString(p2Splits[1].trim());
            ITrendPeriod tp = new TrendPeriod(p1, p2);
            return Optional.of(tp);
        } else {
            throw new RuntimeException("AUTOMATION :: Error in parsing text '" + currentOption + "', revise the parsing logic.");
        }
    }

    @Override
    public IPeriod getFullCyclePeriod() {
        if (fullCyclePeriod == null) {
            doSelect(FULL_CYCLE);
            fullCyclePeriod = getSelectedPeriod().get();
        }
        return fullCyclePeriod;
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "anaPeriod_menu")
        private WebElement apMenu = null;

        @FindBy(id = "d2Form:anaPeriod_body")
        private WebElement apBodyElm = null;

        @FindBy(xpath = "//*[@id=\"d2Form:anaPeriod_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement apMenuButtonElm = null;

        @FindAll({@FindBy(id = "d2Form:anaPeriodlink1"), @FindBy(linkText = "Full Cycle")})
        private WebElement fullCycleElm = null;

        @FindAll({@FindBy(linkText = "Contract Year"), @FindBy(id = "d2Form:anaPeriodlink2")})
        private WebElement contractElm = null;

        @FindAll({@FindBy(linkText = "Rolling Year"), @FindBy(id = "d2Form:anaPeriodlink3")})
        private WebElement rollingElm = null;

        @FindAll({@FindBy(linkText = "Custom"), @FindBy(id = "d2Form:anaPeriodlink4")})
        private WebElement customElm = null;

        @FindAll({@FindBy(id = "d2Form:link5"), @FindBy(id = "d2Form:anaPeriodlink5")})// for Disease Registerery
        private WebElement trendElm = null;
    }

}