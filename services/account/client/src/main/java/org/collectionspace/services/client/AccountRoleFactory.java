/**
 *  This document is a part of the source code and related artifacts
 *  for CollectionSpace, an open source collections management system
 *  for museums and related institutions:

 *  http://www.collectionspace.org
 *  http://wiki.collectionspace.org

 *  Copyright 2009 University of California at Berkeley

 *  Licensed under the Educational Community License (ECL), Version 2.0.
 *  You may not use this file except in compliance with this License.

 *  You may obtain a copy of the ECL 2.0 License at

 *  https://source.collectionspace.org/collection-space/LICENSE.txt

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.collectionspace.services.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.collectionspace.services.account.AccountsCommon;
import org.collectionspace.services.authorization.AccountRole;
import org.collectionspace.services.authorization.AccountValue;
import org.collectionspace.services.authorization.SubjectType;

import org.collectionspace.services.authorization.RoleValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 
 */
public class AccountRoleFactory {
    static private final Logger logger = LoggerFactory.getLogger(AccountRoleFactory.class);

 /**
     * Creates the account role instance.
     *
     * @param accountValue the pv
     * @param roleValueList the rvs
     * @param useAccountId the use perm id
     * @param useRoleId the use role id
     * @return the account role
     */
    static public AccountRole createAccountRoleInstance(AccountValue accountValue,
            Collection<RoleValue> roleValueList,
            boolean useAccountId,
            boolean useRoleId) {

        AccountRole accRole = new AccountRole();
        //service consume is not required to provide subject as it is determined
        //from URI used
        accRole.setSubject(SubjectType.ROLE);
        if (useAccountId) {
            ArrayList<AccountValue> pvs = new ArrayList<AccountValue>();
            pvs.add(accountValue);
            accRole.setAccount(pvs);
        }
        if (useRoleId) {
            //FIXME is there a better way?
            ArrayList<RoleValue> rvas = new ArrayList<RoleValue>();
            for (RoleValue rv : roleValueList) {
                rvas.add(rv);
            }
            accRole.setRole(rvas);
        }

        return accRole;
    }

    /*
     * Convert the Account service's RoleValue list to the AuthZ RoleValue list we need
     */
    static public AccountRole createAccountRoleInstance(AccountsCommon accountsCommon,
            Collection<org.collectionspace.services.account.RoleValue> roleValueList,
            boolean useAccountId,
            boolean useRoleId) {
    	
    	Collection<RoleValue> authzRoleValueList = new ArrayList<RoleValue>();
    	if (roleValueList != null && roleValueList.size() > 0) {
    		for (org.collectionspace.services.account.RoleValue rv : roleValueList) {
    			RoleValue authzRoleValue = new RoleValue();
    			authzRoleValue.setDisplayName(rv.getDisplayName());
    			authzRoleValue.setRoleId(rv.getRoleId());
    			authzRoleValue.setRoleName(rv.getRoleName());
    			authzRoleValue.setRoleRelationshipId(rv.getRoleRelationshipId());
    			authzRoleValue.setTenantId(rv.getTenantId());
    			authzRoleValueList.add(authzRoleValue);
    		}
    	}

		AccountValue accountValue = AccountFactory.createAccountValue(accountsCommon);
        return AccountRoleFactory.createAccountRoleInstance(accountValue, authzRoleValueList, useAccountId, useRoleId);
    }
    
    /**
     * Converts a standard RoleValue list to the type needed by the Account resource.
     * @param roleValueList
     * @return
     */
    static public List<org.collectionspace.services.account.RoleValue> convert(List<RoleValue> roleValueList) {
    	List<org.collectionspace.services.account.RoleValue> result = new ArrayList<org.collectionspace.services.account.RoleValue>();
    	
    	if (roleValueList != null && roleValueList.size() > 0) {
    		for (RoleValue rv : roleValueList) {
    			org.collectionspace.services.account.RoleValue accountRoleValue = new org.collectionspace.services.account.RoleValue();
    			accountRoleValue.setDisplayName(rv.getDisplayName());
    			accountRoleValue.setRoleId(rv.getRoleId());
    			accountRoleValue.setRoleName(rv.getRoleName());
    			accountRoleValue.setRoleRelationshipId(rv.getRoleRelationshipId());
    			accountRoleValue.setTenantId(rv.getTenantId());
    			result.add(accountRoleValue);
    		}
    	}
    	
    	return result;
    }
}
