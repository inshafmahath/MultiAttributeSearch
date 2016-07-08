package org.wso2.carbon.multi.attribute.search.service;

/*
 * Created by inshaf on 6/30/16.
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.multi.attribute.search.service.beans.UserListAttributes;
import org.wso2.carbon.multi.attribute.search.service.internal.MultiSearchComponentServiceHolder;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.Arrays;

public class CustomUserStoreManager {


    private static Log log = LogFactory.getLog(CustomUserStoreManager.class);
    private static String filter = "*";
    private static int maxItemLimit = -1;
    RealmConfiguration realmConfig;

    public CustomUserStoreManager() throws UserStoreException {


        try {
            UserRealm userRealm = (UserRealm) CarbonContext.getThreadLocalCarbonContext().getUserRealm();
            realmConfig = userRealm.getRealmConfiguration();
        } catch (UserStoreException e) {
            throw new UserStoreException(
                    "Failed to initiate User Realm from Carbon Context");
        }
    }


    public String[] getMultiAttributeUserList(String[] claims, String[] claimValues, String[] profileNames) throws UserStoreException {

        String[] allUsers = null;

        if (claims.length != claimValues.length || claims.length != profileNames.length) {
            throw new UserStoreException(
                    "Claims, claim values and profile names consist of invalid length");
        } else {

            if (claims == null) {
                claims = new String[0];
            }

            if (claimValues == null) {
                claimValues = new String[0];
            }

            if (profileNames == null) {
                profileNames = new String[0];
            }


            int claimListLen = claims.length;
            for (int i = 0; i < claimListLen; i++) {
                if (log.isDebugEnabled()) {
                    log.debug("Claim Names: " + claims[i] + " Claim value: " + claimValues[i]);
                }

                try {
                    RealmService realmService = MultiSearchComponentServiceHolder.getRealmService();

                    String[] userArray;

                    if ("null".equals(profileNames[i]) || "".equals(profileNames[i])) {
                        userArray = realmService.getUserRealm(realmConfig).getUserStoreManager().getUserList(claims[i], claimValues[i], null);
                    } else {
                        userArray = realmService.getUserRealm(realmConfig).getUserStoreManager().getUserList(claims[i], claimValues[i], profileNames[i]);
                    }

                    if (userArray.length == 0) {
                        allUsers = null;
                        break;
                    } else {
                        if (allUsers == null) {
                            allUsers = userArray;
                        } else {
                            allUsers = Helper.retainCommonWithBase(allUsers, userArray);
                        }

                    }


                    if (log.isDebugEnabled()) {
                        log.debug("UserList: " + Arrays.toString(allUsers));
                    }


                } catch (UserStoreException e) {
                    throw new UserStoreException(
                            "Error occurred while getting user list for. " + "Claim Name: " + claims[i] + " Claim value: " + claimValues[i]);
                }


            }
        }


        return allUsers;
    }

    public UserListAttributes getMultiAttributeObject(String[] claims, String[] claimValues, String[] profileNames, int startAt, int maxResults) throws UserStoreException {

        String[] allUsers = null;

        if (claims.length != claimValues.length || claims.length != profileNames.length) {
            throw new UserStoreException(
                    "Claims, claim values and profile names consist of invalid length");
        } else if (startAt < 0 || maxResults < 0) {
            throw new UserStoreException(
                    "Missing startAt or maxResults values");
        } else {

            if (claims == null) {
                claims = new String[0];
            }

            if (claimValues == null) {
                claimValues = new String[0];
            }

            if (claimValues == null) {
                claimValues = new String[0];
            }


            try {
                allUsers = getMultiAttributeUserList(claims, claimValues, profileNames);

                if (log.isDebugEnabled()) {
                    log.debug("UserList: " + Arrays.toString(allUsers));
                }


            } catch (UserStoreException e) {
                throw new UserStoreException(e.getMessage());
            }


        }

        int total = allUsers.length;

        if (total<(startAt+maxResults)){
            allUsers = Arrays.copyOfRange(allUsers, startAt, total);
        }else{
            allUsers = Arrays.copyOfRange(allUsers, startAt, (startAt+maxResults));
        }


        UserListAttributes userListAttr = new UserListAttributes();
        userListAttr.setStartAt(startAt);
        userListAttr.setMaxResults(maxResults);
        userListAttr.setTotal(total);
        userListAttr.setUserList(allUsers);

        return userListAttr;
    }


    public UserListAttributes getMultiAttributeObjectWithFilter(String[] filterClaims, String[] filterClaimValues, String[] filterProfileNames, int startAt, int maxResults) throws UserStoreException {

        String[] allUsers = null;

        if (filterClaims.length != filterClaimValues.length || filterClaims.length != filterProfileNames.length) {
            throw new UserStoreException(
                    "Filter Claims, claim values and profile names consist of invalid length");
        } else if (startAt < 0 || maxResults < 0) {
            throw new UserStoreException(
                    "Missing startAt or endAt values");
        } else {

            if (filterClaims == null) {
                filterClaims = new String[0];
            }

            if (filterClaimValues == null) {
                filterClaimValues = new String[0];
            }

            if (filterProfileNames == null) {
                filterProfileNames = new String[0];
            }


            try {

                String[] userArray = getUserList();

                String[] filterUserArray = getMultiAttributeUserList(filterClaims, filterClaimValues, filterProfileNames);

                allUsers = Helper.removeCommonWithBase(userArray, filterUserArray);

            } catch (UserStoreException e) {
                throw new UserStoreException(e.getMessage());
            }


        }


        int total = allUsers.length;

        if (total<(startAt+maxResults)){
            allUsers = Arrays.copyOfRange(allUsers, startAt, total);
        }else{
            allUsers = Arrays.copyOfRange(allUsers, startAt, (startAt+maxResults));
        }

        UserListAttributes userListAttr = new UserListAttributes();
        userListAttr.setStartAt(startAt);
        userListAttr.setMaxResults(maxResults);
        userListAttr.setTotal(total);
        userListAttr.setUserList(allUsers);

        return userListAttr;
    }


    public String[] getUserList() throws UserStoreException {

        String[] allUsers = null;

        try {
            RealmService realmService = MultiSearchComponentServiceHolder.getRealmService();

            allUsers = realmService.getUserRealm(realmConfig).getUserStoreManager().listUsers(filter, maxItemLimit);

            if (log.isDebugEnabled()) {
                log.debug("UserList: " + Arrays.toString(allUsers));
            }


        } catch (UserStoreException e) {
            throw new UserStoreException(
                    "Error occurred while getting user list");
        }

        return allUsers;
    }

}
