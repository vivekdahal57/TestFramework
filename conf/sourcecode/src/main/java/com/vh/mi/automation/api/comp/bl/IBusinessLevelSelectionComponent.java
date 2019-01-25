package com.vh.mi.automation.api.comp.bl;

import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.exceptions.comp.bl.BusinessLevelNotAvailableException;
import com.vh.mi.automation.api.exceptions.comp.bl.BusinessLevelOptionNotAvailable;

import java.util.List;
import java.util.Map;

/**
 * API for BusinessLevel Selection Component
 * @author nimanandhar
 */
public interface IBusinessLevelSelectionComponent {

    /**
     * Closes the selection component for specified businessLevel
     * Clicks on "Close" Button
     * <p>
     * Note: Causes the component to transition to DEAD state
     */
    public void doClose();

    /**
     * Reset the selection for current business level and closes
     * the component.Clicks on "Reset Selection" Button
     * <p>
     * Note: Causes the component to transition to DEAD state
     */
    public void doResetSelection();


    /**
     * Applies the current selections and closes the component
     * Clicks on "Apply" Button.
     * <p>
     */
    public void doApply();

    /**
     * Returns the title displayed in the Selection Component
     * eg Multiple Selection for Plan Type
     *
     * @return the title displayed in the component
     */
    public String getDisplayedTitle();



    public IPaginationComponent getPaginationComponent();


    /**
     * Get a ReadOnlyContainer that represents the top component
     * in the BusinessLevelSelectionComponent
     * @return
     */
    public IReadOnlyContainer getReadOnlyContainer();


    /**
     * Represents the top portion of a Business Level Selection
     * Component that displays the business levels and the selected
     * values for the business levels. Note the name readOnlyContainer
     * maps to the table id readOnlyContainer We have named this
     * IReadOnlyContainer for lack of a better name.Please rename this
     * interface if you can find a better name
     */
    public interface IReadOnlyContainer {
        /**
         * Changes selection type to specified business level
         * The current business level does not affect the
         * method.ie if the current business level selected is
         * 1 and we invoke doChangeSelectionTo(BL.Level1) no
         * exception is thrown
         *
         * @param level the Business Level to change the selection to
         * @throws BusinessLevelNotAvailableException
         */
        public void doChangeSelectionTo(BL level);


        /**
         * Get the map of available business levels, along
         * with the label text of the business levels
         *
         * @return a map of Business Level and Labels
         */
        public Map<BL, String> getLabelTexts();


        /**
         * Get a map of all available Business Levels for the front along
         * with the selection text.
         *
         * @return a map of BL and the selected text
         */
        public Map<BL, String> getCurrentSelections();
    }


    /**
     * Get SelectionGridContainer of the SelectionComponent. The
     * SelectionGridContainer can be used to check/uncheck levels
     * @return
     */
    public ISelectionGridContainer getSelectionGridContainer();

    /**
     * Represents the bottom portion of the Business Level Selection Component
     * which display checkboxes to select business level items
     */
    public interface ISelectionGridContainer {


        /**
         * Get the selection options listed in the current page
         *
         * @return a list of selection options
         */
        public List<String> getSelectionOptionsOnCurrentPage();


        public List<String> getCheckedOptionsOnCurrentPage();



        /**
         * Checks the options provided
         *
         * @param itemsToCheck a list of options to check
         * @throws BusinessLevelOptionNotAvailable if any option is not available in current page
         */
        public void doCheckOptions(List<String> itemsToCheck);


        void doCheckOption(String option);

        /**
         * Clicks on Select All Menu
         * Does not close the Selection Component
         */

        void doCheckRadioOption(String option);

        public void doChooseMenuSelectAll();


        /**
         * Clicks on Menu Select All On Page
         * Does not close the Selection Component
         */
        public void doChooseMenuSelectAllOnPage();


        /**
         * Sets the position for first radio button option

         */

        public boolean isRadioButtonComponentVisible();


        /**
         * Checks if radio button option is selected or not

         */

        public boolean isRadioButtonComponentSelected();

        /**
         * Clicks on Menu Unselect All
         * Does not close the Selection Component
         */
        public void doChooseMenuUnselectAll();


        /**
         * Clicks on the Select and Apply Drill Menu for the provided selection
         * The drill menu is available to the right of every option. This will
         * choose only the given option for the current business level and closes
         * the selection component.
         * Note that this will cause the Selection Component to transition to DEAD state
         *
         * @param selection the selection text for which to apply "Select and Apply"
         * @throws BusinessLevelOptionNotAvailable if the selection option is not available in current page
         */
        public void doChooseMenuSelectAndApply(String selection);



    }



}
