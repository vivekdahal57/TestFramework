package com.vh.mi.automation.impl.pages;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPanelComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractMIPage;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by i80689 on 12/14/2015.
 */
public class CreateDynamicConditionTrackingList extends AbstractMIPage {
    //IBusinessLevelsComponent businessLevelsComponent;
    private WebElements webElements;

    public CreateDynamicConditionTrackingList(WebDriver driver) {
        super(driver);

        webElements = new WebElements(driver);
        // businessLevelsComponent = new BusinessLevelsComponent(driver);
    }


    @Override
    public String getPageTitle() {
        return "(ML101) Create Dynamic Condition Tracking List";
    }

    @Override
    public String getPageId() {
        return "(ML101)";
    }

    //IBusinessLevelsComponent getBusinessLevelsComponent(){
    //   return businessLevelsComponent;
    // }
    public void fillConditionTrackingList() {
        Actions click_mem_list_name = new Actions(getDriver()).moveToElement(webElements.dynamicMemberListName).click(webElements.dynamicMemberListName);
        click_mem_list_name.perform();
        webElements.dynamicMemberListName.sendKeys("dynamic_automated_list");
        //Actions click_select_all = new Actions(getDriver()).moveToElement(webElements.selectConditions).click(webElements.selectConditions);
        //click_select_all.perform();
        Actions click_qrm_filter = new Actions(getDriver()).moveToElement(webElements.filterCondition).click(webElements.filterCondition);
        click_mem_list_name.perform();
        webElements.filterCondition.sendKeys("2001");
        Actions click_apply_filter = new Actions(getDriver()).moveToElement(webElements.applyFilter).click(webElements.applyFilter);
        click_apply_filter.perform();
        Actions click_condition = new Actions(getDriver()).moveToElement(webElements.chooseCondition).click(webElements.chooseCondition);
        click_condition.perform();
        Actions click_save = new Actions(getDriver()).moveToElement(webElements.saveMemList).click(webElements.saveMemList);
        click_save.perform();
        waitTillLoadingPopUpIsDisplayed();


    }

    private void waitTillLoadingPopUpIsDisplayed() {
        new WebDriverWait(getDriver(), TimeOuts.THREE_AND_HALF_MINUTES).ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class).until(new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return new LoadingPopup(getDriver(), TimeOuts.THREE_AND_HALF_MINUTES).isDisplayed();
            }
        });
    }


    public static class WebElements {

        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:memListName")
        WebElement dynamicMemberListName;
        @FindBy(id = "d2Form:j_id58")
        WebElement saveMemList;
        @FindBy(id = "selectAllIcon_select_drillMenu_drillImage")
        WebElement selectConditions;
        @FindBy(id = "d2Form:simpleGrid:column_QUALITYQUESTIONID_header_filterInputText")
        WebElement filterCondition;
        @FindBy(id = "applyFilterIcon_select_drillMenu_drillImage")
        WebElement applyFilter;
        @FindBy(xpath = "//tbody[@id='d2Form:simpleGrid:tb']//input[starts-with(@name,'d2Form:simpleGrid:')]")
        WebElement chooseCondition;
        // @FindBy(class="d2-link-close")
        // WebElement closeButton;


    }


}
