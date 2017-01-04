package com.cdzy.alatin.sync.lvmama;

import org.junit.Test;

import com.cdzy.alatin.sync.SyncMsgAckHeader;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.lvmama.service.impl.DlOtherSerImpl;
import com.cdzy.alatin.sync.tools.cookie.CookieUtil;

public class DlOtherSerTest {

    SyncSession session = CookieUtil.readCookie("CDCJ-HYJQ", "hyjq@666", SiteInfo.URL_GET_VERIFY_CODE(), new LoginSerImpl());

    DlOtherSerImpl dlOtherSer = new DlOtherSerImpl();

    @Test
    public void getCanPublishRouteTypeTest() {
        SyncMsgAckHeader syncMsgAckHeader = new SyncMsgAckHeader();
        try {
            String routeType = dlOtherSer.getCanPublishRouteType(session, syncMsgAckHeader);
            System.out.println(routeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
