package com.vh.mi.automation.impl.pages.outReach.letterGeneration;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.outReach.letterGeneration.IMemberListLG;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 8/9/2017.
 */
public class MemberListLG extends AbstractLandingPage implements IMemberListLG {
    private static final String MODULE_ID = "60";
    private static final String ROW_PLACE_HOLDER = "${row}";
    private static final String MEMBER_LIST_NAME_PLACE_HOLODER = "${dynamicMemberList}";
    private LoadingPopup loadingPopup;
    private WebElements webElements;
    SelectTemplatePage selectTemplatePage;

    public MemberListLG(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public void selectCreatedDynamicMemberList(String dynamicListName){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverApplyList);
        SeleniumUtils.hover(getDriver(),webElements.hoverApplyList);
        String xpath = "//*[@id=\"memConditionPanel_menu\"]//a[.=\"${dynamicMemberList}\"]";
        WebElement createdMemberList = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(MEMBER_LIST_NAME_PLACE_HOLODER,dynamicListName));
        WaitUtils.waitUntilEnabled(getDriver(),createdMemberList);
        SeleniumUtils.click(createdMemberList);
        loadingPopup.waitTillDisappears();
    }

    @Override
    public void selectCheckBoxesInFirstPage(int selection){
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[${row}]/td[1]/input[@type=\"checkbox\"]";
        for (int i = 1; i <= selection; i ++){
            WebElement chkBox = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_PLACE_HOLDER,Integer.toString(i)));
            WaitUtils.waitUntilEnabled(getDriver(),chkBox);
            SeleniumUtils.click(chkBox);
        }
    }

    public SelectTemplatePage goToSelectTemplatePage(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverSendToOutBox);
        SeleniumUtils.hover(getDriver(),webElements.hoverSendToOutBox);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnSendToOutbox);
        SeleniumUtils.click(webElements.btnSendToOutbox);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        selectTemplatePage = new SelectTemplatePage(getDriver());
        selectTemplatePage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return selectTemplatePage;
    }

    public static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:memCondition_1\"]//div[.=\"Apply List\"]//following::div[@class=\"d2-tglPanel-mark_1\"]")
        private WebElement hoverApplyList;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid\"]/thead/tr/th//a[@class=\"d2-drillbtn\"]")
        private WebElement hoverSendToOutBox;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a")
        private WebElement btnSendToOutbox;
    }
}
