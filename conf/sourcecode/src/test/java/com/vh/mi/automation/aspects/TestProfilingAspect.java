package com.vh.mi.automation.aspects;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.documentation.ThreadSafe;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.vh.mi.automation.aspects.AspectUtils.getFullMethodNameWithParameters;
import static com.vh.mi.automation.aspects.AspectUtils.getTimeTaken;

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
public class TestProfilingAspect {
    private final Logger logger = LoggerFactory.getLogger(TestProfilingAspect.class);
    private final Map<JoinPoint, Long> testStartTimes = new ConcurrentHashMap<>();

    @Pointcut("execution(@(org.testng.annotations.*) * *.*(..))")
    public void testNGAnnotatedMethodExecution() {/**/ }

    @Before("execution(@(org.testng.annotations.BeforeSuite) * *.*(..))")
    public void logBambooPlanAndJobIdBeforeSuite() {
        logger.info("BuildNo: {} JobId: {}", System.getProperty("buildNo", "N/A"), System.getProperty("jobId", "N/A"));
    }


    @Before("testNGAnnotatedMethodExecution()")
    public void onTestStart(JoinPoint thisJoinPoint) {
        Preconditions.checkState(!testStartTimes.containsKey(thisJoinPoint));
        testStartTimes.put(thisJoinPoint, System.currentTimeMillis());
    }


    @AfterReturning("testNGAnnotatedMethodExecution()")
    public void onTestCompletedSuccessfully(JoinPoint thisJoinPoint) {
        Preconditions.checkState(testStartTimes.containsKey(thisJoinPoint));
        Long startTime = testStartTimes.remove(thisJoinPoint);
        Long finishedTime = System.currentTimeMillis();
        logger.info(
                "{} Completed : {} Started: {} Ended: {} TimeTaken: {}",
                getMethodType(thisJoinPoint),
                getFullMethodNameWithParameters(thisJoinPoint),
                startTime,
                finishedTime,
                getTimeTaken(startTime)
        );
    }

    @AfterThrowing(value = "testNGAnnotatedMethodExecution()", throwing = "exception")
    public void onTestFailedOrSkipped(JoinPoint thisJoinPoint, Throwable exception) {
        Preconditions.checkState(testStartTimes.containsKey(thisJoinPoint));
        Long startTime = testStartTimes.remove(thisJoinPoint);
        Long finishedTime = System.currentTimeMillis();

        String testOrSetupMethod = getMethodType(thisJoinPoint);
        String failedOrSkipped = "Skipped";
        if (!(exception instanceof SkipException)) {
            failedOrSkipped = "Failed";
            logger.warn("Exception occurred in {}", AspectUtils.getClassAndMethodName(thisJoinPoint), exception); //print stack trace
        }

        logger.info(
                "{} {} : {} Started: {} Ended: {} TimeTaken: {}",
                testOrSetupMethod,
                failedOrSkipped,
                getFullMethodNameWithParameters(thisJoinPoint),
                startTime,
                finishedTime,
                getTimeTaken(startTime));
    }

    private String getMethodType(JoinPoint thisJoinPoint) {
        Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
        if (method.getAnnotation(Test.class) != null) { //method annotated with @Test annotation
            return " Test";
        } else {
            return "Setup";
        }
    }


}
