package com.vh.mi.automation.test.utils;

import org.fest.assertions.Assertions;

import java.util.Collection;
import java.util.List;

/**
 * @author nimanandhar
 */
public class AssertionUtils {

    private AssertionUtils() {
    }

    public static void assertThatCollectionsContainSameElements(Collection collection1, Collection collection2) {
        Assertions.assertThat(collection1).containsOnly(collection2.toArray());
    }

    public static void assertThatListContainSameElements(List list1, List list2) {
        Assertions.assertThat(list1).containsOnly(list2.toArray());
    }
}
