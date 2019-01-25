package com.vh.mi.automation.api.comp;

import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.impl.pages.analytics.individuals.IIndvDynamicMemberList;

/**
 * Created by i82716 on 6/22/2017.
 */
public interface IMemberList extends IAmWebComponent {

    public void createStaticMemberList(String memListName);
    public void resetSelection();
    public boolean checkIfMemberListExists(String memberListName);
    public void deleteMyMemberListWithName(String memberListName);
    public String getOperationCompleteStatusMessage();
    public String getExpectedMessageForMemberListDeletion();
    public void goToSavedMemberList(String memberListName);
    public IIndvDynamicMemberList clickCreateDynamicMemberList();
    public String getOperationSuccessfulMessage();
}
