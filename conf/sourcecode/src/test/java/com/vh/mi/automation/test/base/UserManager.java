package com.vh.mi.automation.test.base;

import com.vh.mi.automation.api.annotations.documentation.PackagePrivate;
import com.vh.mi.automation.api.annotations.documentation.Singleton;
import com.vh.mi.automation.api.annotations.documentation.ThreadSafe;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.resources.KeyedResources;
import com.vh.mi.automation.impl.resources.ResourcePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Dispenses Users to Tests that request them
 * <p/>
 * Relies on the thread safety of KeyedResource and ResourcePool
 * for its thread safety
 *
 * @author nimanandhar
 */
@ThreadSafe
@Singleton
@PackagePrivate
class UserManager {
    public static final String GROUPS_AND_USERS_FILE = "groupsAndUsers.groovy";
    private final KeyedResources<User> keyedUsers;
    private final ResourcePool<User> pooledUsers;

    @PackagePrivate
    static final UserManager INSTANCE = new UserManager();

    private UserManager() {
        List<User> users = getUsers();
        keyedUsers = new KeyedResources<>();
        pooledUsers = new ResourcePool<>();

        for (User user : users) {
            if (user.getAutomationId() == null) {
                pooledUsers.addResource(user);
            } else {
                keyedUsers.addResource(user.getAutomationId(), user);
            }
        }
    }

    public User getUser() {
        try {
            return pooledUsers.getResource();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public User getUser(String automationId) {
        try {
            return keyedUsers.getResource(automationId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void releaseUser(User user) {
        if (user.getAutomationId() == null) {
            pooledUsers.releaseResource(user);
        } else {
            keyedUsers.releaseResource(user.getAutomationId());
        }
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (com.vh.mi.automation.groovy.dsl.groupsandusers.User aUser : TestEnvironment.find.allUsers()) {
            User user = new User(aUser.getAutomationUserId(), aUser.getLoginName(), aUser.getPassword());
            user.addAssignedApplications(TestEnvironment.find.effectiveApplicationsAsssignedToUser(aUser));
            users.add(user);
        }
        return users;
    }
}
