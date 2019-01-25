package com.vh.mi.automation.impl.comp.navPanel;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vh.mi.automation.api.utils.WaitUtils.waitForSeconds;

/**
 * Helper Class to be  used by tests related to Navigation Panel
 * Deals mostly with finding and navigating modules, for verifying
 * the modules are listed in alphabetical order, and clicking on links
 * <p/>
 * This might later be merged with main NavigationPanel component
 * however,many of the features will not be used by normal clients
 * of NavigationPanel.Another reason for the need of this class is that
 * NavigationPanel Component does not directly deal with clicking on links
 * rather it relies on page object classes for landing pages.
 * <p/>
 * Created by nimanandhar on 10/28/2014.
 */
public class NavigationPanelTestHelper {
    public static final String OPEN_PARENT_LEVEL = "open-parent-level";
    public static final String CLOSE_PARENT_LEVEL = "close-parent-level";
    public static final String XPATH_MODULES = "./ul/li/div[1][child::*[(self::a or self::span) and @title ]]";
    public static final String XPATH_REPORT_MODULES = "./ul/li/div[1][child::a]";
    public static final int TIME_OUT = 180;

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private NavigationPanel.WebElements webElements;

    public NavigationPanelTestHelper(WebDriver webDriver) {
        this.driver = webDriver;
        webElements = new NavigationPanel.WebElements(driver);
    }

    public List<Module> getModules(SwitchBoard switchBoard) {
        List<Module> modules;
        try {
            modules = getModulesNew(switchBoard);
        }catch(StaleElementReferenceException e){
            logger.info("Stale Element Exception But Handled");
            SeleniumUtils.refreshPage(getDriver());
            modules = getModulesNew(switchBoard);
        }
        return modules;
    }

    public List<com.vh.mi.automation.groovy.Module> getModulesNew(SwitchBoard switchBoard) {
        String xpath = XPATH_MODULES;
        if (switchBoard == SwitchBoard.REPORTS) {
            xpath = XPATH_REPORT_MODULES;
        }
        WebElement switchboardContentElement = webElements.getContentElement(switchBoard);

        if (!SeleniumUtils.elementExists(switchboardContentElement, xpath)) {
            return Collections.EMPTY_LIST;
        }

        List<WebElement> elements = switchboardContentElement.findElements(By.xpath(xpath));
        List<com.vh.mi.automation.groovy.Module> modules = new ArrayList<>();
        for (WebElement element : elements) {
            String title = element.findElement(By.xpath(".//*[self::a or self::span]")).getAttribute("innerHTML");
            String id = element.getAttribute("id");

            Preconditions.checkState(!title.isEmpty(), "Invalid State When Getting Modules for Switchboard " + switchBoard + " . Title is empty");
            Preconditions.checkState(!id.isEmpty(), "Invalid State When Getting Modules for Switchboard " + switchBoard + " . Id is null");

            com.vh.mi.automation.groovy.Module module = com.vh.mi.automation.groovy.Module.builder().id(id).linkName(title).switchBoard(switchBoard).build();
            if (hasSubModules(element))
                populateSubModules(module, element);
            modules.add(module);
        }
        return modules;
    }


    private void populateSubModules(com.vh.mi.automation.groovy.Module parentModule, WebElement parentDiv) {

        List<WebElement> childElements = parentDiv.findElements(By.xpath("./following-sibling::div/ul/li/div[1][child::*[self::a | self::span]]"));
        for (WebElement childElement : childElements) {
            String title = childElement.findElement(By.xpath("./*[@title]")).getAttribute("title");
            String id = childElement.getAttribute("id");

            com.vh.mi.automation.groovy.Module childModule = com.vh.mi.automation.groovy.Module.builder().id(id).linkName(title).switchBoard(parentModule.getSwitchBoard()).build();
            parentModule.addChild(childModule);

            if (hasSubModules(childElement))
                populateSubModules(childModule, childElement);
        }
    }


    private boolean hasSubModules(WebElement parentDiv) {
        return SeleniumUtils.elementExists(parentDiv, "./following-sibling::div/ul/li");
    }


    public void doExpandSection(Module module) {
        assertThatModuleIsSection(module);
        expandSectionRecursively(module);
    }

    private void expandSectionRecursively(Module section) {
        if (section.hasParent()) {
            expandSectionRecursively(section.getParent());
        }

        //now expand this section because it has no further parent section
        if (isExpanded(section)) {
            return;
        }
        clickOnSection(section);
    }

    public boolean isExpanded(Module module) {
        assertThatModuleIsSection(module);
        return getModuleElement(module).getAttribute("class").endsWith(OPEN_PARENT_LEVEL);
    }


    private void clickOnSection(Module section) {
        WebElement element = getModuleElement(section);
        Preconditions.checkState(element.isDisplayed(), "Error while clicking on section " + section + "Element must be displayed before it can be clicked.Perhaps the switchboard is collapsed.");
        SeleniumUtils.hoverAndClick(getDriver(), element);
        waitForSeconds(1);
    }

