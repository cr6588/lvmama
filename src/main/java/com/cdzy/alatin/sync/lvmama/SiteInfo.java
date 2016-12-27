package com.cdzy.alatin.sync.lvmama;

public class SiteInfo {
    /**
     * 用于存放图片临时文件的文件夹名称
     */
    final public static String SITE_TEMP_IMG_DIR_NAME = "lvmama";
    /**
     * 站点名称
     */
    final public static String SITE_NAME = "驴妈妈";
	/**
	 * 站点ID
	 */
	public static final int SITE_ID = 26;
	/**
	 * 登录的URL
	 */
	public static final String URL_LOGIN = "http://ebooking.lvmama.com/vst_ebooking/login.do";
    /**
     * 退出登录url
     */
    public static final String URL_LOGIN_OUT = "http://ebooking.lvmama.com/vst_ebooking/loginOut.do";
    /**
     * 检查登陆的url（修改密码）
     */
    final static String URL_CHECK_LOGIN = "http://ebooking.lvmama.com/vst_ebooking/editPwd.do";
	/**
	 * 登录时图片的请求地址
	 */
//	public static final String URL_GET_VERIFY_CODE = "http://ebooking.lvmama.com/vst_ebooking/login/validate_code.do?_=";
	public static String URL_GET_VERIFY_CODE() {
	    return "http://ebooking.lvmama.com/vst_ebooking/login/validate_code.do?_=" + System.currentTimeMillis();
	}
//	/**
//	 * 下载旅游圈列表的URL
//	 */
//	public static final String URL_TRAVEL_PLACE = "http://gys.lyq517.com/api/get_sp_catg_syn?catg_id=5";
//	/**
//	 * 下载草稿箱中行程列表的URL
//	 */
//	public static final String URL_DRAFT = "http://gys.lyq517.com/Product/productOnline/status/1.html";
//	/**
//	 * 下载上架产品中行程列表的URL
//	 */
//	public static final String URL_UP = "http://gys.lyq517.com/Product/productOnline/status/2.html";
//	/**
//	 * 下载下架产品中行程列表的URL
//	 */
//	public static final String URL_DOWN = "http://gys.lyq517.com/Product/productOnline/status/3.html";
//	/**
//	 * 下载行程详细信息的URL
//	 */
//	public static final String URL_ROUTE_DETAIL = "http://gys.lyq517.com/Preview/view/id/%s.html";
//	/**
//	 * 下载行程基本信息的URL
//	 */
//	public static final String URL_ROUTE_BASIC = "http://gys.lyq517.com/product/api_basic?id=%s";
//	/**
//	 * 下载团期的URL
//	 */
//	public static final String URL_ROUTE_DATE = "http://gys.lyq517.com/product/api_price?id=%s";
//	/**
//	 * 下载目的地的URL
//	 */
//	public static final String URL_DESTINATION = "http://gys.lyq517.com/api/get_sp_tags?catg_id=%s&prod_id=0";
//    /**
//     * 下载线路类型的URL(产品一級分类)
//     */
//    public static final String URL_ROUTE_FIRST_CATEGORY = "http://gys.lyq517.com/api/get_sp_categories";
//
//    /**
//     * 下载线路类型的URL(产品二級分类)
//     * 将PID替换成一级分类中的pid
//     */
//    public static final String URL_ROUTE_SECOND_CATEGORY = "http://gys.lyq517.com/api/get_sp_categories?pid=PID";
//    /**
//     * 下载目的地
//     * 将CATG_ID替换成产品二級分类的id
//     */
//    public static final String URL_ROUTE_DESTINATION = "http://gys.lyq517.com/api/get_sp_tags?catg_id=CATG_ID&prod_id=0";
//	/**
//	 * 下载线路的URL
//	 */
//	public static final String URL_ROUTE = "http://gys.lyq517.com/api/get_sp_categories?pid=%s";
//
//	/**
//	 * 是否打开post  false 不打开
//	 */
//	boolean post = false;
//	/**
//	 * 响应头格式
//	 */
//	String msg = "siteId={28},loginName={%s},status={%s}";
//	/**
//	 * 新增或者修改行程基本信息
//	 */
//	String URL_UPDATE_ROUTE_BASE = "http://gys.lyq517.com/product/api_basic?step=1";
//	/**
//	 * 修改行程扩展信息
//	 */
//	String URL_UPDATE_ROUTE_EXTEND = "http://gys.lyq517.com/product/api_detail";
//	/**
//	 * 进入新增行程页面
//	 */
//	String URLGETADDROUTE = "http://gys.lyq517.com/Product/create.html"; 
//	/**
//	 * 根据线路id获取目的地 prod_id是行程id
//	 */
//	String URLGETDIS = "http://gys.lyq517.com/api/get_sp_tags?catg_id=%s&prod_id=0";
//	/**
//	 * 删除行程
//	 */
//	String URL_DELETE_ROUTE = "http://gys.lyq517.com/Product/delete.html?id=ROUTEID";
//	/**
//	 * 抓取行程特殊信息
//	 */
//	String URLGETROU = "http://gys.lyq517.com/product/api_basic?id=%s";
//	/**
//	 * 行程下架URL
//	 */
//	public static final String URL_ROUTE_DOWN_CARRIGE = "http://gys.lyq517.com/Product/offLine.html?id=ROUTEID";
//	/**
//	 * 修改行程状态（下架到上架）URL
//	 */
//	public static final String URL_DOWN_UP = "http://gys.lyq517.com/Product/onLine.html?id=%s";
//	/**
//	 * 删除价格
//	 */
//	public static final String URL_DEL_PRICE = "http://gys.lyq517.com/product/api_price";
//	/**
//	 * 网站前缀
//	 */
//	public static final String WEB_PREFIX = "http://gys.lyq517.com";
//    /**
//     * 上架产品
//     */
//    final public static String URL_SUBMIT_ROUTE = "http://gys.lyq517.com/Product/onLine.html?id=ROUTEID";
//    /**
//     * 点击价格模板
//     */
//    final public static String URL_GET_PRODUCT_MARKET = "http://gys.lyq517.com/api/get_product_market?catg_id=CATG_ID&prod_id=PROD_ID";
//    /**
//     * 修改团期
//     */
//    final public static String URL_UPDATE_TRIP = "http://gys.lyq517.com/product/api_price";
//    /**
//     * 上传图片
//     */
//    final public static String URL_UPLOAD_IMG = "http://gys.lyq517.com/file/upload";
//    /**
//     * 订单列表
//     */
//    final public static String URL_ORDER_LIST = "http://gys.lyq517.com/order/sup_api_list?page_index=PAGE_INDEX&page_size=20&partner_id=0&pay_off=0&status=0";
}
