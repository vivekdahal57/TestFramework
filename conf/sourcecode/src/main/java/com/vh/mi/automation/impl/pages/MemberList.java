package com.vh.mi.automation.impl.pages;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80689 on 12/11/2015.
 */
public class MemberList extends AbstractWebComponent {
    private WebElements webElements;


    public MemberList(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    private void showMemberListMenu() {
        Action action = new Actions(getDriver()).moveToElement(webElements.memberListMenu).build();
        action.perform();
    }

    private void clickDynamicMemberListMenu() {
        Actions click = new Actions(getDriver()).moveToElement(webElements.dynamicMemberList).click(webElements.dynamicMemberList);
        click.perform();


    }

    public CreateDynamicConditionTrackingList saveDynamicMemberList() {
        showMemberListMenu();
        clickDynamicMemberListMenu();
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        CreateDynamicConditionTrackingList createDMLPage = new CreateDynamicConditionTrackingList(getDriver());
        createDMLPage.doWaitTillFullyLoaded(100);
        createDMLPage.fillConditionTrackingList();
        createDMLPage.doWaitTillFullyLoaded(100);
        return createDMLPage;



    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }


    public static class WebElements {

        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//div[@id='d2Form:memList_panel_header']//div[@class='d2-tglPanel-mark_1']")
        WebElement memberListMenu;
        @FindBy(id = "d2Form:memList_createDynamicMemList")
        WebElement dynamicMemberList;
        @FindBy(id = "d2Form:memListName")
        WebElement dynamicMemberListName;
        @FindBy(id = "d2Form:j_id58")
        WebElement saveMemList;
        @FindBy(id = "selectAllIcon_select_drillMenu_drillImage")
        WebElement selectConditions;




    }
}
