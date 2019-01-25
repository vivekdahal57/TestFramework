package com.vh.mi.automation.impl.resources;

import com.vh.mi.automation.api.dto.User;
import org.fest.assertions.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author nimanandhar
 */
public class ResourcePoolUnitTest {
    private  ResourcePool<User> resources;
    private volatile User user;
    private volatile boolean resourceAcquired = false;

    @BeforeMethod
    public void setUp() {
        resources = new ResourcePool<>();
        resources.addResource(new User("A", "aUser", "aPassword"));
        resources.addResource(new User("B", "bUser", "bPassword"));
        resources.addResource(new User("C", "cUser", "cPassword"));
    }


    @Test(expectedExceptions = Error.class)
    public void threadShouldNotBeAbleToAcquireAResourceUntilItHasReleasedPreviouslyHeldResource() {
        User user1 = null, user2 = null;

        try {
            user1 = resources.getResource();
            Assertions.assertThat(user1).isNotNull();

            user2 = resources.getResource(); //should fail
        } catch (InterruptedException e) {
            Assert.fail("Impossible");
        } finally {
            if (user1 != null)
                resources.releaseResource(user1);
            if (user2 != null)
                resources.releaseResource(user2);
        }
    }

    @Test
    public void threadShouldNotBeAbleToReleaseAResourceHeldByDifferentThread() throws InterruptedException {
        resourceAcquired = false;
        Thread anotherThread = new Thread() {
            @Override
            public void run() {
                try {
                    user = resources.getResource();
                    resourceAcquired = true;
                    Thread.sleep(10000);
                    resources.releaseResource(user);
                } catch (InterruptedException ex) {
                    throw new Error("Impossible");
                }

            }
        };
        anotherThread.start();
        while (!resourceAcquired) {
            Thread.sleep(100);
        }

        try {
            resources.releaseResource(user);
            Assert.fail("Should fail since this thread is attempting to release the resource acquired by anotherThread");
        } catch (Error expected) {
            //expected
        }

    }
}