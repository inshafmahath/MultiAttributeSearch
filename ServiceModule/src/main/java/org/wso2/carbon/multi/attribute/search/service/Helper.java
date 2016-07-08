package org.wso2.carbon.multi.attribute.search.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by inshaf on 7/5/16.
 */
public class Helper {

    public static String[] combineUserList(String[] userList1, String[] userList2){
        int length = userList1.length + userList2.length;
        String[] result = new String[length];
        System.arraycopy(userList1, 0, result, 0, userList1.length);
        System.arraycopy(userList2, 0, result, userList1.length, userList2.length);
        return result;
    }


    public static String[] getDistinctUserList(String[] userList){
        String[] uniqueUserList = new HashSet<String>(Arrays.asList(userList)).toArray(new String[0]);
        return uniqueUserList;
    }

    public static String[] retainCommonWithBase(String[] List1, String[] List2) {
        List<String> baseList = new ArrayList<String>(Arrays.asList(List1));
        List<String> compareList = new ArrayList<String>(Arrays.asList(List2));
        baseList.retainAll(compareList);

        String[] userArray = new String[baseList.size()];
        userArray = baseList.toArray(userArray);

        return userArray;
    }

    public static String[] removeCommonWithBase(String[] List1, String[] List2) {
        List<String> baseList = new ArrayList<String>(Arrays.asList(List1));
        List<String> compareList = new ArrayList<String>(Arrays.asList(List2));
        baseList.removeAll(compareList);

        String[] userArray = new String[baseList.size()];
        userArray = baseList.toArray(userArray);

        return userArray;
    }




}
