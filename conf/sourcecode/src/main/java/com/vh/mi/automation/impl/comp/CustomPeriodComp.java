package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.ap.ICustomPeriodComp;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80448 on 9/3/2014.
 */
public class CustomPeriodComp extends AbstractPeriodComp implements ICustomPeriodComp {
    public static abstract class XPath {
        public static final String HEADER = "//*[@id=\"d2Form:anaPeriod_provContainer_header\"]/span";
    }

    public static abstract class IDs {
        public static final String FROM_SELECT = "d2Form:anaPeriodCustom_hiddenIPfirstDate";
        public static final String TO_SELECT = "d2Form:anaPeriodCustom_hiddenIPsecondDate";
        public static final String OK_BUTTON = "d2Form:anaPeriod_CustomOK";

        //Added ID for new claims module
        public static final String FROM_SELECT1 = "d2Form:anaPeriodCustom_hiddenIPfirstDate1";
        public static final String TO_SELECT1 = "d2Form:anaPeriodCustom_hiddenIPsecondDate1";
    }

    @FindBy(xpath = XPath.HEADER)
    private WebElement headerElm;

    @FindAll({@FindBy(id = IDs.FROM_SELECT),@FindBy(id = IDs.FROM_SELECT1)})
    WebElement fromElm;

    @FindAll({@FindBy(id = IDs.TO_SELECT),@FindBy(id =IDs.TO_SELECT1)})
    WebElement toElm;



    @FindBy(id = IDs.OK_BUTTON)
    private WebElement okButton;

    public CustomPeriodComp(WebDriver driver) {
        super(driver);
        PageFactory.initElements(getDriver(), this);
    }

    @Override
    protected WebElement getHeaderElement() {
        return headerElm;
    }

    @Override
    protected WebElement getFromElement() {
        return fromElm;
    }

    @Override
    protected WebElement getToElement() {
        return toElm;
    }

    @Override
    public void doSelectAndApply(DateTime from, DateTime to) {
        doSelect(from, to);
        applySelection();
    }

    private void applySelection() {
        applyAndWait(okButton);
    }

    private void applyAndWait(WebElement periodElm) {

        SeleniumUtils.click(periodElm,getDriver());
        if (isAlertPresent()) {
            logger.info("Invalid period selection. Alert ....");
            logger.info("Alert message {}", getDriver().switchTo().alert()
                    .getText());
        } else {
            new LoadingPopup(getDriver()).waitTillDisappears();
        }
    }

    @Override
    public void doApplyDefaultSelects() {
        applySelection();
    }
}
