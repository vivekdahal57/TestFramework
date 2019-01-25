package framework.api.annotations.documentation;

/**
 * Annotation to document that a class is Immutable
 * A class which does not have this annotation is
 * assumed to be mutable.
 * <p/>
 * This annotation implies that the class is thread safe
 * and hence does not require the @ThreadSafe Annotation
 *
 * @author bibdahal
 */

public @interface Immutable {
}
