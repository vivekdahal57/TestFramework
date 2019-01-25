package com.vh.mi.automation.impl.resources;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.documentation.ThreadSafe;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An abstraction of a pool of resources  in which a thread
 * cannot acquire a different resource until it has released
 * a resource it already holds.
 * <p/>
 * If resource is not available, the calling thread blocks
 * until the resource becomes available
 *
 * @author nimanandhar
 */
@ThreadSafe
public class ResourcePool<T> {
    private final Map<Thread, T> dispensedResource = new ConcurrentHashMap<>();
    private final BlockingQueue<T> resources = new LinkedBlockingQueue<>();

    public void addResource(T resource) {
        Preconditions.checkNotNull(resource);
        resources.add(resource);
    }

    /**
     * Get a resource from the pool
     *
     * @throws InterruptedException if the blocked thread is interrupted
     * @throws Error                if the thread already holds another resource
     */
    public T getResource() throws InterruptedException {
        //if requesting thread had previously acquired the resource, return the same resource
        if (dispensedResource.containsKey(Thread.currentThread())) {
            throw new Error(String.format("The thread %s tried to acquire a resource without releasing previous resource", Thread.currentThread()));
        }

        T resource = resources.take();
        dispensedResource.put(Thread.currentThread(), resource);
        return resource;
    }

    /**
     * Release a resource back to the pool
     *
     * @throws Error if the resource belongs to a different thread
     */
    public void releaseResource(T resource) {
        assertResourceWasPreviouslyDispensedToTheThread(resource);
        dispensedResource.remove(Thread.currentThread());
        resources.add(resource);
    }

    private void assertResourceWasPreviouslyDispensedToTheThread(T resource) {
        T previouslyDispensedResource = dispensedResource.get(Thread.currentThread());
        if (previouslyDispensedResource == null) {
            throw new Error("Warning. Thread " + Thread.currentThread() + " does not hold any resource");
        }
        if (!previouslyDispensedResource.equals(resource)) {
            throw new Error("The released resource " + resource + " does not match the resource previously dispensed to the thread" + previouslyDispensedResource);
        }
    }


}
