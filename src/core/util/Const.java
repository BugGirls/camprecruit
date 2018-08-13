package core.util;

import org.springframework.context.ApplicationContext;

/**
 * 项目名称：
 * 修改日期：2015/11/2
 */
public class Const {
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";    //验证码
    public static final String SESSION_USER = "sessionUser";                //session用的用户
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String SESSION_menuList = "menuList";                //当前菜单
    public static final String SESSION_allmenuList = "allmenuList";            //全部菜单
    public static final String SESSION_QX = "QX";
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";                    //用户对象
    public static final String SESSION_USERNAME = "USERNAME";                //用户名
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String SKIN = "SKIN";                                //用户皮肤
    public static final String LOGIN = "/login_toLogin.do";                    //登录地址
    public static final String SYSNAME = "admin/config/SYSNAME.txt";        //系统名称路径
    public static final String PAGE = "admin/config/PAGE.txt";                //分页条数配置路径
    public static final String EMAIL = "admin/config/EMAIL.txt";            //邮箱服务器配置路径
    public static final String SMS1 = "admin/config/SMS1.txt";                //短信账户配置路径1
    public static final String SMS2 = "admin/config/SMS2.txt";                //短信账户配置路径2
    public static final String FWATERM = "admin/config/FWATERM.txt";        //文字水印配置路径
    public static final String IWATERM = "admin/config/IWATERM.txt";        //图片水印配置路径
    public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";    //WEBSOCKET配置路径
    public static final String LOGINEDIT = "admin/config/LOGIN.txt";        //登录页面配置
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";        //图片上传路径
    public static final String FILEPATHFILE = "uploadFiles/file/";            //文件上传路径
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
    public static final String FILEPATHTBARCODE = "uploadFiles/barcode/";    //条形码存放路径
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)|(uploadImgs)).*";    //不对匹配该值的访问路径拦截（正则）
    public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化

    /**
     * APP Constants
     */
    //系统用户注册接口_请求协议参数)
    public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME", "PASSWORD", "NAME", "EMAIL", "rcode"};
    public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名", "密码", "姓名", "邮箱", "验证码"};

    //app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};

    /**
     * 需要和微信后台配置的token一致
     */
    public static final String TOKEN = "test";

    public static final String ORDER_NAME = "云尚互联订单";

    /**
     * AppId 和 AppSecret
     */
    public static final String APP_ID = "wx9760b6876d5e339f";
    public static final String APP_SECRET = "7920140c08c03ad5a348cb0c77c3e222";

    /**
     * 获取access_token URL
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 新增永久素材URL
     */
    public static final String ADD_EVER_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 删除永久素材URL
     */
    public static final String DELETE_EVER_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
    /**
     * 创建卡券URL
     */
    public static final String CREATE_CARD_URL = "https://api.weixin.qq.com/card/create?access_token=TOKEN";

    /**
     * 创建二维码，投放卡券
     */
    public static final String PUT_CARD_QRCODE_URL = "https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN";

    /**
     * 查询codeURl
     */
    public static final String GET_CODE_URL = "https://api.weixin.qq.com/card/code/get?access_token=TOKEN";

    /**
     * 核销卡券URL
     */
    public static final String CONSUME_CARD_URL = "https://api.weixin.qq.com/card/code/consume?access_token=TOKEN";

    /**
     * 删除卡券URL
     */
    public static final String DELETE_CARD_URL = "https://api.weixin.qq.com/card/delete?access_token=TOKEN";

    /**
     * 设置卡券失效接口URL
     */
    public static final String SET_CARD_UNAVAILABLE = "https://api.weixin.qq.com/card/code/unavailable?access_token=TOKEN";

    /**
     * 更改code接口URL
     */
    public static final String UPDATE_CODE_URL = "https://api.weixin.qq.com/card/code/update?access_token=TOKEN";

    /**
     * 修改卡券库存
     */
    public static final String MODIFY_STOCK_URL = "https://api.weixin.qq.com/card/modifystock?access_token=TOKEN";

    /**
     * 创建会员卡URL
     */
    public static final String CREATE_MEMBER_CARD_URL = "https://api.weixin.qq.com/card/create?access_token=TOKEN";

    /**
     * 微信刷卡支付url
     */
    public static final String WECHAT_SWIPE_PAY_URL = "https://api.mch.weixin.qq.com/pay/micropay";

    /**
     * 查询微信刷卡支付订单URL
     */
    public static final String WECHAT_QUERY_SWIPE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 上传适用于门店的图片
     */
    public static final String UPLOAD_IMAGE_FOR_POI_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=TOKEN";

    /**
     * 创建门店URL
     */
    public static final String CREATE_POI_URL = "http://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=TOKEN";

    /**
     * 查询门店信息URL
     */
    public static final String QUERY_POI_URL = "http://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token=TOKEN";

    /**
     * 修改门店信息URL
     */
    public static final String UPDATE_POI_URL = "https://api.weixin.qq.com/cgi-bin/poi/updatepoi?access_token=TOKEN";

    /**
     * 删除门店信息URL
     */
    public static final String DELETE_POI_URL = "https://api.weixin.qq.com/cgi-bin/poi/delpoi?access_token=TOKEN";

    /**
     * 获取用户基本信息
     */
    public static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=TOKEN&openid=OPENID&lang=zh_CN";
}
