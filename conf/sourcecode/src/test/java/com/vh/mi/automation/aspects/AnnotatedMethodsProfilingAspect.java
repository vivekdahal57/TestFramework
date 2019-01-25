package com.vh.mi.automation.aspects;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.logging.LogMethodEnter;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.annotations.logging.LogMethodExit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.vh.mi.automation.aspects.AspectUtils.getFullMethodNameWithParameters;
import static com.vh.mi.automation.aspects.AspectUtils.getTimeTaken;

/**
 * Aspect for profiling methods annotated with
 * <li>{@link LogMethodEnter}  </li>
 * <li>{@link LogMethodExit} </li>
 * <li>{@link LogMethodExecutionTime}</li>
 *
 * @author nimanandhar
 */
@Aspect
public class AnnotatedMethodsProfilingAspect {
    Logger logger = LoggerFactory.getLogger(AnnotatedMethodsProfilingAspect.class);
    private final Map<JoinPoint, Long> methodStartTimes = new ConcurrentHashMap<>();

    /**
     * Pointcuts
     */
    @Pointcut("execution(@(com.vh..LogMethodEnter) * *.*(..))")
    public void logMethodEnterAnnotatedMethods() {/**/ }

    @Pointcut("execution(@(com.vh..LogMethodExit) * *.*(..))")
    public void logMethodExitAnnotatedMethods() {
    }

    @Pointcut("execution(@(com.vh..LogMethodExecutionTime) * *.*(..))")
    public void logMethodExecutionTimeAnnotatedMethods() {
    }


    /**
     * Advices for @LogMethodEnter annotated methods
     */
    @Before("logMethodEnterAnnotatedMethods()")
    public void beforeExecutingLogMethodEnterAnnotatedMethods(JoinPoint thisJoinPoint) {
        logger.info("Entered method {} at {}", AspectUtils.getFullMethodNameWithParameters(thisJoinPoint), System.currentTimeMillis());
    }

    /**
     * Advice for @LogMethodExit annotated methods completed successfully
     */
    @AfterReturning("logMethodExitAnnotatedMethods()")
    public void afterExecutingLogMethodExitAnnotatedMethods(JoinPoint thisJoinPoint) {
        logger.info("Completed method {} at {} ", AspectUtils.getFullMethodNameWithParameters(thisJoinPoint), System.currentTimeMillis());
    }

    /**
     * Advice for @LogMethodExit annotated methods completed successfully
     */
    @AfterThrowing("logMethodExitAnnotatedMethods()")
    public void afterExceptionInLogMethodExitAnnotatedMethods(JoinPoint thisJoinPoint) {
        logger.info("Failed Method {} at {} ", AspectUtils.getFullMethodNameWithParameters(thisJoinPoint), System.currentTimeMillis());
    }


    /**
     * Track start time for methods annotated with @LogMethodExecutionTime
     */
    @Before("logMethodExecutionTimeAnnotatedMethods()")
    public void beforeExecutingLogMethodExecutionTimeAnnotatedMethods(JoinPoint thisJoinPoint) {
        methodStartTimes.put(thisJoinPoint, System.currentTimeMillis());
    }

    /**
     * Calculate and log the execution time for methods annotated with @LogMethodExecutionTime
     */
    @AfterReturning("logMethodExecutionTimeAnnotatedMethods()")
    public void afterExecutingLogMethodExecutionTimeAnnotatedMethods(JoinPoint thisJoinPoint) {
        Preconditions.checkState(methodStartTimes.containsKey(thisJoinPoint));
        Long startTime = methodStartTimes.remove(thisJoinPoint);
        long finishedTime = System.currentTimeMillis();
        logger.info("Method Completed : {} Started: {} Ended: {} TimeTaken: {}",
                getFullMethodNameWithParameters(thisJoinPoint),
                startTime,
                finishedTime,
                getTimeTaken(startTime)
        );
    }

    /**
     * Calculate and log the execution time for methods annotated with @LogMethodExecutionTime
     */
    @AfterThrowing("logMethodExecutionTimeAnnotatedMethods()")
    public void afterExceptionLogMethodExecutionTimeAnnotatedMethods(JoinPoint thisJoinPoint) {
        Preconditions.checkState(methodStartTimes.containsKey(thisJoinPoint));
        Long startTime = methodStartTimes.remove(thisJoinPoint);
        long finishedTime = System.currentTimeMillis();
        logger.info(
                "Method Failed : {} Started: {} Ended: {} TimeTaken: {}",
                getFullMethodNameWithParameters(thisJoinPoint),
                startTime,
                finishedTime,
                getTimeTaken(startTime)
        );
    }
}
