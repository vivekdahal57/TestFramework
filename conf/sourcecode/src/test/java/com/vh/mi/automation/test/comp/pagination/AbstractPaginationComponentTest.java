package com.vh.mi.automation.test.comp.pagination;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.constants.SortOrder;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponentFacade;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * PaginationTest
 *
 * @author nimanandhar
 */
public abstract class AbstractPaginationComponentTest extends BaseTest {
    private PaginationComponentFacade paginator;
    private IDataGrid dataGrid;

    public void setUp() {
        super.setUp();
        setupPage();
        paginator = new PaginationComponentFacade(getPaginationComponent());
        dataGrid = getDataGrid();
        assertThat(dataGrid).isNotNull();
    }

    /**
     * Hook Method to allow tests to navigate to corresponding pages
     * before tests for this component begin
     * <p/>
     * This method is guaranteed to be called before
     * {@link AbstractPaginationComponentTest#getDataGrid}
     * {@link AbstractPaginationComponentTest#getPaginationComponent()}
     */
    protected abstract void setupPage();


    protected abstract IPaginationComponent getPaginationComponent();


    protected abstract IDataGrid getDataGrid();


    @Test(description = "The number of records displayed in data grid should match the record count in" +
            "pagination component.")

    public void recordsMatchInDataGridAndPaginationComponentTest() {
        assertThat(dataGrid.getRows()).hasSize(paginator.getNumberOfRecordsOnCurrentPage());
    }


    @Test(description ="Check whether Data Grid is empty or not")
    public void dataGridShouldNotBeEmpty(){
        assertThat(dataGrid.getData().size() != 0);
    }


    @Test
    public void dataGridShouldChangeWhenDifferentPageIsViewed() {
        //test that all the records in the new page is different
        //and the record count displayed in pagination component changes accordingly
        if (paginator.getTotalPages() < 2)
            throw new SkipException("Skipping test because there is only a single page");

        paginator.gotoFirstPage();
        String dataForFirstPage = dataGrid.getTableDataAsText();

        paginator.gotoPage(2);
        String dataForSecondPage = dataGrid.getTableDataAsText();

        assertThat(dataForFirstPage).isNotEqualTo(dataForSecondPage);

    }


    @Test
    public void changingPagesShouldMaintainCurrentSortOrderAndColumn() {
        //tests that when we navigate to different page, the sort column and order will not change
        paginator.gotoFirstPage();
        if (!paginator.hasNextPage()) {
            throw new SkipException("Skipping test because pagination component has only a single page");
        }

        IDataGridColumn testColumn = getASortableColumn();
        dataGrid.doSort(testColumn, SortOrder.ASC);

        int pageNumber = paginator.currentPage();
        paginator.nextPage();
        assertThat(paginator.currentPage()).isEqualTo(pageNumber + 1);
        assertThat(dataGrid.getCurrentSortedColumn()).isEqualTo(testColumn);
    }


    private IDataGridColumn getASortableColumn() {
        for (IDataGridColumn column : dataGrid.getColumns()) {
            if (dataGrid.isSortable(column)) {
                return column;
            }
        }
        throw new SkipException("No sortable columns found");
    }


    @Test
    public void previousPageButtonShouldBeDisabledWhenFirstPageIsDisplayed() {
        paginator.gotoFirstPage();
        assertThat(paginator.hasPreviousPage()).isFalse();
        assertThat(paginator.hasPreviousPageSet()).isFalse();
    }

    @Test
    public void nextPageButtonShouldBeDisabledWhenLastPageIsDisplayed() {
        int requiredNumberOfClicksToReachLastPage = (int) Math.ceil(paginator.getTotalPages() / 5.0);
        if (requiredNumberOfClicksToReachLastPage > 3) {
            throw new SkipException("Skipping test because it takes too long to navigate to last page");
        }

        paginator.gotoLastPage();
        assertThat(paginator.hasNextPage()).isFalse();
        assertThat(paginator.hasNextPageSet()).isFalse();
    }


    @Test
    public void previousPageShouldBeEnabledWhenSecondPageIsDisplayed() {
        if (paginator.getTotalPages() < 2) {
            throw new SkipException("Skipping test because no of pages is less than 2");
        }
        paginator.gotoPage(2);
        assertThat(paginator.hasPreviousPage()).isTrue();
        assertThat(paginator.hasPreviousPageSet()).isFalse();
    }


    @Test
    public void numberOfPagesInPaginationControlShouldBeLessThanOrEqualToTen() {
        if (paginator.paginationComponent().hasPaginationControls())
            assertThat(paginator.paginationComponent().getCurrentPageSet().size()).isLessThanOrEqualTo(10);
    }

}