    public void doCollapseSection(Module module) {
        if (isCollapsed(module)) {
            return;
        }

        WebElement element = getModuleElement(module);
        Preconditions.checkState(element.isDisplayed(), "Invalid state when collapsing section " + module + ". Cause: Element must be displayed before it can be clicked");
        SeleniumUtils.click(element, getDriver());
        waitForSeconds(1);
    }


    public boolean isCollapsed(Module module) {
        assertThatModuleIsSection(module);
        return isParentCollapsed(module);//if any of the parent module is collapsed then the module is collapsed
    }

    public boolean isVisible(Module module) {
        Preconditions.checkNotNull(module, "Precondition violated in method isVisible. Module is null");
        Preconditions.checkArgument(moduleExist(module), "Precondition violated in method isVisible. Module " + module + " does not exist");
        return getModuleElement(module).isDisplayed();
    }

    private boolean isParentCollapsed(Module module) {
        if (module.hasParent()) {
            return isParentCollapsed(module.getParent()) || isModuleCollapsed(module);
        } else {
            return isModuleCollapsed(module);
        }
    }


    private boolean isModuleCollapsed(Module module) {
        return getModuleElement(module).getAttribute("class").endsWith(CLOSE_PARENT_LEVEL);
    }

    private WebElement getModuleElement(Module module) {
        return getDriver().findElement(By.id(module.getId()));
    }

    private boolean moduleExist(Module module) {
        return module != null && SeleniumUtils.elementExists(webElements.getContentElement(module.getSwitchBoard()), ".//div[@id='" + module.getId() + "']");
    }

    public void clickOnLink(Module link) {
        Preconditions.checkArgument(!link.hasChildren(), "Precondition violated in method clickOnLink");
        if (link.hasParent()) {
            doExpandSection(link.getParent());
        }
        WebElement moduleElement = getModuleElement(link);
        Preconditions.checkState(moduleElement.isDisplayed(), "Precondition violated in clickOnLink. Link = " + link + " Link must be displayed before it can be clicked");

        WebElement element = moduleElement.findElement(By.linkText(link.getLinkName()));
        SeleniumUtils.click(element, driver);

    }

    public boolean hasHomePageIcon(Module module) {
        WebElement moduleElement = getModuleElement(module);
        return SeleniumUtils.elementExists(moduleElement, "//input[contains(@title,'home page')]");
    }

    public boolean hasFavoriteIcon(Module module) {
        WebElement moduleElement = getModuleElement(module);
        return SeleniumUtils.elementExists(moduleElement, "//input[contains(@title,'Favorites')]");
    }

    @LogMethodExecutionTime
    public void waitTillHomePageLinksAppear(Module module) {
        String xpathForHomePage = "//div[@id='" + module.getId() + "']//*[contains(@title,'home page')]";
        new WebDriverWait(driver, TIME_OUT).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(xpathForHomePage)));
    }


    /**
     * Added to debug cases in IE where the homepage was not found
     * todo remove
     */
    public void printInnerHtmlOfModule(Module module) {
        try {
            String innerHTML = driver.findElement(By.id(module.getId())).getAttribute("innerHTML");
            logger.warn("innerHTML =" + innerHTML);
        } catch (Exception ex) {
            logger.error("Error occured while getting innerthmtl {} " + ex);

        }
    }

    public WebDriver getDriver() {
        return driver;
    }


    private void assertThatModuleIsSection(Module module) {
        Assert.assertTrue(module != null, "Assertion failed in method assertThatModuleIsSection. Module should not be null");
        Assert.assertTrue(moduleExist(module), "Assertion failed in method assertThatModuleIsSection. Module must exist. Module = " + module);
        Assert.assertTrue(module.hasChildren(), "Assertion failed in method assertThatModuleIsSection.A section module should have sub-modules. Module = " + module);

        String class_attribute = getModuleElement(module).getAttribute("class");

        Assert.assertTrue(class_attribute.endsWith(CLOSE_PARENT_LEVEL) || class_attribute.endsWith(OPEN_PARENT_LEVEL),
                "class of css module end with be either " + OPEN_PARENT_LEVEL + " or  " + CLOSE_PARENT_LEVEL);
    }


    public List<String> getSwitchboardHeaderTexts() {
        try {
            List<WebElement> switchboardElements = getDriver().findElements(By.xpath("//div[@id='nav-menu']/div[position() mod 2 = 1]"));
            List<String> switchboardTexts = new ArrayList<>();
            for (WebElement switchboardElement : switchboardElements) {
                String id = switchboardElement.getAttribute("id");
                Assertions.assertThat(id.startsWith("swb_"));
                String text = switchboardElement.getText();

                if (text.isEmpty()) {
                    logger.warn("The switchboard text for switchboard id " + id + " is Empty");
                    continue;
                }
                switchboardTexts.add(text);
            }
            return switchboardTexts;
        }catch(StaleElementReferenceException e){
            logger.info("Stale Element Exception but Handled");
            return getSwitchboardHeaderTexts();
        }
    }
}
