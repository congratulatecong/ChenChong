
package com.cong.chenchong.bean;

import java.util.ArrayList;

public class CallshowExtItem {
	public int id = -1;

    public String clientType = "clientType";

    public String clientName = "clientName";

    public int operateType = -1;

    public String iconUrl;

    /**
     * 子菜单
     */
    public ArrayList<CallshowExtItem> children = new ArrayList<CallshowExtItem>();

    public CallshowExtItem(int id, String clientType, String clientName, int operateType, String iconUrl) {
        super();
        this.id = id;
        this.clientType = clientType;
        this.clientName = clientName;
        this.operateType = operateType;
        this.iconUrl = iconUrl;
    }

}
