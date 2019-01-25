package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.comp.qrm.IFavoriteQRM;
import com.vh.mi.automation.api.comp.qrm.ISaveFavoriteQRMList;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRiskDataGrid;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class QualityAndRiskQRMTest extends BaseTest {
    private static final String QRM_NAME = "automation_qrm";
    private QualityAndRisk670 qrmPage;

    @BeforeClass()
    public void setUp() {
        super.setUp();
        qrmPage = navigationPanel.doNavigateTo(QualityAndRisk670.class, defaultWaitTime);
    }


    @Test
    public void testThatFavoriteCanBeCreated() {
        QualityAndRiskDataGrid dataGrid = qrmPage.getDataGrid();
        IFavoriteQRM favoriteQRM = qrmPage.getFavoriteQRM();

        //just in case the qrm is already present delete it first
        if (favoriteQRM.getMenuTexts().contains(QRM_NAME)) {
            logger.warn("The qrm  " + QRM_NAME + " is already present . Attempting to delete it before adding it");
            favoriteQRM.deleteQRM(QRM_NAME);
        }


        int rowsOnFirstPage = dataGrid.getRows().size();
        if (rowsOnFirstPage == 0)
            Assert.fail("Cannot test qrm favorite. No data");

        //select first
        dataGrid.doSelectRow(0);
        //and last row
        if (rowsOnFirstPage > 1)
            dataGrid.doSelectRow(rowsOnFirstPage - 1);


        ISaveFavoriteQRMList saveFavoriteQRMList = favoriteQRM.doSelectMenuCreateList();
        saveFavoriteQRMList.doSaveQrm(QRM_NAME);

        assertThat(favoriteQRM.getMenuTexts().contains(QRM_NAME));

        favoriteQRM.deleteQRM(QRM_NAME);

        assertThat(favoriteQRM.getMenuTexts()).excludes(QRM_NAME);
    }
}
