package com.vh.mi.automation.api.annotations.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Methods that have this annotation will
 * log the execution time of the method
 *
 * @author nimanandhar
 */

@Retention(RUNTIME)
@Target({METHOD})
public @interface LogMethodExecutionTime {
}
