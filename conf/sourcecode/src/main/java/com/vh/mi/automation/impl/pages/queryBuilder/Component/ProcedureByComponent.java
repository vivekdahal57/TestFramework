package com.vh.mi.automation.impl.pages.queryBuilder.Component;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.Component.ISelectProcedureComponent;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.procedure.ProcedureCode;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 2/1/2017.
 */
public class ProcedureByComponent extends AbstractWebComponent implements ISelectProcedureComponent{

    private WebElements webElements;
    ProcedureCode procedureCode;

    public ProcedureByComponent(WebDriver driver) {
        super(driver);
        procedureCode=new ProcedureCode(getDriver());
        webElements=new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public ProcedureCode selectProcedureCode() {
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverProcedure);
        SeleniumUtils.hover(getDriver(),webElements.hoverProcedure);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnProcedureCode);
        SeleniumUtils.click(webElements.btnProcedureCode);
        procedureCode.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return procedureCode;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:breakDownLevel_1_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement hoverProcedure;

        @FindBy(id = "d2Form:link_21")
        private WebElement btnProcedureCode;

        @FindBy (id = "d2Form:link_31")
        private WebElement btnDxCG;
    }
}
