package com.vh.mi.automation.api.comp.qrm;

/**
 * Represents the component that enables naming
 * and saving QRM List
 *
 * @author nimanandhar
 */
public interface ISaveFavoriteQRMList {

    /**
     *
     * @param name
     * @return the message displayed in popup
     */
    String doSaveQrm(String name);

    /**
     * Closes this component
     */
    void doClose();
}
