package framework.api.annotations.documentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Since package private have no keyword in java
 * this annotation is used when the decision to
 * make any element package private is an important
 * element of the design and not some random decision
 *
 * @author bibdahal
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface PackagePrivate {
}
