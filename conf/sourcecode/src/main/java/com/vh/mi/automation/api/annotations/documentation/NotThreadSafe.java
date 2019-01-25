package com.vh.mi.automation.api.annotations.documentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to document that a class is not thread safe.
 *
 * @author nimanandhar
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface NotThreadSafe {
}
