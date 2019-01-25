package com.vh.mi.automation.impl.pages.analytics.benifitAdvisor;

import com.vh.mi.automation.api.comp.IReportingBy;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.pages.analytics.benifitAdvisor.IBenefitAdvisor;
import com.vh.mi.automation.impl.comp.ReportingBy;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author i80448
 */
public class BenefitAdvisor extends AbstractLandingPage implements IBenefitAdvisor {
    public static final String MODULE_ID = "66";
    private IBusinessLevelsComponent businessLevelsComponent;
    private IReportingBy reportingBy;

    public BenefitAdvisor(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevelsComponent = new BusinessLevelsComponent(getDriver());
        reportingBy = PageFactory.initElements(getDriver(), ReportingBy.class);
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevelsComponent;
    }

    @Override
    public IReportingBy getReportingBy() {
        return reportingBy;
    }

}
