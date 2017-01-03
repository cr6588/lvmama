package com.cdzy.alatin.sync.lvmama;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdzy.alatin.sync.LoginBeforeReturn;
import com.cdzy.alatin.sync.LoginReturn;
import com.cdzy.alatin.sync.LoginService;
import com.cdzy.alatin.sync.Site;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.SyncStatus;
import com.cdzy.alatin.sync.TimeSpan;
import com.cdzy.alatin.sync.business.base.LoginServiceBase;
import com.cdzy.alatin.sync.tools.HttpUtil;
import com.cdzy.alatin.sync.tools.SyncAckHeaderUtil;
import com.cdzy.alatin.sync.tools.SyncTools;

@Site(id = SiteInfo.SITE_ID, verifyCode = true, name = SiteInfo.SITE_NAME)
public class LoginSerImpl extends LoginServiceBase implements LoginService {

    private final static Logger logger = LoggerFactory.getLogger(LoginSerImpl.class);

    @Override
    public LoginBeforeReturn loginBefore(SyncSession session) {
        SyncTools.printInfoLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "获取验证码");
        session.clearCookies();
        return new LoginBeforeReturn(SyncStatus.OK, SiteInfo.URL_GET_VERIFY_CODE(), null);
    }

    @Override
    public List<Cookie> modifyCookes(SyncSession session) {
        SyncTools.printErrorLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "modifyCookes");
        return null;
    }

    @Override
    public LoginReturn loginHttp(SyncSession session, String loginName, String password, String verifyCode, String others) throws Exception {
        List<BasicNameValuePair> loginParam = new ArrayList<BasicNameValuePair>();
        loginParam.add(new BasicNameValuePair("userName", loginName));
        loginParam.add(new BasicNameValuePair("password", password));
        loginParam.add(new BasicNameValuePair("validateCode", verifyCode));
        loginParam.add(new BasicNameValuePair("authType", "Y"));
        String loginResponseStr = HttpUtil.responseEntity2Str(HttpUtil.excuteHttpPost(session, SiteInfo.URL_LOGIN, loginParam));;
        if (null != loginResponseStr && !"".equals(loginResponseStr.trim())) {
            Document doc = Jsoup.parse(loginResponseStr);
            if(doc.select("#welcome a").attr("href").contains("/vst_ebooking/editPwd")) {
                return new LoginReturn(SyncStatus.OK, "");
            }
        }
        String msg = null != loginResponseStr  ? loginResponseStr : "";
        SyncTools.printErrorLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "登陆失败:" + msg);
        return new LoginReturn(SyncStatus.USER_PWD_ERROR, msg);
    }

    @Override
    public void checkLoginParam(String loginName, String password, String verifyCode, String others) throws UserPwdException {
        // 数据验证
        if (StringUtil.isBlank(loginName)) {
            throw new UserPwdException("请输入账号");
        }
        if (StringUtil.isBlank(password)) {
            throw new UserPwdException("请输入密码！");
        }
        if (StringUtil.isBlank(verifyCode)) {
            throw new UserPwdException("请输入验证码");
        }
    }

    @Override
    public String logoffHttp(SyncSession session) throws Exception {
        HttpUtil.getGetHttpResStr(session, SiteInfo.URL_LOGIN_OUT, false);
        SyncTools.printInfoLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "退出登录:OK");
        return SyncStatus.OK;
    }

    @Override
    @TimeSpan(180)
    public String checkLoginStatus(SyncSession session) {
        SyncTools.printInfoLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "检查登录状态");
        String res = null;
        try {
            res = HttpUtil.getPostHttpResStr(session, SiteInfo.URL_CHECK_LOGIN, false, null);
            if (null != res && !"".equals(res.trim())) {
                Document document = Jsoup.parse(res);
                if (null != document.select("form") && document.select("form").get(0).attr("action").contains("change_pwd")) {
                    SyncTools.printCheckLoginRes(logger, session, res, SyncStatus.OK);
                    return SyncStatus.OK;
                }
            }
            SyncTools.printCheckLoginRes(logger, session, res, SyncStatus.SES_TIMEOUT);
            return SyncStatus.SES_TIMEOUT;
        } catch (Exception e) {
            SyncTools.printCheckLoginRes(logger, session, res, SyncStatus.SES_TIMEOUT);
            SyncTools.printErrorLog(logger, SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0), "检查登陆异常:" + e.getMessage(), e);
            return SyncStatus.SES_TIMEOUT;
        }
    }


}
