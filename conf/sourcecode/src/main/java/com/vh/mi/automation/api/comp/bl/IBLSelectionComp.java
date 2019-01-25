package com.vh.mi.automation.api.comp.bl;

import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.List;
import java.util.Map;

/**
 * Represents options selection component for a given level.
 * <p/>
 *
 * Created by i80448 on 9/9/2014.
 */
@Deprecated
public interface IBLSelectionComp extends IAmWebComponent {

    /* ## RO Container ## */

    /**
     * Changes the selection list displayed in n-key grid for given business level.
     *
     * @param level
     */
    public void doChangeSelectionListTo(BL level);

    /* ## RO Container ENDS ## */

    /**
     * Close selection component.
     * <br>
     * <b>NOTE: Causes component to transition into DEAD state. </b>
     */
    public void doClose();

    /**
     * Applies current selections.
     * <br>
     * <b>NOTE: Causes component to transition into DEAD state. </b>
     */
    public void doApplySelections();

    /**
     * Resets current selection.
     * <br>
     * <b>NOTE: Causes component to transition into DEAD state. </b>
     */
    public void doResetSelections();

    /**
     * Selects available options in all pages.
     */
    public void doSelectAll();

    /**
     * Selects available options in current page.
     */
    public void doSelectAllOnPage();

    /**
     * Unselects all selected options on all pages.
     */
    public void doUnselectAll();

    /**
     * Gets selection options available in current page for current level.
     *
     * @return
     */
    public List<String> getSelectionOptionsOnCurrentPage();

    /**
     * Selects and apply given key string if available in current page.
     * <br>
     * <b>NOTE: Causes component to transition into DEAD state. </b>
     *
     * @param key
     */
    public void doSelectAndApplyFromCurrentPage(String key);

    /**
     * Selects a row with given key value if available in current page.
     *
     * @param key
     */
    public void doSelectFromCurrentPage(String key);

    /**
     * Gets current n level for current selection options.
     *
     * @return
     */
    public BL getCurrentLevel();

    public Map<BL, String> getKeyLabels();

}
