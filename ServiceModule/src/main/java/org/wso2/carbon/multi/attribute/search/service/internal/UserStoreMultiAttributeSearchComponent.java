package org.wso2.carbon.multi.attribute.search.service.internal;

/**
 * Created by inshaf on 6/30/16.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.multi.attribute.search.service.CustomUserStoreManager;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * @scr.component name="org.wso2.carbon.multi.attribute.search.service.CustomUserStoreManager" immediate=true
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 */
public class UserStoreMultiAttributeSearchComponent {


    private static Log log = LogFactory.getLog(UserStoreMultiAttributeSearchComponent.class);

    protected void activate(ComponentContext ctxt) {

        try {
            CustomUserStoreManager customUserStoreManager = new CustomUserStoreManager();
            ctxt.getBundleContext().registerService(CustomUserStoreManager.class.getName(), customUserStoreManager, null);

        } catch (UserStoreException e) {
            log.error("Failed to activate CustomUserStoreManager");
        }

        log.info("CustomUserStoreManager bundle activated successfully..");

    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("CustomUserStoreManager  bundle is deactivated ");
        }
    }

    protected void setRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service for MultiAttribute Search");
        }
        MultiSearchComponentServiceHolder.setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.debug("Unsetting the Realm Service for MultiAttribute Search");
        }
        //MultiSearchComponentServiceHolder.setRealmService(null);
    }




}
