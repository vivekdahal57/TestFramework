package com.vh.mi.automation.test.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.IDiseaseRegistryCD016;
import com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.DiseaseRegistryCD016;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/8/18.
 */
@Test(groups = {"Report-Download" })
public class DiseaseRegistryCD016SendToExcel extends BaseTest {
    private IDiseaseRegistryCD016 diseaseRegistryCD016;
    private static final String FILENAME = "DR016*.xlsx";

    @BeforeClass
    public void setUp(){
        super.setUp();
        diseaseRegistryCD016 = navigationPanel.doNavigateTo(DiseaseRegistryCD016.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(diseaseRegistryCD016.getPageTitle());
    }

    @Test
    public void checkSendToExcelTest() throws IOException{
      diseaseRegistryCD016.getPreferencesBar().sendToExcel();
      assertThat(diseaseRegistryCD016.sendToExcelAndValidate(FILENAME, context)).isTrue();
    }
}
