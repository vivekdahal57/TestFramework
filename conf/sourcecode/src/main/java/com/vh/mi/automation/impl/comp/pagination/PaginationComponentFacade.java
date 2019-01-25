package com.vh.mi.automation.impl.comp.pagination;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;

/**
 * Facade that wraps {@link IPaginationComponent} providing some intelligence
 * and  useful behaviors on top of PaginationComponent.
 * <p/>
 * It is intended that tests use this facade instead of PaginationComponent in order to
 * simplify the tests.
 *
 * @author nimanandhar
 */
public class PaginationComponentFacade {
    private final IPaginationComponent paginationComponent;

    public PaginationComponentFacade(IPaginationComponent paginationComponent) {
        Preconditions.checkNotNull(paginationComponent);
        this.paginationComponent = paginationComponent;
    }

    /**
     * Get access to the underlying pagination component if you want to use this directly
     *
     * @return the underlying pagination component
     */
    public IPaginationComponent paginationComponent() {
        return paginationComponent;
    }


    public void gotoFirstPage() {
        if (!paginationComponent.hasPaginationControls())
            return;
        gotoPage(1);
    }

    public void gotoLastPage() {
        if (!paginationComponent.hasPaginationControls())
            return;
        gotoPage(getTotalPages());
    }


    /**
     * Select the given page in the pagination control
     * If the page number is valid, this will navigate forward/backward
     * as required until the page can be selected.
     *
     * @param pageNumber any page number between 1 and maxPage inclusive
     * @throws java.lang.UnsupportedOperationException if the pagination controls are not visible
     * @throws java.lang.IllegalArgumentException      if page number is 0 or greater than maximum page number
     */
    public void gotoPage(int pageNumber) {
        Preconditions.checkArgument(pageNumber > 0, "Page number should be greater than 0");
        if (!paginationComponent.hasPaginationControls())
            throw new UnsupportedOperationException("Cannot select Page because pagination controls are not available");

        Preconditions.checkArgument(pageNumber <= getTotalPages(), "Cannot select Page " + pageNumber + "because it is greater than total Pages");

        if (paginationComponent.currentPage() == pageNumber)
            return;

        if (paginationComponent.getCurrentPageSet().contains(pageNumber))
            paginationComponent.selectPage(pageNumber);

        //click on next/previous until the pageNumber is visible
        boolean selectNext = paginationComponent.currentPage() < pageNumber;
        while (!paginationComponent.getCurrentPageSet().contains(pageNumber)) {
            if (selectNext)
                paginationComponent.nextPageSet();
            else
                paginationComponent.previousPageSet();
        }
        paginationComponent.selectPage(pageNumber);

    }

    /**
     * @return true if this is the last page.
     */
    public boolean onLastPage() {
        return !paginationComponent.hasNextPage();
    }

    /**
     * @return
     */
    public int getTotalPages() {
        int totalRecords = paginationComponent.getTotalRecords();
        if (totalRecords == 0) return 0;

        //if there is single page then no pagination controls are available
        if (totalRecords > 0 && !paginationComponent.hasPaginationControls())
            return 1;

        //if this is last page, we cannot get accurate records per page
        //so go to previous page and get recordsPerPage
        int recordsPerPage = getNumberOfRecordsOnCurrentPage();

        if (onLastPage() && paginationComponent.hasPreviousPage()) {
            paginationComponent.previousPage();
            recordsPerPage = getNumberOfRecordsOnCurrentPage();
            paginationComponent.nextPage();
        }
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }


    public int getNumberOfRecordsOnCurrentPage() {
        if (paginationComponent.getTotalRecords() == 0)
            return 0;

        Range<Integer> currentRecordRange = paginationComponent.getCurrentRecordRange();
        return currentRecordRange.upperEndpoint() - currentRecordRange.lowerEndpoint() + 1;
    }


    /**
     * Returns the currentPage.
     *
     * @return <p/>
     * PageNumber as visible in the pagination Controls<p/>
     * 1 if there is data but pagination controls is not visible due to size of data<p/>
     * 0 if there are not data
     */
    public int currentPage() {
        if (paginationComponent.hasPaginationControls())
            return paginationComponent.currentPage();
        else {
            if (getNumberOfRecordsOnCurrentPage() > 0)
                return 1;
        }
        return 0;
    }

    /**
     * Navigate to nextPage if applicable
     * If navigation is not possible it will return false. This is
     * intended to allow idioms such as
     * <pre><code>
     *     while(nextPage()){
     *     }
     * </code> </pre>
     * although the applicability of such idiom is at doubt.
     *
     * @return true if navigated to next Page, false otherwise
     */
    public boolean nextPage() {
        if (paginationComponent.hasNextPage()) {
            paginationComponent.nextPage();
            return true;
        }
        return false;
    }

    public int getTotalRecords() {
        return paginationComponent.getTotalRecords();
    }

    public boolean hasNextPage() {
        return paginationComponent.hasNextPage();
    }

    public boolean hasPreviousPage() {
        return paginationComponent.hasPreviousPage();
    }

    public boolean hasPreviousPageSet() {
        return paginationComponent.hasPreviousPageSet();
    }

    public boolean hasNextPageSet() {
        return paginationComponent.hasNextPageSet();
    }
}
