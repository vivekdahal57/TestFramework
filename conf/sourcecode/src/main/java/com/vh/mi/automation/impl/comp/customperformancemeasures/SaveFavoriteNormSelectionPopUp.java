package com.vh.mi.automation.impl.comp.customperformancemeasures;

import com.vh.mi.automation.api.comp.customperformancemeasures.ISaveFavoriteNormSelection;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 2/1/18.
 */
public class SaveFavoriteNormSelectionPopUp extends AbstractWebComponent implements ISaveFavoriteNormSelection{
    private LoadingPopup loadingPopup;

    public SaveFavoriteNormSelectionPopUp(WebDriver webDriver){
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        loadingPopup = new LoadingPopup(webDriver);

    }
    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void doSaveNormSelection(String viewName) {
        SeleniumUtils.hover(getDriver(), favoriteViewsHoverElement);
        WaitUtils.waitForSeconds(2);
        String xpath = "//*[@id = 'savedView_menu']//a[text()='Save As']";
        SeleniumUtils.click(saveAsLink);
        viewNameInputBox.sendKeys(viewName);
        saveButton.click();
    }

    @FindBy(id = "d2Form:savedViewName")
    WebElement viewNameInputBox;

    @FindBy(xpath = "//table[@id = 'd2Form:_saveSavedViewModelPanelContentTable']//input[@value = 'Save']")
    WebElement saveButton;

    @FindBy(xpath = "//*[@id=\"d2Form:savedView_panel_header\"]/table/tbody/tr/td/div/div[2]")
    private WebElement favoriteViewsHoverElement;

    @FindBy(xpath = "//*[@id = 'savedView_menu']//a[text()='Save As']")
    private WebElement saveAsLink;

}
