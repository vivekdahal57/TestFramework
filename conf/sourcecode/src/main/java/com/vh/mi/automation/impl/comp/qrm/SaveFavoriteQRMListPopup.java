package com.vh.mi.automation.impl.comp.qrm;

import com.vh.mi.automation.api.comp.qrm.ISaveFavoriteQRMList;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.comp.MessagePopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

class SaveFavoriteQRMListPopup extends AbstractWebComponent implements ISaveFavoriteQRMList {
    private MessagePopup messagePopup;

    public SaveFavoriteQRMListPopup(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        messagePopup = new MessagePopup(driver, TimeOuts.SIXTY_SECONDS);
    }

    @Override
    public boolean isFullyLoaded() {
        return header.isDisplayed();
    }


    @Override
    public String doSaveQrm(String name) {
        saveQRMTextBox.clear();
        saveQRMTextBox.sendKeys(name);

        saveButton.click();
        new LoadingPopup(getDriver()).waitTillDisappears();
        WaitUtils.waitForMilliSeconds(500);
        messagePopup.waitTillDisappears();
        return messagePopup.getMessage();
    }

    @Override
    public void doClose() {
        throw new NotImplementedException();
    }

    @FindBy(id = "d2Form:_saveFavQrmListModelPanelHeader")
    WebElement header;

    @FindBy(id = "d2Form:txtSaveQRM")
    WebElement saveQRMTextBox;

    @FindBy(xpath = "//*[@id='d2Form:_saveFavQrmListModelPanelContainer']//input[@value='Save']")
    WebElement saveButton;
}
