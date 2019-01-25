package framework.api.annotations.documentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to document classes which are not
 * technically immutable, but are designed such
 * that the class will not be modified once it
 * has been constructed
 *
 * @author bibdahal
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface EffectivelyImmutable {
}
