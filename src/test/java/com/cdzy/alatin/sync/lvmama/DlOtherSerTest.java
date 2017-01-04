package com.cdzy.alatin.sync.lvmama;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
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

    @Test
    public void pathTest() {
            try {
//                JarFile xmlFile = new JarFile("D:/alatin2.4/alatin-sync-node-0.0.1/lib/alatin-sync-lvmama-0.0.1.jar");
//                
//                Enumeration<JarEntry> Enumeration = xmlFile.entries();
//                for (;Enumeration.hasMoreElements();) {
//                    JarEntry entry = Enumeration.nextElement();
//                    System.out.println(entry.);
//                }
//                JarEntry jarEntry = new JarEntry(xmlFile.getEntry("com/cdzy/alatin/sync/lvmama/http/"));
//                xmlFile.
//                System.out.println();
//                jarEntry.
//                URL url = new URL("");
                System.out.println(JSON.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//                url.toURI();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
