package com.vh.mi.automation.impl.comp.favqm;

/**
 * Created by i82298 on 1/17/2017.
 */
public enum QualityMeasuresType {
    QRM,HEDIS;

    public static QualityMeasuresType getName(String selectedType) {
        QualityMeasuresType type;
        switch (selectedType) {
        case "Quality & Risk":
            type = QRM;
            break;
        case "HEDIS - Claims Based":
            type = HEDIS;
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown Quality Measures Type [" + selectedType + "]");
        }
        return type;
    }


    public static String getQmTypeText(QualityMeasuresType type) {
        String typeText;
        switch (type) {
        case  QRM:
            typeText = "Quality & Risk" ;
            break;
        case HEDIS :
            typeText = "HEDIS - Claims Based";
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown Quality Measures Type [" + type + "]");
        }
        return typeText;
    }
}
