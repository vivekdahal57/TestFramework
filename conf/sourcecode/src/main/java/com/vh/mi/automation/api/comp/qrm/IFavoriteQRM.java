package com.vh.mi.automation.api.comp.qrm;

import java.util.List;

/**
 * @author nimanandhar
 */
public interface IFavoriteQRM {

    /**
     * Clicks on the "Create List" Menu
     *
     * @return An instance of ISaveFavoriteQRMList that can be used to enter the name of qrm
     * @throws QRMMenuNotAvailableException if the menu is not available
     * @throws QRMNotSelectedException      if not QRM has been selected prior to clicking on Create List
     */
    public ISaveFavoriteQRMList doSelectMenuCreateList();


    public void doSelectMenuAddToList(String qrmName);


    public void doSelectMenuRemoveFromList(String qrmName);


    /**
     * Deletes the favorite qrm
     *
     * @param qrmName
     * @throws
     */
    public void deleteQRM(String qrmName);


    public ISelectFavoriteQRMList doSelectMenuDeleteList();

    /**
     * Selects the given qrm in Favorite QRMs
     *
     * @param qrmName throws NoSuchQRM if the QRM is not available
     */
    public void doSelectQRM(String qrmName);


    /**
     * Checks if a favorite QRM with given name is present
     *
     * @param name
     * @return
     */
    public boolean isQrmPresent(String name);


    /*
    Get All Menu Displayed
     */
    public List<String> getMenuTexts();


    /**
     * gets the label displayed
     */
    public String getLabel();

}
