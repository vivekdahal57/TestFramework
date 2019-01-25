package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents component which allows one to select values for Business Levels
 *
 * @author nimanandhar
 */
public class BusinessLevelSelectionComponent extends AbstractWebComponent implements IBusinessLevelSelectionComponent {
    private final WebElements webElements;
    private final ILoadingComp loadingPopup;

    private IReadOnlyContainer readOnlyContainer;
    private ISelectionGridContainer selectionGridContainer;
    private IPaginationComponent paginationComponent;

    public BusinessLevelSelectionComponent(WebDriver driver, BL businessLevel) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
        this.readOnlyContainer = new ReadOnlyContainer(driver);
        this.selectionGridContainer = new SelectionGridContainer(driver);
        this.paginationComponent = PaginationComponent.newBLPaginationComponent(driver);
    }

    @Override
    public void doClose() {
        webElements.closeButton.click();
    }

    @Override
    public void doResetSelection() {
//        webElements.resetSelectionButton.click();
        SeleniumUtils.click(webElements.resetSelectionButton,getDriver());
        loadingPopup.waitTillDisappears();
    }

    @Override
    public void doApply() {
//        webElements.applyButton.click();
        SeleniumUtils.click(webElements.applyButton,getDriver());
        loadingPopup.waitTillDisappears();
    }

    @Override
    public String getDisplayedTitle() {
        return webElements.panelTitle.getText();
    }

    @Override
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public IReadOnlyContainer getReadOnlyContainer() {
        return readOnlyContainer;
    }

    @Override
    public ISelectionGridContainer getSelectionGridContainer() {
        return selectionGridContainer;
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.applyButton.isDisplayed();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id='_nKeyButtons']//input[@value='Close']")
        WebElement closeButton;

        @FindBy(xpath = "//*[@id='_nKeyButtons']//input[@value='Apply']")
        WebElement applyButton;

        @FindBy(xpath = "//*[@id='_nKeyButtons']//input[@value='Reset Selection']")
        WebElement resetSelectionButton;

        @FindBy(id = "_general_panel_title")
        WebElement panelTitle;
    }
}
