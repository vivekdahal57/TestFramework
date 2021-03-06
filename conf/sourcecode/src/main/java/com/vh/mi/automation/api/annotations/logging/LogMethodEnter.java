package com.vh.mi.automation.api.annotations.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Methods that have this annotation will generate
 * log when the method is entered
 *
 * @author nimanandhar
 */

@Retention(RUNTIME)
@Target({METHOD})
public @interface LogMethodEnter {
}
