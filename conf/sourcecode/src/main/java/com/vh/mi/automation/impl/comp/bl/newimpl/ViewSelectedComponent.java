package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class ViewSelectedComponent extends AbstractWebComponent implements IBusinessLevelsComponent.IBusinessLevel.IViewSelected {
    private final WebElements webElements;

    public ViewSelectedComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.changeSelectionButton.isDisplayed();
    }

    @Override
    public String getTitle() {
        return webElements.title.getText();
    }

    @Override
    public int getNumberOfSelectedItems() {
        String selectedCountText = webElements.selectedCount.getText();
        assertThat(selectedCountText).startsWith("(").endsWith(")");
        return Integer.valueOf(selectedCountText.replaceAll("[)(]", ""));
    }

    @Override
    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();

        do {
            List<WebElement> tableRows = webElements.selectionTable.findElements(By.xpath(".//tbody/tr"));
            for (WebElement tableRow : tableRows) {
                selectedItems.add(tableRow.getText());
            }
        } while (nextPage());

        return selectedItems;
    }

    private boolean nextPage() {
        String style = webElements.nextLink.getAttribute("style");
        if (style.toLowerCase().contains("text-decoration: underline")) {
            webElements.nextLink.click();
            WaitUtils.waitForMilliSeconds(500);
            return true;
        } else
            return false;
    }

    @Override
    public void doClose() {
        SeleniumUtils.click(webElements.close, getDriver());
        //added because sometimes clicking the close button does not seem to work

        new WebDriverWait(getDriver(), TimeOuts.FIVE_SECONDS)
                .until(new Function<WebDriver,Boolean>() {

            @Override
            public Boolean apply(WebDriver input) {
                return !isFullyLoaded();
            }
        });
        try {
            if (webElements.changeSelectionButton.isDisplayed()) {
                SeleniumUtils.click(webElements.close, getDriver());
            }
        } catch (Exception ex) {
            logger.error("Exception occured in doClose {} " + ex);
        }

    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//input[@value='Change Selection']")
        WebElement changeSelectionButton;

        @FindBy(xpath = "//div[@id='NKeyOptionsDisplay']//span[starts-with(@id,'selected_count_')]")
        WebElement selectedCount;

        @FindBy(xpath = "//div[starts-with(@id,'_nKeyMoreSelection_holder_')]/table")
        WebElement selectionTable;

        //just using id='_general_panel_control' does not work for remote firefox
        @FindBy(xpath = "//*[@id='_general_panel_control']/img")
        WebElement close;

        @FindBy(id = "_general_panel_title")
        WebElement title;

        @FindBy(xpath = "//div[@id='NKeyOptionsDisplay']//a[starts-with(@id,'moreSelection_next_') and text()='Next']")
        WebElement nextLink;

    }
}
