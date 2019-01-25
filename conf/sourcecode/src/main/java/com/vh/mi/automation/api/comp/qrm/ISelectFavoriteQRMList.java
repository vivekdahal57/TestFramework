package com.vh.mi.automation.api.comp.qrm;

import java.util.List;

/**
 * @author nimanandhar
 */
public interface ISelectFavoriteQRMList {
    /*
     * @return the list of QRM listed in this compoenent
     */
    List<String> getQRMList();

    /**
     * Checks the given qrm
     * @param qrm
     * @return
     */
    String doSelect(String qrm);

    /**
     *
     * @param qrms
     * @return The Message displayed after selecting a list
     */
    String  doSelect(List<String> qrms);

    void doClose();
}
