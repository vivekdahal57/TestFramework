package com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.features.IHaveMemberList;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i82716 on 6/29/2017.
 */
public interface IIndividualDrillPage extends IDrillPage,IHaveMemberList {
    public void createDymanicMemberList(int topN,String memListName);
    public boolean deleteMemberList(String memListName);
}
