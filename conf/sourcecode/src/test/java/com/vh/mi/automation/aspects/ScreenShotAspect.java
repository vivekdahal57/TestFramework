package com.vh.mi.automation.aspects;

import com.vh.mi.automation.impl.utils.screenshot.ScreenShotTaker;
import com.vh.mi.automation.test.base.BaseTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

/**
 * Takes screen-shot when Tests fails, or exception occurs while running tests
 *
 * @author nimanandhar
 */
@Aspect
public class ScreenShotAspect {
    Logger logger = LoggerFactory.getLogger(ScreenShotAspect.class);
    private ScreenShotTaker screenShotTaker = new ScreenShotTaker();

    @Pointcut("execution(@( org.testng.annotations.*) * *..BaseTest+.*(..))")
    public void testExecutionPointcut() {
    }

    @AfterThrowing(value = "testExecutionPointcut()", throwing = "exception")
    public void onTestFailedOrSkipped(JoinPoint thisJoinPoint, Throwable exception) {
        try {
            if (exception instanceof SkipException)
                return; //don't need to take screen-shot for skipped tests

            BaseTest testClass = (BaseTest) thisJoinPoint.getTarget();
            WebDriver webDriver = testClass.getWebDriver();

            if (webDriver != null) {
                String fileName = getFileName(thisJoinPoint, exception);
                screenShotTaker.takeScreenShot(webDriver, fileName);
            } else {
                logger.warn("Cannot take ScreenShot. WebDriver is Null {}", testClass.getClass().getSimpleName());
            }
        } catch (Exception ex) {
            logger.error("Excepton occurred {}", ex);
        }

    }


    private String getFileName(JoinPoint thisJoinPoint, Throwable exception) {
        return AspectUtils.getFullMethodNameWithParameters(thisJoinPoint) + "_exception=" + exception.getClass().getSimpleName() + ".png";
    }
}
