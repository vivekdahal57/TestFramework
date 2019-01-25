package com.vh.mi.automation.impl.comp.qrm;

import com.vh.mi.automation.api.comp.qrm.ISelectFavoriteQRMList;
import com.vh.mi.automation.api.comp.qrm.QRMException;
import com.vh.mi.automation.api.comp.qrm.QRMNotSelectedException;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.comp.MessagePopup;
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

class SelectFavoriteQRMList extends AbstractWebComponent implements ISelectFavoriteQRMList {
    private static final String SELECT_QRM_MESSAGE = "Select one or more QRM lists";
    private MessagePopup messagePopup;

    public SelectFavoriteQRMList(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        messagePopup = new MessagePopup(getDriver());
    }

    @Override
    public List<String> getQRMList() {
        List<WebElement> qrmElements = qrmListGrid.findElements(By.xpath("./tbody/tr/td[2]"));
        List<String> qrmList = new ArrayList<>();
        for (WebElement qrmElement : qrmElements) {
            qrmList.add(qrmElement.getText());
        }
        return qrmList;
    }

    @Override
    public String doSelect(String qrm) {
        return doSelect(Arrays.asList(qrm));
    }


    @Override
    public String doSelect(List<String> qrms) {
        for (String qrm : qrms) {
            String xpath = String.format("./tbody/tr/td[position()=2 and text()='%s']/preceding-sibling::td/input[@type='checkbox']", qrm);
            WebElement checkBoxElement = qrmListGrid.findElement(By.xpath(xpath));
            SeleniumUtils.click(checkBoxElement,getDriver());
        }

        doClickSelectButton();

        if (SeleniumUtils.isAlertPresent(getDriver())) {
            String message = SeleniumUtils.getAlertTextAndDismissAlert(getDriver());
            doClose(); // an unexpected situation occured and perhaps the user cannot continue so close this component
            if (message.equals(SELECT_QRM_MESSAGE)) {
                throw new QRMNotSelectedException(message);
            } else
                throw new QRMException(message);
        }

        new LoadingPopup(getDriver()).waitTillDisappears();
        WaitUtils.waitForMilliSeconds(500);
        messagePopup.waitTillDisappears();
        return messagePopup.getMessage();
    }


    private void doClickSelectButton() {
        SeleniumUtils.click(selectButton,getDriver());
    }

    @Override
    public void doClose() {
        SeleniumUtils.click(closeButton,getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return selectButton.isDisplayed();
    }

    @FindBy(xpath = "//table[@id='d2Form:_otherActionFavQrmListModelPanelContentTable']//input[@value='Select']")
    WebElement selectButton;

    @FindBy(xpath = "//table[@id='d2Form:_otherActionFavQrmListModelPanelContentTable']//input[@value='Close']")
    WebElement closeButton;

    @FindBy(id = "d2Form:selectFavQrmListIdsGrid")
    WebElement qrmListGrid;
}
