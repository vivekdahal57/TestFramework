package com.vh.mi.automation.aspects;

import com.vh.mi.automation.api.annotations.documentation.ThreadSafe;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect to log and profile tests
 * To disable this aspect comment out the aspect in aop.xml
 * Note that this aspect will be used concurrently when running
 * tests in parallel threads, so it is important to make this thread
 * safe. In this case we are relying on the thread safety of ConcurrentHashMap
 * and Logger to ensure thread safety
 *
 * @author nimanandhar
 */

@Aspect
@ThreadSafe
public class ClickAspect {


    @Pointcut("execution(* org.openqa.selenium.WebElement.*(..))")
    public void seleniumClick() {/**/ }




    @Before("execution(* org.openqa.selenium..RemoteWebElement.click(..))")
    public void onStart(JoinPoint thisJoinPoint) {
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        throw new RuntimeException();
//        BaseTest.webDriverGlobal
    }
    @Before("execution(* org.openqa.selenium..RemoteWebElement+.click())")
    public void onStart2(JoinPoint thisJoinPoint) {
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        throw new RuntimeException();
//        BaseTest.webDriverGlobal
    }

    @Before("execution(* org.openqa.selenium..WebElement+.click())")
    public void onStart3(JoinPoint thisJoinPoint) {
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        throw new RuntimeException();
//        BaseTest.webDriverGlobal
    }


    private String getFileName(JoinPoint thisJoinPoint, Throwable exception) {
        return AspectUtils.getFullMethodNameWithParameters(thisJoinPoint) + "_exception=" + exception.getClass().getSimpleName();
    }


}
