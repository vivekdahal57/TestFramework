package com.vh.mi.automation.api.comp.bl;

import com.vh.mi.automation.api.exceptions.comp.bl.BusinessLevelNotAvailableException;
import com.vh.mi.automation.api.exceptions.comp.bl.ViewSelectedNotAvailableException;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.pages.common.IMIPage;

import java.util.Collection;
import java.util.List;

/**
 * Represents Business Levels Component Available in Page
 * A Business Levels Component can be viewed as a collection
 * of Business Level Components.
 * <p/>
 *
 * @author nimanandhar
 */
public interface IBusinessLevelsComponent extends IAmWebComponent {

    /**
     * Get the max number of levels present
     * This can be removed if the method isLevelAvailable suffices
     *
     * @return the max levels 1 to 6 inclusive
     */
    public int getMaxLevels();


    /**
     * Query whether a business level is available in AUT
     *
     * @param level
     * @return true if the level is available in front, false otherwise
     */
    public boolean isLevelAvailable(BL level);


    /**
     * Resets current business levels' selections to default state.
     */
    public void doResetSelections();


    /**
     * Get the BusinessLevel Component for the supplied level, which
     * can then be queried for labels, selected items etc
     *
     * @param level the level for which to get the component
     * @return a business level component for the supplied level
     * @throws BusinessLevelNotAvailableException if the given level is not available
     */
    public IBusinessLevel getBusinessLevelComponent(BL level);


    /**
     * Get all the BusinessLevel Component available in the BusinessLevels Component
     *
     * @return a collection of BusinessLevel Components
     */
    public Collection<IBusinessLevel> getBusinessLevels();


    /**
     * Represent a Single Business Level Component
     * eg
     * <pre>
     * -----------------------------------
     * Plan Type  [All          ]  >
     * -----------------------------------
     * </pre>
     */
    public interface IBusinessLevel {

        /**
         * @return the label for this business level eg Plan Type
         */
        public String getLabel();


        /**
         * @return The selected text for this business level eg All
         */
        public String getSelected();


        /**
         * Opens a selection component for this business level
         *
         * @return the selection component instance
         */
        public IBusinessLevelSelectionComponent doOpenSelection();


        /**
         * @return true if the View Selected Icon is visible
         */
        public boolean isViewSelectedComponentVisible();


        /**
         * Clicks on the View Selected yellow icon to open the Selection
         * List for the Business Level. Note this is only available if
         * multiple items are selected for the business level
         *
         * @return an instance of IViewSelected component
         * @throws ViewSelectedNotAvailableException if the view selected icon
         *                                           is not available, for instance if only one item is selected for BL
         */
        public IViewSelected doOpenViewSelected();


        /**
         * When multiple items are selected for a business level, a small
         * yellow icon with green top appears to the right of the Business Level
         * Clicking on it opens the View Selected Component which lists all the
         * values selected for the business level
         */
        public interface IViewSelected {

            public String getTitle();

            public int getNumberOfSelectedItems();

            public List<String> getSelectedItems();

            public void doClose();
        }

    }

}
