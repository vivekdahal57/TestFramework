package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.placeOfService;

import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.PlaceOfService.IPlaceOfService;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.procedure.ProcedureGroup;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/18/17.
 */
public class PlaceOfService extends QueryBuilderToolBar  implements IPlaceOfService {
    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";
    WebElements webElements;

    public PlaceOfService(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());

    }
    @Override
    public String getPageLink() {
        return "Place of Service";
    }

    @Override
    public void includeTopNGroups(int topN) {
        for(int i = 1; i <= topN; i++){
            selectNthCheckbox(String.valueOf(i));
        }
    }

    private void selectNthCheckbox(String nthCheckbox){
        String xpath = "(//*[@id='d2Form:simpleGrid:tb']//td/input[@type='checkbox'])[${nthCheckbox}]";
        WebElement groupIncludeCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(NTH_CHECKBOX_PLACEHOLDER, nthCheckbox));
        SeleniumUtils.click(groupIncludeCheckbox);
    }


    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }
    }
}
