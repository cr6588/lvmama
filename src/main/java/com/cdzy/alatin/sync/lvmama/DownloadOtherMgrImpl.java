package com.cdzy.alatin.sync.lvmama;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdzy.alatin.sync.SyncMsgAckHeader;
import com.cdzy.alatin.sync.SyncMsgHeader;
import com.cdzy.alatin.sync.SyncService;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.SyncStatus;
import com.cdzy.alatin.sync.entities.base.DownCity;
import com.cdzy.alatin.sync.entities.base.DownLine;
import com.cdzy.alatin.sync.entities.base.DownMLayerSpec;
import com.cdzy.alatin.sync.entities.base.DownSLayerSpec;
import com.cdzy.alatin.sync.entities.base.DownSpot;
import com.cdzy.alatin.sync.entities.base.SingleData;
import com.cdzy.alatin.sync.entities.download.other.DlAllowLineTypeReq;
import com.cdzy.alatin.sync.entities.download.other.DlAllowLineTypeRes;
import com.cdzy.alatin.sync.entities.download.other.DlDaysFromCitysReq;
import com.cdzy.alatin.sync.entities.download.other.DlDaysFromCitysRes;
import com.cdzy.alatin.sync.entities.download.other.DlDaysSpotsReq;
import com.cdzy.alatin.sync.entities.download.other.DlDaysSpotsRes;
import com.cdzy.alatin.sync.entities.download.other.DlDaysToCitysReq;
import com.cdzy.alatin.sync.entities.download.other.DlDaysToCitysRes;
import com.cdzy.alatin.sync.entities.download.other.DlFromCitysReq;
import com.cdzy.alatin.sync.entities.download.other.DlFromCitysRes;
import com.cdzy.alatin.sync.entities.download.other.DlLineReq;
import com.cdzy.alatin.sync.entities.download.other.DlLineRes;
import com.cdzy.alatin.sync.entities.download.other.DlMLayerSpecReq;
import com.cdzy.alatin.sync.entities.download.other.DlMLayerSpecRes;
import com.cdzy.alatin.sync.entities.download.other.DlSLayerSpecReq;
import com.cdzy.alatin.sync.entities.download.other.DlSLayerSpecRes;
import com.cdzy.alatin.sync.entities.download.other.DlSpotsReq;
import com.cdzy.alatin.sync.entities.download.other.DlSpotsRes;
import com.cdzy.alatin.sync.entities.download.other.DlToCitysReq;
import com.cdzy.alatin.sync.entities.download.other.DlToCitysRes;
import com.cdzy.alatin.sync.entities.download.other.DlWayCitysReq;
import com.cdzy.alatin.sync.entities.download.other.DlWayCitysRes;
import com.cdzy.alatin.sync.svc_api.DownloadOtherMgr;
import com.cdzy.alatin.sync.tools.ChineseToPinYin;
import com.cdzy.alatin.sync.tools.HttpUtil;
import com.cdzy.alatin.sync.tools.SyncAckHeaderUtil;
import com.cdzy.alatin.sync.tools.SyncTools;

/**
 * 金熊猫下载其它
 */
public class DownloadOtherMgrImpl implements DownloadOtherMgr {
    private static final Logger logger = LoggerFactory.getLogger(DownloadOtherMgrImpl.class);

    private LoginServiceImpl jinxiongmaoLoginService = new LoginServiceImpl();
    private DownloadOtherServiceImpl downloadOtherService = new DownloadOtherServiceImpl();

