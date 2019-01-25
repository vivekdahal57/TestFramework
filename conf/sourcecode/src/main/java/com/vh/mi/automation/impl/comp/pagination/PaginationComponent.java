package com.vh.mi.automation.impl.comp.pagination;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPanelComponent;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.impl.utils.NumberParser;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page Object for Pagination Control
 *
 * @author nimanandhar
 */
public class PaginationComponent extends AbstractWebComponent implements IPaginationComponent {
    private static final String NEXT_PAGE_IMAGE = "/vh/images/navigate_right.gif";
    private static final String NEXT_PAGE_SET_IMAGE = "/vh/images/navigate_right2.gif";
    private static final String PREVIOUS_PAGE_IMAGE = "/vh/images/navigate_left.gif";
    private static final String PREVIOUS_PAGE_SET_IMAGE = "/vh/images/navigate_left2.gif";
    private static final Pattern RECORDS_PATTERN = Pattern.compile("Records ([\\d,]+)-([\\d,]+) of ([\\d,]+)");
    private final String containerId;

    private final WebElements webElements;
    private final LoadingPopup loadingPopup;
    private final LoadingPanelComponent loadingPanelComponent;

    public static IPaginationComponent newD2FormPaginationComponent(WebDriver webDriver) {
        return new PaginationComponent(webDriver, "d2Form:paginationPanel");
    }

    public static IPaginationComponent newBLPaginationComponent(WebDriver webDriver) {
        return new PaginationComponent(webDriver, "_nKeyPaginationPanel");

    }

    private PaginationComponent(WebDriver driver, String paginationContainerId) {
        super(driver);
        webElements = new WebElements(driver, paginationContainerId);
        this.containerId = paginationContainerId;
        loadingPanelComponent = new LoadingPanelComponent(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public Range<Integer> getCurrentRecordRange() {
        String text = webElements.recordsLabel().getText();
        if (text.equals("No record found "))
            throw new UnsupportedOperationException("Unsupported operation getCurrentRecordRange. No record found");

        Matcher matcher = RECORDS_PATTERN.matcher(text);
        if (matcher.find()) {
            int lower = NumberParser.parseInteger(matcher.group(1));
            int upper = NumberParser.parseInteger(matcher.group(2));

            return Range.closed(lower, upper);
        } else
            throw new RuntimeException("Could not match pattern in text " + text);
    }


    @Override
    public final int getTotalRecords() {
        return NumberParser.parseInteger(webElements.totalRecords().getAttribute("value"));
    }


    @Override
    public final boolean hasNextPage() {
        return hasPaginationControls() && isClickable(parentElementOfImage(NEXT_PAGE_IMAGE));
    }

    @Override
    public final int currentPage() {
        if (!hasPaginationControls())
            throw new UnsupportedOperationException("Unsupported Operation currentPage. Current Page is not displayed in pagination component");
        return NumberParser.parseInteger(webElements.currentPage().getText());
    }


    @Override
    public Set<Integer> getCurrentPageSet() {
        if (!hasPaginationControls())
            throw new UnsupportedOperationException("Unsupported Operation getCurrentPageSet. Pagination Controls are not available");

        List<WebElement> elements = webElements.container().findElements(By.xpath(".//td[@class='d2-pagination-link' or @class='d2-pagination-currentPage']"));

        Set<Integer> pages = new HashSet<>();
        for (WebElement element : elements) {
            pages.add(Integer.valueOf(element.getText()));
        }

        return pages;
    }


    private boolean isClickable(WebElement webElement) {
        return StringUtils.equalsIgnoreCase(webElement.getTagName(), "a");
    }

    private WebElement parentElementOfImage(String imageSource) {
        return webElements.container().findElement(By.xpath(".//img[@src='" + imageSource + "']/parent::*"));
    }

    private void clickOnImage(String imgSource) {
        WebElement webElement = parentElementOfImage(imgSource);
        Preconditions.checkState(isClickable(webElement));
       SeleniumUtils.click(webElement,getDriver());
        waitTillLoadingPopupDisappears();
    }

    private void waitTillLoadingPopupDisappears() {
        if (containerId.equals("d2Form:paginationPanel"))
            loadingPopup.waitTillDisappears();          //for pagination component of forms
        else
            loadingPanelComponent.waitTillDisappears(); //for pagination component in Business Levels
    }

    @Override
    public void nextPage() {
        if (!hasNextPage())
            throw new UnsupportedOperationException("Unsupported Operation nextPage. nextPage control is disabled or unavailable");
        clickOnImage(NEXT_PAGE_IMAGE);
    }

    @Override
    public boolean hasPreviousPage() {
        return hasPaginationControls() && isClickable(parentElementOfImage(PREVIOUS_PAGE_IMAGE));
    }

    @Override
    public void previousPage() {
        if (!hasPreviousPage())
            throw new UnsupportedOperationException("Unsupported Operation previousPage.Control is disabled or unavailable");
        clickOnImage(PREVIOUS_PAGE_IMAGE);
    }


    @Override
    public boolean hasNextPageSet() {
        return hasPaginationControls() && isClickable(parentElementOfImage(NEXT_PAGE_SET_IMAGE));
    }

    @Override
    public void nextPageSet() {
        if (!hasNextPageSet())
            throw new UnsupportedOperationException("Unsupported Operation nextPageSet.Control is disabled or unavailable");
        clickOnImage(NEXT_PAGE_SET_IMAGE);
    }


    @Override
    public boolean hasPreviousPageSet() {
        return hasPaginationControls() && isClickable(parentElementOfImage(PREVIOUS_PAGE_SET_IMAGE));
    }

    @Override
    public void previousPageSet() {
        if (!hasPreviousPageSet())
            throw new UnsupportedOperationException("Unsupported Operation previousPageSet.Control is disabled or unavailable");
        clickOnImage(PREVIOUS_PAGE_SET_IMAGE);
    }

    @Override
    public boolean hasPaginationControls() {
        return SeleniumUtils.elementExists(webElements.container(), ".//td[@class='d2-pagination-currentPage']");
    }

    @Override
    public void selectPage(int pageNumber) {
        if (!hasPaginationControls())
            throw new UnsupportedOperationException("Unsupported Operation gotoPage. Pagination Control Not available");
        if (!getCurrentPageSet().contains(pageNumber))
            throw new UnsupportedOperationException("Unsupported Operation gotoPage. Given Page number is invalid or not currently selectable");

        if (currentPage() == pageNumber)
            return;
        webElements.container().findElement(By.xpath(".//td[@class='d2-pagination-link']/a[text()=" + pageNumber + "]")).click();
        waitTillLoadingPopupDisappears();
    }


    public boolean isFullyLoaded() {
        return webElements.container().isDisplayed();
    }

    private static class WebElements {
        private WebDriver webDriver;
        private String rootElement;

        private WebElements(WebDriver webDriver, String containerId) {
            this.webDriver = webDriver;
            this.rootElement = containerId;
        }

        public WebElement container() {
            return webDriver.findElement(By.id(rootElement));
        }


        private WebElement recordsLabel() {
            return webDriver.findElement(By.xpath("//*[@id='" + rootElement + "']/table[@class='d2-pagination-table']/tbody/tr/td"));
        }

        private WebElement totalRecords() {
            return webDriver.findElement(By.xpath("//*[@id='" + rootElement + "']/input[@value and @type='hidden' and contains(@class,'totalRecords')]"));
        }

        private WebElement currentPage() {
            return webDriver.findElement(By.xpath("//*[@id='" + rootElement + "']//*[@class='d2-pagination-currentPage']"));
        }

    }
}
