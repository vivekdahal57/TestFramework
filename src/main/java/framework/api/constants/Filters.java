package framework.api.constants;

/**
 * Created by bibdahal
 */
public enum Filters {
    // filters for Numeric columns
    LESS_OR_EQUALS,
    GREATER_OR_EQUALS,
    EQUALS,
    BETWEEN,
    NOT_EQUALS,

    //filters for columns with String data type
    CONTAINS,
    NOT_CONTAINS,

    //filters for columns with Date data type
    BEFORE,
    TO,
    AFTER;

}
