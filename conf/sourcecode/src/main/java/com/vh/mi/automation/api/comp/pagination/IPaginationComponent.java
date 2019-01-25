package com.vh.mi.automation.api.comp.pagination;

import com.google.common.collect.Range;

import java.util.Set;

/**
 * Interface for the pagination component
 * This interface is meant purely to drive the corresponding component in the browser.
 * Implementers of this interface will not attempt to perform any logic to complete
 * the methods. For eg selectPage(13) will fail if the page is not displayed as selectable
 * in the pagination component,even if this could be performed by selecting nextPage repeatedly
 * until the page number is visible.When such cases arise the implementation will and should
 * throw UnsupportedException.
 * <p/>
 * In a sense, instances of this class are like a dumb person.When we tell it to do a certain action
 * it will do it if possible given the current state of the component, but will not attempt to use his
 * brain
 * <p/>
 * The basic idea for this design is to minimize the number of interface in these components, since
 * there will be arbitrarily many operations that we want to display intelligent behavior. All such
 * operations will be implemented through a facade
 * <p/>
 * Please note that this design is experimental, and may change if it turns out to be cumbersome
 *
 * @author nimanandhar
 */
public interface IPaginationComponent {

    /**
     * @return the current record range as displayed in pagination control
     * @throws UnsupportedOperationException if range is not displayed in the control
     *                                       because no records were found
     */
    public Range<Integer> getCurrentRecordRange();


    /**
     * @return totalRecords, 0 if no Records Found
     */
    public int getTotalRecords();


    /**
     * Get the current Page number in the pagination Control
     *
     * @throws java.lang.UnsupportedOperationException page number is not displayed
     */
    public int currentPage();

    /**
     * Gets the set of pages currently displayed in the pagination Control
     *
     * @throws UnsupportedOperationException if no Pages are currently displayed
     */
    public Set<Integer> getCurrentPageSet();


    /**
     * @return true if nextPage control is available and clickable, false otherwise
     */
    public boolean hasNextPage();


    /**
     * click on nextPage control and wait till the operation is complete
     *
     * @throws UnsupportedOperationException if control is inactive or unavailable
     */
    public void nextPage();


    /**
     * @return true if previousPage control is available and clickable, false otherwise
     */
    public boolean hasPreviousPage();


    /**
     * click on previousPage control and wait till the operation is complete
     *
     * @throws UnsupportedOperationException if control is inactive or unavailable
     */
    public void previousPage();


    /**
     * @return true if nextPageSet control is available and clickable, false otherwise
     */
    public boolean hasNextPageSet();


    /**
     * click on nextPageSet control and wait till the operation is complete
     *
     * @throws UnsupportedOperationException if control is inactive or unavailable
     */
    public void nextPageSet();


    /**
     * click on previousPageSet control and waits till the operation is complete
     *
     * @throws UnsupportedOperationException if control is inactive or unavailable
     */
    public void previousPageSet();


    /**
     * @return true iff previousPageSet control is available and clickable
     */
    public boolean hasPreviousPageSet();


    /**
     * @return true if pagination controls (next,previous etc) are displayed(maybe disabled)
     */
    public boolean hasPaginationControls();

    /**
     * Selects the page number
     *
     * @param pageNumber
     * @throws UnsupportedOperationException if the given page number is not displayed in the pagination control
     *                                       (even if the page number is valid)
     */
    public void selectPage(int pageNumber);
}
