package com.vh.mi.automation.test.comp.navigationPanel;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Uses reflection to create a map of landingPages and the corresponding LandingPage implementation class
 * Created by nimanandhar on 10/10/2014.
 */
public class LandingPageClasses {
    private static final String PACKAGE_NAME = "com.vh.mi.automation.impl.pages";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<MILandingPages, Class> pagesClassMap = new HashMap<>();
    private final ClassLoader classLoader;
    private final WebDriver webDriver;
    private final WebElements webElements;

    LandingPageClasses(WebDriver webDriver) {
        Preconditions.checkArgument(webDriver != null);
        this.webDriver = webDriver;
        this.classLoader = LandingPageClasses.class.getClassLoader();
        initializeMap();
        webElements = new WebElements(webDriver);
    }

    public void popupExists() {
        if(webElements.closePopup.isDisplayed()){
            SeleniumUtils.click(webElements.closePopup, webDriver);
        }
    }

    Set<MILandingPages> getAllImplementedLandingPages() {
        return pagesClassMap.keySet();
    }

    Class<IAmLandingPage> getImplClass(MILandingPages landingPages) {
        if (!hasMappingFor(landingPages)) {
            throw new RuntimeException("Landing Page " + landingPages + " not found in pagesClassMap");
        }
        return pagesClassMap.get(landingPages);
    }

    boolean hasMappingFor(MILandingPages landingPages) {
        return pagesClassMap.containsKey(landingPages);
    }

    private void initializeMap() {
        try {
            ImmutableSet<ClassPath.ClassInfo> classesInPackage = ClassPath.from(classLoader).getTopLevelClassesRecursive(PACKAGE_NAME);
            for (ClassPath.ClassInfo classInfo : classesInPackage) {
                if (isNonAbstractLandingPage(classInfo)) {
                    Optional<MILandingPages> landingPage = getLandingPage(classInfo);
                    if (landingPage.isPresent())
                        pagesClassMap.put(landingPage.get(), classInfo.load());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNonAbstractLandingPage(ClassPath.ClassInfo classInfo) {
        try {
            Class<?> aClass = classLoader.loadClass(classInfo.getName());
            if (IAmLandingPage.class.isAssignableFrom(aClass) && !aClass.getSimpleName().startsWith("Abstract")) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    private Optional<MILandingPages> getLandingPage(ClassPath.ClassInfo classInfo) {
        try {
            Constructor<?> landingPageConstructor = classInfo.load().getConstructor(WebDriver.class);
            IAmLandingPage landingPage = (IAmLandingPage) landingPageConstructor.newInstance(webDriver);

            String pageId = landingPage.getPageId();
            if(pageId.isEmpty())
                return Optional.absent();

            MILandingPages miLandingPage = MILandingPages.getByPageId(pageId);
            return Optional.of(miLandingPage);

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:_tryNewIndividualModal_controls_close")
        WebElement closePopup;
    }

}