    /**
     * 下载出发城市
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlFromCitysReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2003, name = "下载出发城市")
    public DlFromCitysRes dlFromCities(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlFromCitysReq dlFromCitysReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlFromCitysRes dlFromCitysRes = new DlFromCitysRes();
        List<DownCity> downCities = new ArrayList<DownCity>();
        dlFromCitysRes.setDownCities(downCities);
        dlFromCitysRes.setIsLast(1);
        dlFromCitysRes.setDownTime(new Date());
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlFromCitysRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无出发城市", logger, true);
        return dlFromCitysRes;
    }

    /**
     * 下载目的城市
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlToCitysReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2004, name = "下载目的城市")
    public DlToCitysRes dlToCities(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlToCitysReq dlToCitysReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlToCitysRes dlToCitysRes = new DlToCitysRes();
        dlToCitysRes.setIsLast(1);
        dlToCitysRes.setDownTime(new Date());
        List<DownCity> downCities = new ArrayList<DownCity>();
        dlToCitysRes.setDownCities(downCities);
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlToCitysRes;
        }
//        dlToCitysRes.setDownCities(downloadOtherService.getDownCityInDestination(session, syncMsgAckHeader));
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "下载目的城市成功", logger, true);
        dlToCitysRes.setIsLast(1);
        return dlToCitysRes;
    }

    /**
     * 下载每天的出发城市
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param daysFromCitysReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2005, name = "下载每天的出发城市")
    public DlDaysFromCitysRes dlDaysFromCities(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlDaysFromCitysReq daysFromCitysReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlDaysFromCitysRes dlDaysFromCitysRes = new DlDaysFromCitysRes();
        List<DownCity> downCities = new ArrayList<DownCity>();
        dlDaysFromCitysRes.setDownCities(downCities);
        dlDaysFromCitysRes.setIsLast(1);
        dlDaysFromCitysRes.setDownTime(new Date());
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlDaysFromCitysRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无每天的目的城市", logger, true);
        return dlDaysFromCitysRes;
    }

    /**
     * 下载每天的目的城市
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param daysToCitysReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2006, name = "下载每天的目的城市")
    public DlDaysToCitysRes dlDaysToCities(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlDaysToCitysReq daysToCitysReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlDaysToCitysRes dlDaysToCitysRes = new DlDaysToCitysRes();
        List<DownCity> downCities = new ArrayList<DownCity>();
        dlDaysToCitysRes.setDownCities(downCities);
        dlDaysToCitysRes.setIsLast(1);
        dlDaysToCitysRes.setDownTime(new Date());
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlDaysToCitysRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无每天的目的城市", logger, true);
        return dlDaysToCitysRes;
    }

    /**
     * 下载景点
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlSpotsReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2007, name = "下载景点")
    public DlSpotsRes dlSpots(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlSpotsReq dlSpotsReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlSpotsRes dlSpotsRes = new DlSpotsRes();
        List<DownSpot> downSpots = new ArrayList<DownSpot>();
        dlSpotsRes.setDownSpots(downSpots);
        dlSpotsRes.setIsLast(1);
        dlSpotsRes.setDownTime(new Date());
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlSpotsRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无景点", logger, true);
        return dlSpotsRes;
    }

    /**
     * 下载每天的景点
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param daysSpotsReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2008, name = "下载每天的景点")
    public DlDaysSpotsRes dlDaysSpots(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlDaysSpotsReq daysSpotsReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlDaysSpotsRes dlDaysSpotsRes = new DlDaysSpotsRes();
        List<DownSpot> downSpots = new ArrayList<DownSpot>();
        dlDaysSpotsRes.setDownSpots(downSpots);
        dlDaysSpotsRes.setIsLast(1);
        dlDaysSpotsRes.setDownTime(new Date());
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlDaysSpotsRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无每天的景点", logger, true);
        return dlDaysSpotsRes;
    }

    /**
     * 下载账号允许发布的线路类型
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlAllowLineTypeReq
     * @see 该函数暂时不由服务组调用，需要接入组登录账号成功后自行下载并写入消息队列通知服务组
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2009, name = "下载账号允许发布的线路类型")
    public DlAllowLineTypeRes dlAllowLineType(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlAllowLineTypeReq dlAllowLineTypeReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlAllowLineTypeRes dlAllowLineTypeRes = new DlAllowLineTypeRes();
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlAllowLineTypeRes;
        }
        String routeType = downloadOtherService.getCanPublishRouteType(session, syncMsgAckHeader);
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "下载账号允许发布的线路类型成功", logger, true);
        dlAllowLineTypeRes.setRouteType(routeType);
        return dlAllowLineTypeRes;
    }

    /**
     * 下载线路
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlLineReq
     * @return
     */
    @Override
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2010, name = "下载线路")
    public DlLineRes dlLine(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlLineReq dlLineReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlLineRes dlLineRes = new DlLineRes();
        dlLineRes.setIsLast(1);
        dlLineRes.setDownTime(new Date());
        List<DownLine> downLines = new ArrayList<DownLine>();
        dlLineRes.setDownLines(downLines);
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlLineRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无下载线路", logger, true);
        return dlLineRes;
    }

    /**
     * 下载途径城市
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlWayCitysReq
     * @return
     */
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2012, name = "下载途径城市")
    public DlWayCitysRes dlWayCities(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlWayCitysReq dlWayCitysReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlWayCitysRes dlWayCitysRes = new DlWayCitysRes();
        dlWayCitysRes.setIsLast(1);
        dlWayCitysRes.setDownTime(new Date());
        List<DownCity> downCities = new ArrayList<DownCity>();
        dlWayCitysRes.setDownCities(downCities);
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlWayCitysRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无途径城市", logger, true);
        return dlWayCitysRes;
    }

    /**
     * 下载特殊项(单层数据)
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlSLayerSpecReq
     * @return
     */
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2013, name = "下载特殊项(单层数据)")
    public DlSLayerSpecRes dlSLayerSpec(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlSLayerSpecReq dlSLayerSpecReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlSLayerSpecRes dlSLayerSpecRes = new DlSLayerSpecRes();
        dlSLayerSpecRes.setIsLast(1);
        dlSLayerSpecRes.setDownTime(new Date());
        List<DownSLayerSpec> downSLayerSpecs = new ArrayList<DownSLayerSpec>();
        dlSLayerSpecRes.setDownSLayerSpecs(downSLayerSpecs);
        DownSLayerSpec downSLayerSpec = new DownSLayerSpec();
        downSLayerSpecs.add(downSLayerSpec);
        List<SingleData> singleDatas = new ArrayList<SingleData>();
        downSLayerSpec.setSingleDatas(singleDatas);
        downSLayerSpec.setMenuId(3440);
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlSLayerSpecRes;
        }
