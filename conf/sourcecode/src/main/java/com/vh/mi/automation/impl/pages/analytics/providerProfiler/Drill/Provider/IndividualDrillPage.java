package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.IIndividualDrillPage;
import com.vh.mi.automation.impl.comp.MemberList;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 6/29/2017.
 */
public class IndividualDrillPage extends AbstractDrillPage implements IIndividualDrillPage {

    private IMemberList memberList;
    private LoadingPopup loadingPopup;
    private WebElements webElements;

    public IndividualDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
        memberList = new MemberList(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return  "(V048a2) Paid by Specialty, Provider and Member".equals(webElements.pageTitle.getText());
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public IMemberList getMemberList() {
        return memberList;
    }

    @Override
    public void createDymanicMemberList(int topN,String memListName){
        getMemberList().clickCreateDynamicMemberList().selectAndCreateDynamicMemList(memListName,topN);
        loadingPopup.waitTillDisappears();
    }

    @Override
    public boolean deleteMemberList(String memListName){
        getMemberList().deleteMyMemberListWithName(memListName);
        return getMemberList().getOperationCompleteStatusMessage().equalsIgnoreCase(getMemberList().getExpectedMessageForMemberListDeletion());
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

    }

}
