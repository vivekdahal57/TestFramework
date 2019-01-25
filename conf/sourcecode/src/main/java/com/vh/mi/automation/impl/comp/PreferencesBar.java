package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.IIndvClaimsDetailExtractPopUp;
import com.vh.mi.automation.api.comp.IPreferencesBar;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 7/20/2017.
 */
public class PreferencesBar extends AbstractWebComponent implements IPreferencesBar {


    WebElements webElements;

    public PreferencesBar(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void exportTOPDF() {
        webElements.pdfButton.click();
    }

    @Override
    public void sendToExcel() {
        webElements.excelButton.click();
    }

    @Override
    public void offlineExcel() {
        webElements.offlineExcelButton.click();
    }

    @Override
    public void csv() {
        webElements.csvButton.click();
    }

    @Override
    public IIndvClaimsDetailExtractPopUp indvClaimsDetailsExport() {
        webElements.indvClaimDetailsExportButton.click();
        IndvClaimsDetailExtractPopUp indvClaimsDetailExtractPopUp = new IndvClaimsDetailExtractPopUp(getDriver());
        indvClaimsDetailExtractPopUp.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return indvClaimsDetailExtractPopUp;
    }

    @Override
    public SelectTemplatePage sendToOutBox() {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnSendToOutBox);
        SeleniumUtils.click(webElements.btnSendToOutBox);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        SelectTemplatePage selectTemplatePage = new SelectTemplatePage(getDriver());
        selectTemplatePage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return selectTemplatePage;
    }

    @Override
    public void backButton() {
        webElements.backButton.click();
    }

    @Override
    public void resetToDefaultViewButton() {
        webElements.resetToDefaultViewButton.click();
    }

    @Override
    public void viewReport() {
        webElements.viewReport.click();
    }

    @Override
    public void scoreCard() {
        webElements.scoreCared.click();
    }

    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id="d2Form:sendToPdf")
        WebElement pdfButton;

        @FindBy(id ="d2Form:sendToExcel")
        WebElement excelButton;

        @FindBy(id = "d2Form:offlineExcel")
        WebElement offlineExcelButton;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr//a[.=\"Send to Outbox\"]")
        WebElement btnSendToOutBox;

        @FindBy(xpath="//*[@id='d2Form:topToolBar']/tbody//td/a[text()='CSV']")
        WebElement csvButton;

        @FindBy(xpath="//*[@id=\"d2Form:topToolBar\"]//a[text()='Export Individual Claim Details']")
        WebElement indvClaimDetailsExportButton;

        @FindBy(xpath="//*[@id='d2Form:topToolBar']/tbody//td/a[text()='Back']")
        WebElement backButton;

        @FindBy(xpath="//*[@id='d2Form:topToolBar']/tbody//tr/td/a[text()='Reset to Default View'] ")
        WebElement resetToDefaultViewButton;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td[6]/table/tbody/tr[2]/td/a")
        WebElement viewReport;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td[5]/table/tbody/tr[2]/td/a")
        WebElement scoreCared;

    }
}