//        try {
//            String res = HttpUtil.getGetHttpResStr(session, SiteInfo.URLGETADDROUTE, false);
//            Document document = Jsoup.parse(res);
//            Elements modes = document.select("input[name=\"mode\"]"); //出团类型
//            for (int i = 0; i < modes.size(); i++) {
//                SingleData singleData = new SingleData();
//                singleData.setId(modes.get(i).attr("ng-value"));
//                singleData.setName(modes.get(i).parent().text());
//                singleData.setFullSpell(ChineseToPinYin.getPingYin(singleData.getName()));
//                singleData.setSimpleSpell(ChineseToPinYin.getPinYinHeadChar(singleData.getName()));
//                singleDatas.add(singleData);
//            }
//        } catch (Exception e) {
//            SyncTools.printErrorLog(logger, syncMsgAckHeader, "解析出团类型出错");
//            e.printStackTrace();
//            SyncAckHeaderUtil.setUnknown(syncMsgAckHeader, "解析出团类型出错" + e.getMessage(), logger, true);
//            return dlSLayerSpecRes;
//        }
//        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "下载出团类型成功", logger, true);
        return dlSLayerSpecRes;
    }

    /**
     * 下载特殊项(多层数据)
     * @param session
     * @param syncMsgHeader
     * @param syncMsgAckHeader
     * @param dlMLayerSpecReq
     * @return
     */
    @SyncService(siteId = SiteInfo.SITE_ID, opCode = 2014, name = "下载特殊项(多层数据)")
    public DlMLayerSpecRes dlMLayerSpec(SyncSession session, SyncMsgHeader syncMsgHeader, SyncMsgAckHeader syncMsgAckHeader, DlMLayerSpecReq dlMLayerSpecReq) {
        SyncAckHeaderUtil.copySyncMsgHeader(syncMsgHeader, syncMsgAckHeader);
        DlMLayerSpecRes dlMLayerSpecRes = new DlMLayerSpecRes();
        dlMLayerSpecRes.setIsLast(1);
        dlMLayerSpecRes.setDownTime(new Date());
        List<DownMLayerSpec> downMLayerSpecs = new ArrayList<DownMLayerSpec>();
        dlMLayerSpecRes.setDownMLayerSpecs(downMLayerSpecs);
        if (!jinxiongmaoLoginService.checkLoginStatus(session).equals(SyncStatus.OK)) {
            SyncAckHeaderUtil.setSesTimeout(syncMsgAckHeader, "账号登录已失效，请重新登录", logger, true);
            return dlMLayerSpecRes;
        }
        SyncAckHeaderUtil.setSuccess(syncMsgAckHeader, "无多层特殊项", logger, true);
        return dlMLayerSpecRes;
    }
}
