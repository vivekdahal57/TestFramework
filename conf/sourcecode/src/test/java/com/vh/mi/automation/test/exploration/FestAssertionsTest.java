package com.vh.mi.automation.test.exploration;

import org.fest.assertions.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author nimanandhar
 */
public class FestAssertionsTest {

    @Test
    public void testThatContainsOnlyChecksThatACollectionHasExactlySameElementsRegardlessOfPosition() throws Exception {
        Collection<String> list1 = Arrays.asList("one", "two");
        Collection<String> list2 = Arrays.asList("two", "one");
        Collection<String> list3 = Arrays.asList("one", "two", "three");
        Collection<String> list4 = Arrays.asList("one");
        Assertions.assertThat(list1).containsOnly(list2.toArray());

        try {
            Assertions.assertThat(list1).containsOnly(list3.toArray());
            Assert.fail("containsOnly should fail if list are different");
        } catch (AssertionError expected) {
        }

        try {
            Assertions.assertThat(list1).containsOnly(list4.toArray());
            Assert.fail("containsOnly should fail if list are different");
        } catch (AssertionError expected) {
        }

    }
}
