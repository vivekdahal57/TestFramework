package com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.drill.QRMDetail690;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nimanandhar
 */
public class QualityAndRiskDataGrid extends AbstractDataGrid {

    private static Logger logger = LoggerFactory.getLogger(QualityAndRiskDataGrid.class);

    public QualityAndRiskDataGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return Columns.fromId(id);
    }

    @Override
    public IDataGridColumn getCurrentSortedColumn() {
        try {
            return super.getCurrentSortedColumn();
        } catch (WebDriverException ex) {
            return null;
        }
    }

    /*
    Check the particular row. Row numbers start at 0
     */
    public void doSelectRow(int row) {
        String xpath = "//tbody[@id='d2Form:simpleGrid:tb']/tr[" + (row + 1) + "]/td[1]/input[@type='checkbox']";
        WebElement checkBox = getDriver().findElement(By.xpath(xpath));
        checkBox.click();
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Detail":
                return QRMDetail690.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }


    public static enum Columns implements IDataGridColumn {
        QUALITYQUESTIONID("QUALITYQUESTIONID", "#", DataType.STRING),
        QRMTYPE("QRMTYPE", "Type", DataType.STRING),
        DISCATEGORY("DISCATEGORY", "Disease Category", DataType.STRING),
        SECCATEGORY("SECCATEGORY", "Secondary Category", DataType.STRING),
        ISSUENAME("ISSUENAME", "Condition", DataType.STRING),
        QUESTION("QUESTION", "Description", DataType.STRING),

        PERCENTCOMPLIANT("PERCENTCOMPLIANT", "Actual", DataType.PERCENTAGE),
        PERCENTCOMPLIANTNORM("PERCENTCOMPLIANTNORM", "Norm", DataType.PERCENTAGE),
        PANELWITHISSUE("PANELWITHISSUE", "With Condition", DataType.INTEGER),
        PANELMEETINGCRITERIA("PANELMEETINGCRITERIA", "With Gap/Risk", DataType.INTEGER),
        PANELNOTMEETINGCRITERIA("PANELNOTMEETINGCRITERIA", "Without Gap/Risk", DataType.INTEGER),

        COMPLIANCE("COMPLIANCE", "% of Individual with Gap/Risk", DataType.GRAPH);

        private String id;
        private DataType dataType;
        private String label;

        Columns(String id, String label, DataType dataType) {
            this.id = id;
            this.label = label;
            this.dataType = dataType;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public DataType getDataType() {
            return dataType;
        }

        @Override
        public String getLabel() {
            return label;
        }

        public static IDataGridColumn fromId(String id) {
            for (Columns c : Columns.values()) {
                if (c.getId().equals(id)) return c;
            }

            logger.warn("Mapping not available for ID - " + id);

            return null;
        }

        public static IDataGridColumn fromLabel(String label) {
            for (Columns c : Columns.values()) {
                if (c.getLabel().equals(label)) return c;
            }

            logger.warn("Mapping not available for label - " + label);

            return null;
        }
    }
}
