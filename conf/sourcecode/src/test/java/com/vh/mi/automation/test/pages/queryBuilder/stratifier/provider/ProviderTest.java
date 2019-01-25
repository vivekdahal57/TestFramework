package com.vh.mi.automation.test.pages.queryBuilder.stratifier.provider;

import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.provider.Provider;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by i20345 on 1/23/2017.
 */


public class ProviderTest extends BaseTest{

    Provider provider;

    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod (alwaysRun = true)
    public void beforeEach(){
        QueryBuilder queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, 30);
        provider = queryBuilder.selectCriteriaComponent(Provider.class);
        provider.selectProviderTab();
    }

    @Test
    public  void test()
    {

    }
}
