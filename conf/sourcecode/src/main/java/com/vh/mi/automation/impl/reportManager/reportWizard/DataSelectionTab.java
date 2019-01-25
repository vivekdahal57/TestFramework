package com.vh.mi.automation.impl.reportManager.reportWizard;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.reportManager.reportWizard.IDataSelectionTab;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.ReportManagerLoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;


public class DataSelectionTab extends AbstractWebComponent implements IDataSelectionTab{

    private final WebElements webElements;
    private final ReportManagerLoadingPopup reportManagerLoadingPopup;

    public DataSelectionTab(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        reportManagerLoadingPopup = new ReportManagerLoadingPopup(driver);
    }


    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void selectApplicationFromDataSelectionTab(String reportTitle){
        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        SeleniumUtils.sendKeysToInput(reportTitle,webElements.titleNameTextBox);
        SeleniumUtils.click(webElements.applicationDrillElement);
        waitForAjaxCallToFinish();
        SeleniumUtils.click(webElements.getApplicationCheckBoxElement);
        webElements.addToSelectionButton.click();
    }

    @Override
    public List<String> selectApplicationAndBusinessLvlFromDataSelectionTab(String reportTitle, Map<BL,String> businessLevel) {
        List<String> lvlLabels = new ArrayList<>();
        SeleniumUtils.sendKeysToInput(reportTitle,webElements.titleNameTextBox);
        SeleniumUtils.click(webElements.applicationDrillElement);
        waitForAjaxCallToFinish();
        SeleniumUtils.click(webElements.getApplicationCheckBoxElement);
        webElements.addToSelectionButton.click();

        businessLevel.forEach( (bl, category)  -> {
            if (bl.getIndex() <= getMaxLevels()) {
                WebElement webElement = webElements.blSelection(bl);
                SeleniumUtils.click(webElement,getDriver());
                waitForAjaxCallToFinish();
                lvlLabels.addAll(categorySelection(bl.getIndex(), category));
                webElements.addToSelectionButton.click();
            }else{
                logger.info("Business Level " + bl.getIndex() + "not available");
            }
        });

        return lvlLabels;
    }

    @Override
    public void dataSelectionSaveAndContinue(){
        SeleniumUtils.click(webElements.saveAndContinueButton);
    }

    private List<String> categorySelection(int blIndex, String category){
        List<String> lvlLabels = new ArrayList<>();
        int maxCategoryLvl =  webElements.blCategorySelectionMenu.findElements(By.xpath("./tr")).size();
        List<String> blCategoryList = Arrays.asList(category.split("\\s*,\\s*"));
        blCategoryList.stream().forEachOrdered( cl -> {
            if (Integer.parseInt(cl) <= maxCategoryLvl){
                WebElement lvlCheckBox = webElements.blCategorySelectionMenu.findElement(By.xpath("./tr[" + cl + "]/td/input[@type='CHECKBOX']"));
                WebElement lvlLabel = webElements.blCategorySelectionMenu.findElement(By.xpath("./tr[" + cl + "]/td[2]/label"));
                lvlLabels.add(lvlLabel.getText());
                if(!lvlCheckBox.isSelected()) {
                    SeleniumUtils.click(lvlCheckBox);
                }
            }else {
                logger.info("Category " + cl + " not available at Businesslvl " + blIndex);
            }
        });

        return lvlLabels;
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        reportManagerLoadingPopup.waitTillDisappears();
        checkState(!reportManagerLoadingPopup.isDisplayed(), "The popup should have disappeared");
    }

    @Override
    public int getMaxLevels(){
        int maxLvl = webElements.blSelectionMenu.findElements(By.xpath("./tr")).size() - 1; //gives size of BL excluding Application Menu In Data Selection
        return maxLvl;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        //input[@name='repName']
        @FindBy (name = "repName")
        WebElement titleNameTextBox;

        @FindBy (xpath= "//form[@name='DataSelectionForm']//img[@id='EXPAND_BOB']")
        WebElement applicationDrillElement;

        @FindBy(xpath="(//form[@name='DataSelectionForm']//div[@id='DatasetRollup']//td/input[@type='CHECKBOX'])[1]")
        WebElement getApplicationCheckBoxElement;

        @FindBy (name = "btnAddPT")
        WebElement addToSelectionButton;

        @FindBy (name = "btnDSContinue")
        WebElement saveAndContinueButton;

        @FindBy(xpath = "//div[@id='drillPanel']/table/tbody")
        WebElement blSelectionMenu;

        @FindBy(xpath = "//div[@id='resultRoot']/table/tbody")
        WebElement blCategorySelectionMenu;

        private WebElement blSelection(BL bl){
            String xpath = "./tr[position()=" + (bl.getIndex() + 1 ) + "]/td[3]/img"; // Since In data selection , the first element appears to be application
            // selection , so bl index is incremented by 1 to satisfy business level with xpath.
            return blSelectionMenu.findElement(By.xpath(xpath));
        }
    }
}

