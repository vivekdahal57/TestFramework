package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.IIndvClaimsDetailExtractPopUp;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.comp.loading.*;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndvClaimsDetailExtractPopUp extends AbstractWebComponent implements IIndvClaimsDetailExtractPopUp {

    WebElements webElements;
    LoadingPopup loadingPopup;

    public IndvClaimsDetailExtractPopUp(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.popupDiv.isDisplayed();
    }

    public void setMembersToExport(String members){
        SeleniumUtils.sendKeysToInput(members, webElements.memberInputBox);
    }

    public void setJobName(String jobName){
        SeleniumUtils.sendKeysToInput(jobName, webElements.jobNameInputBox);
    }

    @Override
    public void exportToExcel(String members, String jobName){
        setMembersToExport(members);
        setJobName(jobName);
        SeleniumUtils.click(webElements.excelRadioButton);
        SeleniumUtils.click(webElements.sendExportButton);
        loadingPopup.waitTillDisappears();
        SeleniumUtils.click(webElements.jobProgressOkButton);
    }

    @Override
    public void exportToCsv(String members, String jobName){
        setMembersToExport(members);
        setJobName(jobName);
        SeleniumUtils.click(webElements.csvRadioButton);
        SeleniumUtils.click(webElements.sendExportButton);
        loadingPopup.waitTillDisappears();
        SeleniumUtils.click(webElements.jobProgressOkButton);
    }

    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id="d2Form:_panelSendVMRToExcelCDiv")
        WebElement popupDiv;

        @FindBy(id ="d2Form:vmrSendToExcelIn")
        WebElement memberInputBox;

        @FindBy(id = "d2Form:vmrCSVJobName")
        WebElement jobNameInputBox;

        @FindBy(id = "d2Form:csv-excelRadio:0")
        WebElement excelRadioButton;

        @FindBy(id = "d2Form:csv-excelRadio:1")
        WebElement csvRadioButton;

        @FindBy(id = "d2Form:sendVMRToExcel")
        WebElement sendExportButton;

        @FindBy(xpath = "//div[@id=\"d2Form:_vmrCsvJobProgressPanelContentDiv\"]//input[@type='button' and @value='OK']")
        WebElement jobProgressOkButton;

    }

}
