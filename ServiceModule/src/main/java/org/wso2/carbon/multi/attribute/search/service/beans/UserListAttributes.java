package org.wso2.carbon.multi.attribute.search.service.beans;

import java.io.Serializable;

/**
 * Created by inshaf on 7/7/16.
 */
public class UserListAttributes implements Serializable {
    private int startAt;
    private int maxResults;
    private int total;
    private String[] userList;

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String[] getUserList() {
        return userList;
    }

    public void setUserList(String[] userList) {
        this.userList = userList;
    }
}
