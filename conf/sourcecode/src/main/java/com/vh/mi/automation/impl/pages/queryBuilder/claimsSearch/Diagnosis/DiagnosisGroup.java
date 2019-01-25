package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.Diagnosis;

import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.Diagnosis.IDiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/3/2017.
 */
public class DiagnosisGroup extends QueryBuilderToolBar implements IDiagnosisGroup {

    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";
    WebElements webElements;

    public DiagnosisGroup(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }


    @Override
    public void includeTopNGroups(int topN) {
        for (int i = 1; i <= topN; i++){
            selectNthCheckbox(String.valueOf(i));
        }
    }


    private void selectNthCheckbox(String nthCheckbox){
        String xpath = "(//*[@id='d2Form:simpleGrid:tb']//td/input[@type='checkbox'])[${nthCheckbox}]";
        WebElement gruopIncludeCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(NTH_CHECKBOX_PLACEHOLDER, nthCheckbox));
        SeleniumUtils.click(gruopIncludeCheckbox);
    }

    @Override
    public String getPageLink() {
        return "Diagnosis";
    }


    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }
    }
}
