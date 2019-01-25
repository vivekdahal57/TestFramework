package com.vh.mi.automation.api.comp;

import com.vh.mi.automation.api.features.IAmWebComponent;

public interface IAnalysisCondition  extends IAmWebComponent {

    void clickRun();
    void selectAnalysisCondition(IAnalysisCondition.Type type, IAnalysisCondition.Level level, String value);

     enum Type {
        DiagGroup("DiagCode"),
        ProcGroup("ProcCode"),
        RX_CLASS("RxCode"),
        Drugs("DrugCode");

        String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

     enum Level {
        First("first"),
        Second("second"),
        Third("third");

        String level;

        Level(String level) {
            this.level = level;
        }

        public String getLevel() {
            return this.level;
        }
    }
}
