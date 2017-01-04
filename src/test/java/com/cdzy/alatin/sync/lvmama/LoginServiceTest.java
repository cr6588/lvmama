package com.cdzy.alatin.sync.lvmama;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.cdzy.alatin.sync.LoginReturn;
import com.cdzy.alatin.sync.SyncMsgAckHeader;
import com.cdzy.alatin.sync.SyncSession;
import com.cdzy.alatin.sync.SyncStatus;
import com.cdzy.alatin.sync.tools.SyncAckHeaderUtil;
import com.cdzy.alatin.sync.tools.TestSession;
import com.cdzy.alatin.sync.tools.UploadImg;

public class LoginServiceTest {

    /**
     * 登陆方法测试
     * 测试结果样例
     * 99007:session不存在
     * 99009:账号为空
     * 99009:密码为空
     * 99009:验证码为空
     * 99009:此次登录已经过期（网站返回的信息）实际情况是（没有访问过验证码链接直接登陆，就会出现此处提示)
     * 99009:验证码错误（网站返回的信息）
     * 请输入验证码：
     * ocav
     * 0:1703 + 1730
     * 请输入验证码：
     * wfyh
     * 99009:帐号/密码错误
     * 请输入验证码：
     * efpp
     * 99009:帐号/密码错误
     */
    @Test
    public void loginTest() {
        SyncSession session = null;

        LoginSerImpl loginService = new LoginSerImpl();

        String username = null;
        String password = null;
        String verifyCode = "";

        LoginReturn loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        session = new TestSession();
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        username = "CDCJ-HYJQ";
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        password = "hyjq@666";
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        verifyCode = "1111";
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        SyncMsgAckHeader syncMsgAckHeader = SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0);
        verifyCode = "1111";
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.OK, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        username = "xxxxxxxxx";     //错误账号
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

        username = "xiaoyaoyou";
        password = "123456";        //错误密码
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
//        scanner.close(); //scanner.close();关闭的是系统输入流一旦关闭其它地方无法再次输入
        loginReturn =  loginService.login(session, username, password, verifyCode, null);
        Assert.assertEquals(SyncStatus.USER_PWD_ERROR, loginReturn.getStatus());
        System.out.println(loginReturn.getStatus() + ":" + loginReturn.getErrorMessage());

    }

    /**
     * 检查登陆方法测试
     */
    @Test
    public void checkLoginStatusTest() {
        SyncSession session = new TestSession();
        LoginSerImpl loginService = new LoginSerImpl();
        SyncMsgAckHeader syncMsgAckHeader = SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0);
        Assert.assertEquals(SyncStatus.SES_TIMEOUT, loginService.checkLoginStatus(session));                //未登陆
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        String verifyCode = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
        scanner.close();
        LoginReturn loginReturn =  loginService.login(session, "CDCJ-HYJQ", "hyjq@666", verifyCode, null);
        Assert.assertEquals(SyncStatus.OK, loginReturn.getStatus());
        Assert.assertEquals(SyncStatus.OK, loginService.checkLoginStatus(session));                          //已登陆
    }

    @Test
    public void logoffTest() {
        SyncSession session = new TestSession();
        LoginSerImpl loginService = new LoginSerImpl();
        SyncMsgAckHeader syncMsgAckHeader = SyncAckHeaderUtil.createSyncMsgAckHeader(session, 0);
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        String verifyCode = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
        Assert.assertEquals(SyncStatus.OK, loginService.logoff(session));                //未登陆退出
        LoginReturn loginReturn = loginService.login(session, "CDCJ-HYJQ", "hyjq@666", verifyCode, null);
        Assert.assertEquals(SyncStatus.OK, loginReturn.getStatus());
        Assert.assertEquals(SyncStatus.OK, loginService.checkLoginStatus(session));
        Assert.assertEquals(SyncStatus.OK, loginService.logoff(session));                          //已登陆退出
        Assert.assertEquals(SyncStatus.SES_TIMEOUT, loginService.checkLoginStatus(session));
        UploadImg.GetVerifyimage(session, syncMsgAckHeader, SiteInfo.URL_GET_VERIFY_CODE());
        System.out.println("请输入验证码：");
        verifyCode = scanner.next();
        loginReturn = loginService.login(session, "CDCJ-HYJQ", "hyjq@666", verifyCode, null);
        Assert.assertEquals(SyncStatus.OK, loginService.checkLoginStatus(session));
    }
}
