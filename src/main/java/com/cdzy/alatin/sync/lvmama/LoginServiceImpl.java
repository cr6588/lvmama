package com.cdzy.alatin.sync.lvmama;

import java.util.List;

import org.apache.http.cookie.Cookie;

import com.cdzy.alatin.sync.LoginBeforeReturn;
import com.cdzy.alatin.sync.LoginReturn;
import com.cdzy.alatin.sync.LoginService;
import com.cdzy.alatin.sync.Site;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.buiness.SiteInfo;
import com.cdzy.alatin.sync.business.base.LoginServiceBase;

@Site(id = SiteInfo.SITE_ID, verifyCode = true, name = "驴妈妈")
public class LoginServiceImpl extends LoginServiceBase implements LoginService {

    @Override
    public LoginBeforeReturn loginBefore(SyncSession arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cookie> modifyCookes(SyncSession arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LoginReturn loginHttp(SyncSession session, String loginName, String password, String verifyCode, String others) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void checkLoginParam(String loginName, String password, String verifyCode, String others) throws UserPwdException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String logoffHttp(SyncSession session) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String checkLoginStatus(SyncSession session) {
        // TODO Auto-generated method stub
        return null;
    }


}
