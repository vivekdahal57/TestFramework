package com.vh.mi.mxautomation.impl.pages.analytics.individuals;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.mxautomation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i81306 on 3/26/2018.
 */
public class Individual301 extends AbstractLandingPage {
    public static final String MODULE_ID = "4";
    WebElements webElements;


    public Individual301(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        //JavascriptExecutor jse = (JavascriptExecutor)driver;
       /* JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $('#NavHeader1_tabs_ctl00_btnNavHeaderTab');");
        stringBuilder.append("x.click();");
        js.executeScript(stringBuilder.toString());
*/
        //jse.executeScript("window.scrollBy(0,250)", "");
        //jse.executeScript("window")
        //WebElement b = driver.findElement(By.id(""));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
       // jse.executeScript("arguments[0].click();", webElements.column1);



    }

    public void test(WebDriver driver){

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("document.getElementById('gbqfb').click();");
    }


    public void filterAge(){
        webElements.filterIcon.click();
        SeleniumUtils.selectByVisibleText(webElements.columnSelect, "Age");
        SeleniumUtils.selectByValue(webElements.filterSelect, ">");
        SeleniumUtils.sendKeysToInput("10", webElements.filterValue);
    }


    public void testApp(){
        new NgWebDriver((JavascriptExecutor) getDriver()).waitForAngularRequestsToFinish();
      //  WaitUtils.waitForSeconds(10);
        webElements.filterIcon.click();

        SeleniumUtils.selectByVisibleText(webElements.columnSelect, "Age");
        SeleniumUtils.selectByValue(webElements.filterSelect, ">");
        SeleniumUtils.sendKeysToInput("10", webElements.filterValue);

        webElements.applyButton.click();
    }


    public void dragAndDrop() throws InterruptedException {

        SeleniumUtils.doubleClick(getDriver(), webElements.column1);
        SeleniumUtils.doubleClick(getDriver(), webElements.column2);
      /*  Actions builder = new Actions(getDriver());
        builder.clickAndHold(webElements.tagFirstCol).perform()
                .moveToElement(webElements.tagSecondCol).perform();
        Thread.sleep(2000);
        builder.release(webElements.tagSecondCol).build().perform();*/

        Actions builder = new Actions(getDriver());
        builder.clickAndHold(webElements.tagFirstCol).moveToElement(webElements.tagSecondCol).perform();
        builder.release().build().perform();
        System.out.println("ss");

    }

    public void customization(String fields ,String searchName){
        new NgWebDriver((JavascriptExecutor) getDriver()).waitForAngularRequestsToFinish();
       //WaitUtils.waitForSeconds(5);
        webElements.customizationIconn.click();
        //SeleniumUtils.click(webElements.customizationIcon);
        webElements.customizationDropDownIcon.click();
        WaitUtils.waitForSeconds(5);
        SeleniumUtils.selectByVisibleText(webElements.customizationFieldGroups, fields);
        System.out.println("test");
        List<WebElement> searchElements = SeleniumUtils.findElementsByXPath(getDriver(), "//app-modal-content/form/div[3]/div[2]/div//button");
        for(int i = 0 ; i < searchElements.size() ; i++){
           // searchElements.get(i).click().;
            Actions actions = new Actions(getDriver());

            actions.moveToElement(searchElements.get(i)).click().perform();
        }
        String searchElementXpath = "//app-modal-content/form/div[3]/div[1]/div/div[2]/ul//li[text()[contains(.,'$searchName')]]";
        WaitUtils.waitForSeconds(1);
        WebElement searchElement = SeleniumUtils.findElementByXPath(getDriver(), searchElementXpath.replace("$searchName" , searchName));
        WaitUtils.waitForSeconds(1);
        SeleniumUtils.click(searchElement);
        String xpath = "//app-modal-content/form/div[4]/div/button[text()='Apply']";
        WebElement applyButton = SeleniumUtils.findElementByXPath(getDriver(), xpath);
        SeleniumUtils.click(applyButton);
        System.out.println("test");
        //System.out.println("Test");
    }


    public void saveCohort(){
        new NgWebDriver((JavascriptExecutor) getDriver()).waitForAngularRequestsToFinish();
        webElements.icUsers.click();

    }



    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//app-data-grid")
        private WebElement dataGrid;

        @FindBy(xpath = "//app-customization//i[@class='ic-gear']")
        private WebElement gearIcon;

        @FindBy(className = "close-modal")
        private WebElement closeModal;

        @FindBy(className = "ic-filter")
        private WebElement filterIcon;

        @FindBy(xpath = "//app-modal-content//select[@formcontrolname='name']")
        private WebElement columnSelect;

        @FindBy(xpath = "//app-modal-content//select[@formcontrolname='op']")
        private WebElement filterSelect;

        @FindBy(xpath = "//app-modal-content//input")
        WebElement filterValue;

        @FindBy(xpath = "//app-modal-content//button[text()='Apply']")
        WebElement applyButton;

        @FindBy(xpath = "//app-data-grid//table//th[4]//span")
        WebElement column1;

        @FindBy(xpath = "//app-data-grid//table//th[5]//span")
        WebElement column2;

        @FindBy(xpath = "//app-data-grid//table//th[6]//span")
        WebElement column3;

        @FindBy(xpath = "//app-sort-list//div[@class='tags']//span[@draggable='true'][1]")
        WebElement tagFirstCol;

        @FindBy(xpath = "//app-sort-list//div[@class='tags']//span[@draggable='true'][2]")
        WebElement tagSecondCol;


        @FindBy(className = "badge-remove" )
        WebElement closeIcon;

        @FindBy(xpath = "//div[contains(@class, 'modal-content ng-star-inserted')]/a/*[name()='svg']")
        WebElement closeIconInFilterModal;

        @FindBy(className = "ic-users")
        WebElement icUsers;

        @FindBy(xpath = "//app-modal-content//span[@class ='ng-arrow-zone']")
        WebElement customizationDropDownIcon;

        @FindBy(className = "ic-gear")
        WebElement customizationIcon;

        @FindBy(xpath = "//div[@class='datagrid']//a//i[@class= 'ic-gear']")
        WebElement customizationIconn;

        @FindBy(xpath = "//app-customization//select[@formcontrolname = \"fieldCategory\"]")
        WebElement customizationFieldGroups;




    }


}
