package com.vh.mi.automation.impl.comp.qrm;

import com.vh.mi.automation.api.comp.qrm.*;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public class FavoriteQRM extends AbstractWebComponent implements IFavoriteQRM {
    private static final String MENU_CREATE_LIST = "Create List";
    private static final String MENU_DELETE_LIST = "Delete List";
    private static final String QRM_CREATED_MESSAGE = "A new favorite QRM list %s created";
    private static final String QRM_DELETED_MESSAGE = "Selected List(s) Succesfully Deleted.";

    private final WebElements webElements;

    public FavoriteQRM(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.label.isDisplayed();
    }

    @Override
    public ISaveFavoriteQRMList doSelectMenuCreateList() {
        doClickMenu(MENU_CREATE_LIST);

        if (SeleniumUtils.isAlertPresent(getDriver())) {
            String message = SeleniumUtils.getAlertTextAndDismissAlert(getDriver());
            throw new QRMNotSelectedException(message);
        }

        SaveFavoriteQRMListPopup saveQRMListPopup = new SaveFavoriteQRMListPopup(getDriver());
        saveQRMListPopup.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return saveQRMListPopup;
    }

    @Override
    public void doSelectMenuAddToList(String qrmName) {
        throw new NotImplementedException();
    }

    @Override
    public void doSelectMenuRemoveFromList(String qrmName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isQrmPresent(String name) {
        return getMenuTexts().contains(name);
    }

    @Override
    public void deleteQRM(String qrmName) {
        ISelectFavoriteQRMList selectFavoriteQRMList = doSelectMenuDeleteList();
        String message = selectFavoriteQRMList.doSelect(qrmName);
        if (!message.equals(QRM_DELETED_MESSAGE)) {
            throw new QRMException(message);
        }
    }

    @Override
    public ISelectFavoriteQRMList doSelectMenuDeleteList() {
        doClickMenu(MENU_DELETE_LIST);

        SelectFavoriteQRMList selectFavoriteQRMList = new SelectFavoriteQRMList(getDriver());
        selectFavoriteQRMList.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return selectFavoriteQRMList;
    }

    @Override
    public void doSelectQRM(String qrmName) {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getMenuTexts() {
        showPopupMenu();
        List<String> menu = new ArrayList<>();
        for (WebElement element : webElements.favQRMsMenu.findElements(By.tagName("a"))) {
            menu.add(element.getText());
        }
        hidePopupMenu();
        return menu;
    }


    @Override
    public String getLabel() {
        return webElements.label.getText();
    }


    private void showPopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.favQRMHoverIcon)
                .build();
        hoverAction.perform();
    }


    private void hidePopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.label)
                .build();
        hoverAction.perform();
    }

    private void doClickMenu(String menuText) {
        //first check that menu exists
        if (!getMenuTexts().contains(menuText)) {
            throw new QRMMenuNotAvailableException(menuText);
        }

        showPopupMenu();
        WebElement element = webElements.favQRMsMenu.findElement(By.linkText(menuText));
       SeleniumUtils.click( element,getDriver());
    }

    private static final class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "d2Form:favQRMs_body")
        WebElement label;

        @FindBy(xpath = ".//*[@id='d2Form:favQRMs_header']//*[@class='d2-tglPanel-mark_1']")
        WebElement favQRMHoverIcon;

        @FindBy(id = "favQRMs_menu")
        WebElement favQRMsMenu;
    }
}
