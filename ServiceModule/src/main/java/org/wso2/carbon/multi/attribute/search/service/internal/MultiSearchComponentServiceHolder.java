package org.wso2.carbon.multi.attribute.search.service.internal;

import org.wso2.carbon.user.core.service.RealmService;

/**
 * Created by inshaf on 7/1/16.
 */
public class MultiSearchComponentServiceHolder {

    private static RealmService realmService;

    private MultiSearchComponentServiceHolder(){

    }

    public static RealmService getRealmService() {
        return realmService;
    }

    public static void setRealmService(RealmService realmService) {
        MultiSearchComponentServiceHolder.realmService = realmService;
    }
}
