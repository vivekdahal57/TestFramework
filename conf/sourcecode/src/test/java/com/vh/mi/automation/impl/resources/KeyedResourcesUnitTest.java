package com.vh.mi.automation.impl.resources;

import com.vh.mi.automation.api.dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author nimanandhar
 */
public class KeyedResourcesUnitTest {
    private KeyedResources<User> resources;
    private volatile boolean resourceAcquired = false;

    @BeforeMethod
    public void setUp() {
        resources = new KeyedResources<>();
        resources.addResource("A", new User("A", "aUser", "aPassword"));
        resources.addResource("B", new User("B", "bUser", "bPassword"));
        resources.addResource("C", new User("C", "cUser", "cPassword"));
    }

    @Test
    public void testThatAThreadCannotMoreThanOneResourceSimultaneously() throws Exception {
        User userA = resources.getResource("A");
        assertThat(userA).isNotNull();
        assertThat(userA.getUserId()).isEqualTo("aUser");

        try {
            resources.getResource("A");
            Assert.fail("A Thread should not reacquire resource it already holds");
        } catch (Error expected) {
            //expected
        }

        try {
            resources.getResource("B");
            Assert.fail("A Thread should not acquire a different resource without releasing a previously held resource");
        } catch (Error expected) {
            //expected
        }

        resources.releaseResource("A");
        User userB = resources.getResource("B");
        assertThat(userB).isNotNull();
        assertThat(userB.getUserId()).isEqualTo("bUser");
        resources.releaseResource("B");

    }

    @Test(expectedExceptions = Error.class)
    public void releasingAResourceNotAcquiredByThisThreadShouldThrowException() throws InterruptedException {
        Thread threadA = new Thread() {
            @Override
            public void run() {
                try {
                    resources.getResource("A");
                } catch (InterruptedException e) {
                    throw new Error("Impossible");
                }
            }
        };
        threadA.start();
        resources.getResource("B");
        resources.releaseResource("A");
    }


    @Test
    public void aThreadShouldBlockUntilTheResourceIsREleasedByAnotherThread() throws InterruptedException {
        final int SLEEP_TIME = 5000;
        resourceAcquired = false;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    resources.getResource("A");
                    resourceAcquired = true;
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Impossible");
                } finally {
                    resources.releaseResource("A");
                }
            }
        };

        long startTime = System.currentTimeMillis();
        thread.start();

        waitTillOtherThreadHasAcquiredResource();
        User userA = resources.getResource("A");
        assertThat(userA).isNotNull();
        assertThat(userA.getUserId()).isEqualTo("aUser");

        long elapsedTime = System.currentTimeMillis() - startTime;
        assertThat(elapsedTime).isGreaterThan(SLEEP_TIME);


    }

    private void waitTillOtherThreadHasAcquiredResource() {
        while (!resourceAcquired) {
            try {
                Thread.sleep(100); //check at intervals of 0.1 seconds
            } catch (InterruptedException e) {
                throw new RuntimeException("Unexpected . Thread was interrupted from sleep");
            }
        }
    }
}