package com.vh.mi.automation.impl.resources;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.documentation.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * A Keyed Resource represents resource that have a key
 * to identify them, and that can be used by only one
 * thread at a time.
 * <p/>
 * If a thread attempts to acquire a resource held by another
 * thread it enters into a blocked state until the resource
 * is free.
 * If a thread tries to acquire a resource without releasing
 * previously held resource, an Error is thrown
 * If a thread tries to release a resource held by a different
 * thread, an exception is thrown
 *
 * @author nimanandhar
 */
@ThreadSafe
public class KeyedResources<T> {
    private static final int PERMITS_PER_RESOURCE = 1;
    private final Map<Thread, String> heldResources = new ConcurrentHashMap<>();
    private final Map<String, Semaphore> resourcePermits = new ConcurrentHashMap<>();
    private final Map<String, T> resources = new ConcurrentHashMap<>();

    public void addResource(String key, T resource) {
        Preconditions.checkNotNull(key, "Cannot add resource with null key");
        Preconditions.checkNotNull(resource, "Cannot add null resource");
        Preconditions.checkState(!resources.containsKey(key), "Duplicate resource " + key);

        resourcePermits.put(key, new Semaphore(PERMITS_PER_RESOURCE));
        resources.put(key, resource);
    }

    /**
     * Get a resource identified by the key
     *
     * @throws IllegalArgumentException if the specified resource does not exist
     * @throws Error                    if the calling thread already holds the same or different resource
     */
    public T getResource(String key) throws InterruptedException {
        Preconditions.checkNotNull(key);
        if (heldResources.containsKey(Thread.currentThread())) {
            throw new Error(String.format("The thread %s tried to acquire the resource %s without releasing previous resource %s", Thread.currentThread(), key, heldResources.get(Thread.currentThread())));
        }
        assertResourceExists(key);
        resourcePermits.get(key).acquire();
        heldResources.put(Thread.currentThread(), key);
        return resources.get(key);
    }

    /**
     * Release the resource back to the pool
     *
     * @throws IllegalArgumentException if the specified resource does not exist
     * @throws Error                    if the resource was not acquired previously by the calling thread
     */
    public void releaseResource(String key) {
        Preconditions.checkNotNull(key);
        assertResourceExists(key);

        String resourceId = heldResources.get(Thread.currentThread());
        if (resourceId == null)
            throw new Error("The current thread does not hold any resources. Cannot release the resource " + key);

        if (!resourceId.equals(key)) {
            throw new Error("The current thread does not hold the resource " + key + " Cannot release the resource");
        }

        resourcePermits.get(key).release();
        heldResources.remove(Thread.currentThread());
    }

    private void assertResourceExists(String key) {
        if (!resourcePermits.containsKey(key)) {
            throw new IllegalArgumentException("No resource found with key " + key);
        }
    }

}
