package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 6/23/2017.
 */
public class IndvDynamicMemberList extends AbstractWebComponent implements IIndvDynamicMemberList{

    private WebElements webElements;
    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";

    public IndvDynamicMemberList(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    private void selectMemberId(int topN){
        //for (int i=1;i<=topN;i++){
            String xpath = "(//*[@id='d2Form:simpleGrid:tb']//td/input[@type='checkbox'])[${nthCheckbox}]";
            SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(NTH_CHECKBOX_PLACEHOLDER,String.valueOf(topN))).click();
        //}
    }

    @Override
    public void selectAndCreateDynamicMemList(String memListName,int topN){
        SeleniumUtils.sendKeysToInput(memListName,webElements.memListTextBox);
        selectMemberId(topN);
        webElements.saveBtn.click();
        //WaitUtils.waitForSeconds(1);
        waitForLoadingPopup();
        waitTillDocumentReady(TimeOuts.TEN_SECONDS);
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    private void waitForLoadingPopup(){
        try{
            new LoadingPopup(getDriver()).waitTillDisappears();
        }catch(StaleElementReferenceException e){
            new LoadingPopup(getDriver()).waitTillDisappears();
        }
    }

    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id="d2Form:testLink")
        WebElement pageTitle;

        @FindBy(id="d2Form:memListName")
        WebElement memListTextBox;

        @FindBy(xpath = "//table[@id='d2Form:topToolBar']//a[text()='Save']")
        WebElement saveBtn;

        @FindBy(xpath = "//table[@id='d2Form:topToolBar']//a[text()='Close']")
        WebElement closeBtn;
    }

}
