package com.vh.mi.automation.impl.comp.favqm;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i82298 on 1/17/2017.
 */
public class FavQmUtils {

    public static String getSelectedQRM(IDataGridRow selectedRow) {
        List<String> selectedMeasures = new ArrayList<>();
        String qmNo = selectedRow.getValue(3);
        return qmNo;
    }


    public static String  getSelectedQualityMeasures(IDataGridRow selectedRow){
        String qualityMeasures = selectedRow.getValue(2);
        return qualityMeasures;
    }

    public static String getSelectedHedis(IDataGridRow selectedRow) {
        StringBuilder sb = new StringBuilder();
        String measureAbbr = selectedRow.getValue(3);
        String number = selectedRow.getValue(2);
        sb.append(measureAbbr).append(" (").append(number).append(")");
        return sb.toString();
    }


}
