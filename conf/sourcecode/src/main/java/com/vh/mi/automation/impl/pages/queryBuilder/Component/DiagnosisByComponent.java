package com.vh.mi.automation.impl.pages.queryBuilder.Component;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.Component.ISelectDiagnosisComponent;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DiagnosisCode;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Diagnosis.DxCG;

/**
 * Created by i20345 on 12/28/2016.
 */
public class DiagnosisByComponent extends AbstractWebComponent implements ISelectDiagnosisComponent{

    private WebElements webElements;
    DiagnosisCode diagCode;

    public DiagnosisByComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        diagCode=new DiagnosisCode(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public DiagnosisCode selectDiagnosisCode() {
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverDiagnosis);
        SeleniumUtils.hover(getDriver(),webElements.hoverDiagnosis);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnDiagnosisCode);
        SeleniumUtils.click(webElements.btnDiagnosisCode);
        diagCode.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return diagCode;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id='d2Form:breakDownLevel_header']/table/tbody/tr/td/div/div[2]")
        private WebElement hoverDiagnosis;

        @FindBy(id = "d2Form:link_21")
        private WebElement btnDiagnosisCode;

        @FindBy (id = "d2Form:link_31")
        private WebElement btnDxCG;
    }
}
