package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.Rx;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.Rx.IRxCode;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10316 on 7/7/2017.
 */
public class RxCode extends QueryBuilderToolBar implements IRxCode {
    WebElements webElements;
    public static final String pageTitle="(Stratifier) Query Builder - RX";
    public static final String ROW_HOLDER="${row}";

    public RxCode(WebDriver driver){
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public String getPageLink() {
        return "Rx";
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }


    @Override
    public void includeDrugs(int topN) {
        String xpath="//*[@id=\"d2Form:simpleGridAll:tb\"]/tr[${row}]/td[4]";

        for(int i=1; i<topN; i++){
            WebElement webElement= SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(ROW_HOLDER,String.valueOf(i)));
            webElement.click();
        }

    }

    private static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);

        }
        @FindBy(xpath = "//div[@class='dr-pnl-h rich-panel-header' and text()='Query']//following::div[1]")
        private WebElement hoverQuery;

        @FindBy(xpath="//div[@id='query_menu']//a[text()='Refine Logic']")
        private WebElement btnRefineLogic;

    }
}
