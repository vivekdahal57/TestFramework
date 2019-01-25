package com.vh.mi.automation.impl.comp.dataGrid.drill;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.comp.dataGrid.drill.IDataGridDrillMenu;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by i80448 on 12/9/2014.
 */
public class DataGridDrillMenu extends AbstractWebComponent implements IDataGridDrillMenu  {

    @FindBy(id = "dropmenudiv")
    private WebElement compRootElm;

    public DataGridDrillMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public ImmutableList<String> getDrillOptions() {
        // drill menu
        List<WebElement> menus = getDrillOptionsElms();

        List<String> transform = Lists.transform(menus, new Function<WebElement, String>() {
            @Override
            public String apply(WebElement input) {
                return input.getAttribute("innerHTML").trim();
            }
        });

        return ImmutableList.copyOf(transform);
    }

    public ImmutableList<String> getDrillOptionsBetween(String from, String to){
        //drill menu
        List<WebElement> menus = getDrillOptionElms(from, to);
        List<String> transform = Lists.transform(menus, new Function<WebElement, String>() {
            @Override
            public String apply(WebElement input) {

                return input.getAttribute("innerHTML");
            }
        });

        return ImmutableList.copyOf(transform);

    }

    @Override
    public ImmutableList<String> getDrillOptionAfter(String from) {
        List<WebElement> menus = getDrillOptionElmsAfter(from);
        List<String> transform = Lists.transform(menus, new Function<WebElement, String>(){
            @Override
             public String apply(WebElement input){
                 return input.getAttribute("innerHTML");

            }
        });
        return ImmutableList.copyOf(transform);
    }

    @Override
    public void doDrillBy(String option) {
        SeleniumUtils.click(getDrillOptionElm(option), getDriver());

    }

    private WebElement getDrillOptionElm(String option) {
        List<WebElement> options = getDrillOptionsElms();
        for (WebElement elm : options) {
            if (option.equals(elm.getAttribute("innerHTML").trim())) {
                return elm;
            }
        }

        throw new AutomationException("Drill by link not available for option = " + option + ". Make sure that page has this feature or something wrong in implementation.");
    }

    /**
     * Drill menu option link elements.
     *
     * @return
     */
    private List<WebElement> getDrillOptionsElms() {
        Preconditions.checkState(compRootElm != null);

        // drill menu options
        List<WebElement> menus = compRootElm.findElements(By.tagName("a"));
        return menus;
    }

    private List<WebElement> getDrillOptionElms(String from, String to){
        String xpath = "//*[@id=\"dropmenudiv\"]//*[text()='${from}']//following::a [count(.| //*[text()='${to}']//preceding::a) = count( //*  [text()='${to}']//preceding::a)]";
        List<WebElement> menus = getDriver().findElements(By.xpath(xpath.replace("${from}",from).replace("${to}",to)));
        return menus;
    }

    private List<WebElement> getDrillOptionElmsAfter(String from){
       String xpath= "//*[@id=\"dropmenudiv\"]//*[text()= '${from}']//following::a[contains(@class, 'd2-menu-item')]";
       List<WebElement> menus = getDriver().findElements(By.xpath(xpath.replace("${from}",from)));
       return menus;
    }


    @Override
    public boolean isFullyLoaded() {
        return false;
    }
}
