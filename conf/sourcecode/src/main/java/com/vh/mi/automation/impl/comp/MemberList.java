package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.IIndvDynamicMemberList;
import com.vh.mi.automation.impl.pages.analytics.individuals.IndvDynamicMemberList;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

/**
 * Created by i82716 on 6/22/2017.
 */
public class MemberList extends AbstractWebComponent implements IMemberList {
    private static final String MEMBERLIST_NAME_PLACEHOLDER = "${MemberList_Name}";

    LoadingPopup loading;

    WebElements webElements;

    public MemberList(WebDriver driver){
        super(driver);
        loading = new LoadingPopup(getDriver());
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void createStaticMemberList(String memListName) {
        SeleniumUtils.hoverOnElement(webElements.memberListMenu,getDriver());
        SeleniumUtils.click(webElements.createStaticMemListLink);
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.memListNameElm, TimeOuts.FIVE_SECONDS);
        SeleniumUtils.hoverOnElement(webElements.memberListMenu,getDriver());
        WaitUtils.waitUntilDisappear(getDriver(), webElements.createStaticMemListLink, TimeOuts.FIVE_SECONDS);
        SeleniumUtils.sendKeysToInput(memListName, webElements.memListNameElm);
        webElements.saveBtnElm.click();
        loading.waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.memberListOperationStatusMessage, TimeOuts.FIVE_SECONDS);
    }

    @Override
    public boolean checkIfMemberListExists(String memberListName){
      try {
          SeleniumUtils.hoverOnElement(webElements.memberListMenu, getDriver());
          WebElement memListLink = getMyMemberlistLink(memberListName);
          WaitUtils.waitUntilEnabled(getDriver(), memListLink);
          SeleniumUtils.click(memListLink);
          loading.waitTillDisappears();
          return isMemberListOpen(memberListName);
      }catch (NoSuchElementException ex){
          logger.info(memberListName + "NOT FOUND");
          return false;
      }
    }

    public WebElement getMyMemberlistLink(String memberListName){
        String xpath = "//*[@id=\"d2Form:deleteSaveditemGrid\"]//a[contains(.,'${MemberList_Name}')]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(MEMBERLIST_NAME_PLACEHOLDER, memberListName));
    }

    private boolean isMemberListOpen(String memberListName){
        return webElements.memberListDescriptionDiv.getText().equals(memberListName);
    }

    @Override
    public void deleteMyMemberListWithName(String memberListName){
        SeleniumUtils.hoverOnElement(webElements.memberListMenu,getDriver());
        WaitUtils.waitUntilEnabled(getDriver(), webElements.deleteListLink);
        SeleniumUtils.click(webElements.deleteListLink);
        loading.waitTillDisappears();
        SeleniumUtils.hoverOnElement(webElements.memberListMenu,getDriver());
        WaitUtils.waitUntilDisappear(getDriver(), webElements.deleteListLink, TimeOuts.FIVE_SECONDS);
        selectMemberListForDeletion(memberListName);
        SeleniumUtils.click(webElements.deleteListButton);
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        loading.waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.memberListOperationStatusMessage, TimeOuts.TEN_SECONDS);
    }


    private void selectMemberListForDeletion(String memberListName){
        String xpath = "//*[@id=\"d2Form:selectMemberListIdsGrid:tb\"]//td[contains(.,'${MemberList_Name}')]//preceding::td[1]//input[@type='checkbox'][1]";
        SeleniumUtils.click(SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(MEMBERLIST_NAME_PLACEHOLDER, memberListName)));
    }

    @Override
    public void goToSavedMemberList(String memberListName) {
        SeleniumUtils.hoverOnElement(webElements.memberListMenu,getDriver());
        WaitUtils.waitUntilEnabled(getDriver(), getMyMemberlistLink(memberListName));
        getMyMemberlistLink(memberListName).click();
        loading.waitTillDisappears();
    }

    @Override
    public IIndvDynamicMemberList clickCreateDynamicMemberList() {
        SeleniumUtils.hoverOnElement(webElements.memberListMenu, getDriver());
        SeleniumUtils.click(webElements.createDynamicMemListLink);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        IndvDynamicMemberList indvDynamicMemberListPage = PageFactory.initElements(getDriver(),IndvDynamicMemberList.class);
        indvDynamicMemberListPage.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return indvDynamicMemberListPage;
    }

    @Override
    public void resetSelection() {

    }

    @Override
    public String getOperationSuccessfulMessage(){
        String message = webElements.memberListOperationStatusMessage.getText();
        WaitUtils.waitUntilDisappear(getDriver(), webElements.memberListOperationStatusMessage, TimeOuts.TEN_SECONDS);
        return message;
    }

    @Override
    public String getOperationCompleteStatusMessage() {
        return webElements.memberListOperationStatusMessage.getText();
    }

    public String getExpectedMessageForMemberListDeletion(){
        return "The selected Member List(s) have been successfully removed.";
    }

    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:memList_panel_header\"]/table/tbody/tr/td/div/div[2]")
        WebElement memberListMenu;

        @FindAll({@FindBy(id = "d2Form:memList_CreateMemList"), @FindBy(linkText = "Create List with Selected Members")})
        WebElement createStaticMemListLink;

        @FindAll({@FindBy(id = "d2Form:memList_createDynamicMemList"), @FindBy(linkText = "Create Dynamic List")})
        WebElement createDynamicMemListLink;

        @FindAll({@FindBy(id = "d2Form:memList_ResetMemList"), @FindBy(linkText = "Reset Selection")})
        WebElement resetSelectionLink;

        @FindBy(id = "d2Form:memListName")
        WebElement memListNameElm;

        @FindBy(id = "d2Form:publicChkbox")
        WebElement publicCheckBox;

        @FindBy(id = "d2Form:hasCriteria")
        WebElement conditionCheckBox;

        @FindBy(xpath = "//table[@id='d2Form:_saveMemberListModelPanelContentTable']/tbody/tr/td[1]/input[@value='Save']")
        WebElement saveBtnElm;

        @FindBy(id = "ani_message")
        WebElement memberListOperationStatusMessage;

        @FindBy(id = "memList_descDiv")
        WebElement memberListDescriptionDiv;

        @FindBy(id = "d2Form:memList_DelMemList")
        WebElement deleteListLink;

        @FindBy(id = "d2Form:btnSelect")
        WebElement deleteListButton;

    }
}
