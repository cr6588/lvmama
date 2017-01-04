package com.cdzy.alatin.sync.lvmama.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.cdzy.alatin.sync.SyncMsgAckHeader;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.business.util.MapperProxy;
import com.cdzy.alatin.sync.lvmama.http.DlOtherHttp;
import com.cdzy.alatin.sync.tools.Dictionary;

public class DlOtherSerImpl {

    private DlOtherHttp dlOtherHttp;
    public DlOtherSerImpl () {
        dlOtherHttp = MapperProxy.getHttpInstance(DlOtherHttp.class, dlOtherHttp);
    }
    /**
     * 获取允许发布的线路类型
     * @param session
     * @param syncMsgAckHeader
     * @return
     * @throws Exception 
     */
    public String getCanPublishRouteType(SyncSession session, SyncMsgAckHeader syncMsgAckHeader) throws Exception {
        String routeType = "";
        String res = dlOtherHttp.showAddProduct(session);
        Elements productTypes = Jsoup.parse(res).select("select[name=\"productType\"] option");
        
        if (productTypes != null) {
            for (Element productType : productTypes) {
                String productTypeText = productType.text();
                if(productTypeText.contains("短线")) {
                    routeType += Dictionary.RouteType.AROUND_TRIP + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.AROUND_TRIP + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                } else if (productTypeText.contains("长线")) {
                    routeType += Dictionary.RouteType.DEMONSTRIC_LONG_LINE + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.DEMONSTRIC_LONG_LINE + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                } else if (productTypeText.contains("出境/港澳台")) {
                    routeType += Dictionary.RouteType.OUTBOUND_TRAVEL + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.OUTBOUND_TRAVEL + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                    routeType += Dictionary.RouteType.HONG_KONG + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.HONG_KONG + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                    routeType += Dictionary.RouteType.MACAO + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.MACAO + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                    routeType += Dictionary.RouteType.TAIWAN + "+" + Dictionary.RouteNature.FROM_FOLLOW + ";";
                    routeType += Dictionary.RouteType.TAIWAN + "+" + Dictionary.RouteNature.DEST_FOLLOW + ";";
                }
            }
        }
    return routeType;
    }

}
