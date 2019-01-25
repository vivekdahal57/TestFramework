package com.vh.mi.automation.api.reportManager.reportWizard;

import com.vh.mi.automation.api.features.IAmWebComponent;

public interface IReportsTab extends IAmWebComponent {
   void selectAllReportChapter();
   void reportChapterSaveAndContinue();
   void selectReportSet(ReportSet reportSet);

    enum ReportSet {
      GRP_JDK("400"),
      GOLD_REPORT("3"),
      MI("123"),;

      private String value;

      ReportSet(String value){
         this.value = value;
      }

      public String getValue() {
         return value;
      }
   }
}
