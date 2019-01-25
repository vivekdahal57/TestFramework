package com.vh.mi.automation.test.base;

import com.vh.mi.automation.api.dto.SFW;
import com.vh.mi.automation.groovy.dsl.groupsandusers.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Will be used by tests to get information about users/groups/issuers
 * listed in groupsAndUser script for assertions
 *
 * @author nimanandhar
 */
public class TestEnvironment {

    public static final Finder find = new TestEnvironment.Finder();

    private TestEnvironment() {
    }

    public static class Finder {
        GroupsAndUsersScript groupAndUserDefinitions;
        List<User> allUsers = new ArrayList<>();
        private Map<String, User> idUserMap = new HashMap<>();
        private Map<String, Issuer> idIssuerMap = new HashMap<>();
        private Map<String, Group> idGroupMap = new HashMap<>();

        private Finder() {
            Binding binding = new Binding();
            binding.setVariable("mode", "test");
            String scriptName = System.getProperty("script");
            if (scriptName == null) {
                scriptName = "groupsAndUsers.groovy";
            }
            try {
                InputStream is = User.class.getClassLoader().getResourceAsStream(scriptName);
                Reader reader = new InputStreamReader(is);
                groupAndUserDefinitions = (GroupsAndUsersScript) new GroovyShell(binding).evaluate(reader);
                populateUserMap(groupAndUserDefinitions);
                populateIssuerMap(groupAndUserDefinitions);
                populateGroupMap(groupAndUserDefinitions);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        public Set<String> effectiveApplicationsAsssignedToUser(User user) {
            return groupAndUserDefinitions.getAssignedApplications(user);
        }

        //todo this must be refactored and validated
        public SFW sfwByUserAndApplication(String automationUserId, String appId) {
            List<App> applications = userById(automationUserId).getApplications();
            for (App application : applications) {
                if (application.getAppId().equals(appId)) {
                    if (application.getSfw() == null) {
                        return null;
                    } else {
                        SFW sfw = new SFW();
                        sfw.setLevelSfwMap(application.getSfw().getLevelDescriptions());
                        sfw.setAllow(application.getSfw().getAllow());
                        sfw.setExpectedResultsMap(application.getSfw().getExpectedResultsMap());
                        return sfw;
                    }
                }
            }
            throw new Error("AppId " + appId + " not assigned to user " + automationUserId); //todo also consider group
        }
        //todo this must be refactored and validated
        public SFW sfwByGroupAndApplication(String automationGroupId, String appId) {
            List<App> applications = groupById(automationGroupId).getApplications();
            for (App application : applications) {
                if (application.getAppId().equals(appId)) {
                    if (application.getSfw() == null) {
                       throw new Error(""); //todo provide message and throw
                    } else {
                        SFW sfw = new SFW();
                        sfw.setLevelSfwMap(application.getSfw().getLevelDescriptions());
                        sfw.setAllow(application.getSfw().getAllow());
                        sfw.setExpectedResultsMap(application.getSfw().getExpectedResultsMap());
                        return sfw;
                    }
                }
            }
            throw new Error("AppId " + appId + " not assigned to user " + automationGroupId); //todo also consider group
        }

        public List<User> allUsers() {
            return allUsers;
        }

        public User userById(String automationUserId) {
            if (!idUserMap.containsKey(automationUserId)) {
                throw new Error("No such user with automationUserId " + automationUserId);
            }
            return idUserMap.get(automationUserId);
        }

        public Issuer issuerById(String automationIssuerId) {
            if (!idIssuerMap.containsKey(automationIssuerId)) {
                throw new Error("No such issuer " + automationIssuerId);
            }
            return idIssuerMap.get(automationIssuerId);
        }


        public Group groupById(String automationGroupId) {
            if (!idGroupMap.containsKey(automationGroupId)) {
                throw new Error("No such group with automationGroupId " + automationGroupId);
            }
            return idGroupMap.get(automationGroupId);
        }


        //Helper methods
        private void populateUserMap(GroupsAndUsersScript groupAndUserDefinitions) {
            for (User user : groupAndUserDefinitions.getAllUsers()) {
                if (user.getAutomationUserId() != null) {
                    idUserMap.put(user.getAutomationUserId(), user);
                }
                allUsers.add(user);
            }
        }

        private void populateIssuerMap(GroupsAndUsersScript groupAndUserDefinitions) {
            idIssuerMap.clear();
            for (Issuer issuer : groupAndUserDefinitions.getIssuers().allIssuers()) {
                idIssuerMap.put(issuer.getAutomationIssuerId(), issuer);
            }
        }

        private void populateGroupMap(GroupsAndUsersScript groupAndUserDefinitions) {
            for (Group group : groupAndUserDefinitions.getGroups().allGroups()) {
                if (group.getAutomationGroupId() != null) {
                    idGroupMap.put(group.getAutomationGroupId(), group);
                }
            }
        }
    }
}
