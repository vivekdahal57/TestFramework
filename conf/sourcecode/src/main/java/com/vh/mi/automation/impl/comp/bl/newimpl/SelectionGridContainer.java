package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.impl.comp.LoadingPanelComp;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent.ISelectionGridContainer;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Selenium Implementation of {@link ISelectionGridContainer}
 *
 * @author nimanandhar
 */
public class SelectionGridContainer extends AbstractWebComponent implements ISelectionGridContainer {
    private final WebElements webElements;

    public SelectionGridContainer(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public List<String> getSelectionOptionsOnCurrentPage() {
        List<WebElement> elements = webElements.selectionOptionTableBody.findElements(By.xpath("./tr/td[2]"));
        List<String> options = new ArrayList<>();
        for (WebElement element : elements) {
            options.add(element.getText());
        }
        return options;
    }

    @Override
    public List<String> getCheckedOptionsOnCurrentPage() {
        List<WebElement> checkBoxElements = webElements.selectionOptionTableBody.findElements(By.xpath("./tr/td/input[@type='checkbox'] "));
        List<String> checkedOptions = new ArrayList<>();

        for (WebElement element : checkBoxElements) {
            if(element.isSelected()) {
                String checkedText = element.findElement(By.xpath("./parent::td/following-sibling::td[1]")).getText();
                checkedOptions.add(checkedText);
            }
        }
        return checkedOptions;
    }


    @Override
    public void doCheckOptions(List<String> itemsToCheck) {
        for (String item : itemsToCheck) {
            doCheckOption(item);
        }
    }

    @Override
    public void doCheckOption(String option) {
        String xpathExpression = "./tr/td[position()=2 and text()='" + option + "']/preceding-sibling::td/input[@type='checkbox']";
        WebElement checkBoxElement = webElements.selectionOptionTableBody.findElement(By.xpath(xpathExpression));
        assertThat(checkBoxElement.getAttribute("checked")).as("precondition the checkbox should be unchecked").isNullOrEmpty();
        SeleniumUtils.click(checkBoxElement,getDriver());
    }

    @Override
    public void doCheckRadioOption(String option) {
        String xpathExpression = "./tr/td[position()=2 and text()='" + option + "']/preceding-sibling::td/input[@type='radio']";
        WebElement radioButtonElement = webElements.selectionOptionTableBody.findElement(By.xpath(xpathExpression));
        assertThat(radioButtonElement.getAttribute("checked")).as("precondition the checkbox should be unchecked").isNullOrEmpty();
        SeleniumUtils.click(radioButtonElement,getDriver());
    }


    @Override
    public void doChooseMenuSelectAll() {
        new Actions(getDriver()).moveToElement(webElements.selectAllDrillImage).perform();
        SeleniumUtils.click(webElements.selectAllLink,getDriver());
        new LoadingPanelComp(getDriver()).waitTillDisappears();
    }

    @Override
    public void doChooseMenuSelectAllOnPage() {
        new Actions(getDriver()).moveToElement(webElements.selectAllDrillImage).perform();
       SeleniumUtils.click(webElements.selectAllOnPageLink, getDriver());
    }


    public boolean isRadioButtonComponentVisible() {
        try {
            WebElement radioBtnIsDisplayed = webElements.nKeyRadioBtn;
            radioBtnIsDisplayed.isDisplayed();
            return true;
        }
        catch (WebDriverException e)  {
            return false;
        }

    }
    public boolean isRadioButtonComponentSelected() {
        WebElement radioBtnIsDisplayed = webElements.nKeyRadioBtn;
        return radioBtnIsDisplayed.isSelected();
    }

    @Override
    public void doChooseMenuUnselectAll() {
        new Actions(getDriver()).moveToElement(webElements.selectAllDrillImage).perform();
       SeleniumUtils.click(webElements.unselectAllLink,getDriver());
    }

    @Override
    public void doChooseMenuSelectAndApply(String selection) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "_nKeyGrid:selAll")
        WebElement selectAllLink;

        @FindBy(id = "_nKeyGrid:selAllonPage")
        WebElement selectAllOnPageLink;

        @FindBy(id = "d2-dropMenu d2-unselected-link")
        WebElement unselectAllLink;

        @FindBy(id = "_nKeyGrid:tb")
        WebElement selectionOptionTableBody;

        @FindBy(id = "selectAllIconNkey_select_drillMenu_drillImage")
        WebElement selectAllDrillImage;

        @FindBy(id = "d2Form:mainNKey_nKeyTable:tb")
        WebElement nKeyTableBody;

        @FindBy(xpath = "//*[@id=\"d2-nkey-radio-2\"]")
        WebElement nKeyRadioBtn;


    }
}
