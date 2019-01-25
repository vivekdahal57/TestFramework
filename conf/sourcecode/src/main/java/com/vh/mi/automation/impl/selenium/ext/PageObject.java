package com.vh.mi.automation.impl.selenium.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark fields in a composite page object whose <code>WebElement</code>
 * can be initialized by <code>AutomationPageFactory</code>.
 * 
 * 
 * @author rshah (i68052)
 * @Version: Id:$ PageObject.java, Jul 26, 2013$
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface PageObject {

}
