package framework.api.annotations.documentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to document that a class is a singleton
 * Allows us to determine that a class is a singleton
 * without analyzing the source code
 *
 * Singleton as a design pattern is considered bad since
 * singleton class are often used as global variables.
 * Annotating all singletons allows us to find them very
 * easily and perhaps refactor them.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Singleton {
}
