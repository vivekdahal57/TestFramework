package com.vh.mi.mxautomation.test.pages.analytics.individuals;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.vh.mi.mxautomation.impl.pages.analytics.individuals.Individual301;
import com.vh.mi.mxautomation.test.base.MxBaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 3/26/2018.
 */
public class Individual301Test extends MxBaseTest {

    Individual301 individual301;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        individual301 = new Individual301(webDriver);
    }

    @Test
    public void testTest(){
        assertThat(individual301.getDisplayedPageTitle()).isEqualTo("Intelligence Solutions - Individuals");
        individual301.filterAge();
    }

    @Test
    public void testapp() {
        assertThat(individual301.getDisplayedPageTitle()).isEqualTo("Intelligence Solutions - Individuals");
        individual301.testApp();

    }

    @Test
    public void dragAndDropTest() throws InterruptedException {
        assertThat(individual301.getDisplayedPageTitle()).isEqualTo("Intelligence Solutions - Individuals");
        individual301.dragAndDrop();
        System.out.println("Test");
    }

    @Test
    public void customizationTest() {
        assertThat(individual301.getDisplayedPageTitle()).isEqualTo("Intelligence Solutions - Individuals");
       // WaitUtils.waitForSeconds(5);
        individual301.customization("All Field Groups" , "Current");

        System.out.println("Test");
    }

    @Test
    public void saveCohort(){
        new NgWebDriver((JavascriptExecutor) getWebDriver()).waitForAngularRequestsToFinish();


    }
}
