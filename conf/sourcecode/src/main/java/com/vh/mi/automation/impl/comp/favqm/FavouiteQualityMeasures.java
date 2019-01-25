package com.vh.mi.automation.impl.comp.favqm;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 Created by i82298 on 11/30/2016. */
public class FavouiteQualityMeasures extends AbstractWebComponent {
    private final WebElements webElements;

    public FavouiteQualityMeasures(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {

        return false;
    }

    private void openMenu() {

        if (!webElements.favQmMenuBody.isDisplayed()) {
            SeleniumUtils.hoverOnElement(webElements.favQmMenu, getDriver());
        }
    }


    public FavouriteQualityMeasuresPopUp openCreateListComponent() {
        openMenu();
        webElements.createListElement.click();
        return new FavouriteQualityMeasuresPopUp(getDriver());
    }

    public void applyList(String listName, ListScope listScope) {
        openMenu();
        List<WebElement> selectedLists = null;
        if (listScope == ListScope.PRIVATE) {
            selectedLists = webElements.privateListPanel
                    .findElements(By.xpath("./tr/td[1]/a"));
        } else {
            selectedLists = webElements.publicListPanel
                    .findElements(By.xpath("./tr/td[1]/a"));
        }
        for (WebElement ele : selectedLists) {
            if (listName.equals(ele.getText())) {
                ele.click();
                break;
            }
        }
        new LoadingPopup(getDriver(), "_qmSelectLoading").waitTillDisappears();

    }

    public List<String> getAvailableListName() {
        List<String> publicList = getAvailebleListName(ListScope.PUBLIC);
        List<String> privateList = getAvailebleListName(ListScope.PRIVATE);
        List<String> totalList = new ArrayList<>();
        totalList.addAll(privateList);
        totalList.addAll(publicList);
        return totalList;
    }

    private List<String> getAvailebleListName(ListScope scope) {
        List<String> availableList = new ArrayList<>();
        List<WebElement> elements = null;

        if (scope == ListScope.PUBLIC) {
            elements = webElements.publicListPanel
                    .findElements(By.xpath("./tr//a"));

        } else if (scope == ListScope.PRIVATE) {
            elements = webElements.privateListPanel
                    .findElements(By.xpath(""));
        }
        for (WebElement ele : elements) {
            availableList.add(ele.getText());
        }
        return availableList;
    }

    public void deleteList(String listName, ListScope listScope) {
        openMenu();
        WebElement selectedList = null;
        StringBuilder sb = new StringBuilder();
        sb.append("//a[@id='");
        sb.append("d2Form:");
        String deleteLinkPostFix = "";
        List<WebElement> selectedLists = null;
        if (listScope == ListScope.PRIVATE) {
            sb.append("private:");
            deleteLinkPostFix = ":lnk1_Delete";
            selectedLists = webElements.privateListPanel
                    .findElements(By.xpath("./tr/td[1]/a"));
        } else {
            sb.append("public:");
            deleteLinkPostFix = ":lnk1_Delete_1";
            selectedLists = webElements.publicListPanel
                    .findElements(By.xpath("./tr/td[1]/a"));
        }
        for (WebElement ele : selectedLists) {
            if (listName.equals(ele.getText())) {
                selectedList = ele;
                break;
            }
        }
        String index = selectedList.getAttribute("id");
        String id = sb.append(getRowNumberFromId(index))
                .append(deleteLinkPostFix).append("']").toString();
        final WebElement delete = selectedList.findElement(By.xpath(id));
        openMenu();
        SeleniumUtils.hoverOnElement(selectedList, getDriver());
        WaitUtils.waitUntil(getDriver(),1000,new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver tab) {
                return delete.isDisplayed();
            }
        });

        delete.click();
        //        SeleniumUtils.click(delete, getDriver());
        if (SeleniumUtils.isAlertPresent(getDriver())) {
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        new LoadingPopup(getDriver()).waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.statusMessage, TimeOuts.TEN_SECONDS);
    }

    public String getDeletionCompleteStatusMessage(){
        String message = webElements.statusMessage.getText();
        WaitUtils.waitUntilDisappear(getDriver(), webElements.statusMessage, TimeOuts.THIRTY_SECONDS );
        return message;
    }

    public void resetList() {
        openMenu();
        webElements.resetList.click();
        WaitUtils.waitForSeconds(5);
        new LoadingPopup(getDriver(), "_qmSelectLoading").waitTillDisappears();
    }

    private String getRowNumberFromId(String id) {

        return id.split(":")[2];
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:favQMList_ListPanel_header\"]/table/tbody/tr/td/div/div[@class=\"d2-tglPanel-mark_1\"]")
        private WebElement favQmMenu;

        @FindBy(id = "favQMList_ListPanel_menu")
        private WebElement favQmMenuBody;

        @FindBy(id = "d2Form:favQMList_ListPanel_CreateListPanel_1")
        private WebElement createListElement;

        @FindBy(id = "d2Form:private:tb")
        private WebElement privateListPanel;

        @FindBy(id = "d2Form:public:tb")
        private WebElement publicListPanel;

        @FindBy(id = "d2Form:favQMList_ListPanel_resetListPanel")
        private WebElement resetList;

        @FindBy(id = "ani_message")
        private WebElement statusMessage;

    }

}
