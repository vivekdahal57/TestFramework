package com.vh.mi.automation.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * A collection of utility methods used by aspects
 *
 * @author nimandhar
 */
public class AspectUtils {

    static String getClassAndMethodName(JoinPoint joinPoint) {
        if (joinPoint.getTarget() != null) {
            return joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
        } else {
            //static methods dont have a target object
            return joinPoint.getStaticPart().getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        }
    }

    static String getFullMethodNameWithParameters(JoinPoint joinPoint) {
        return getClassAndMethodName(joinPoint) + getParameters(joinPoint);
    }

    private static String getParameters(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < args.length; i++) {
            sb.append(parameterNames[i]).append("=").append(args[i]);
            if (i < args.length - 1)
                sb.append(",");
        }

        sb.append("]");
        return sb.toString();
    }

    static String getTimeTaken(Long startTime) {
        Long timeTakenMillis = System.currentTimeMillis() - startTime;
        return timeTakenMillis + " milliseconds";
    }
}
