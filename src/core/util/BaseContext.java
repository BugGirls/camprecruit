package core.util;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseContext {
    private static String rootpath = null;

    private static String host = null;

    private static String dialectname = null;

    private static String top1sql = "";

    private static String rownum1oracle = "";

    private static String rownum1oracleOrderby = "";

    public static Object getBean(ServletContext sc, String name) {
        return getBean(name);
    }

    public static Object getBean(HttpServletRequest request, String name) {
        return getBean(name);
    }

    public static String getRootPath() {
        return rootpath;
    }

    public static void setRootPath(String _rootpath) {
        rootpath = _rootpath;
    }

    public static String getSQLPropertiesFile() {
        if (dialectname == null)
            return "";
        if (dialectname.contains("SQLServer")) {
            top1sql = "top 1";
            return "sqlmap_mssql.properties";
        }
        if (dialectname.contains("Oracle")) {
            rownum1oracle = " rownum=1 and ";
            rownum1oracleOrderby = " where rownum=1  ";
            return "sqlmap_oracle.properties";
        }
        return "";

    }

    public static String getDialectname() {
        return dialectname;
    }

    public static void setDialectname(String dialectname) {
        BaseContext.dialectname = dialectname;
    }

    // ****************************************************************8
    /**
     * 通过 ApplicationContext 取得工厂实例
     */
    public static BaseContext init(ApplicationContext context) {
        getInstance().setApplicationContext(context);
        return sbf;
    }

    /**
     * 通过 ServletContext 取得工厂实例
     */
    public static BaseContext init(ServletContext context) {
        getInstance().setServletContext(context);
        return sbf;
    }

    /**
     * 取得系统配置对象单实例
     */
    public static BaseContext getInstance() {
        return sbf;
    }

    public void setApplicationContext(ApplicationContext context) {
        actx = context;
    }

    public ApplicationContext getApplicationContext() {
        if (actx == null) {
            initApplicationContext(sctx);
        }
        return actx;
    }

    public void setServletContext(ServletContext context) {
        initApplicationContext(context);
    }

    /**
     * 通过ServletContext 初始化ApplicationContext
     */
    @SuppressWarnings("unchecked")
    private void initApplicationContext(ServletContext context) {
        // 取得DispatcherServlet空间的上下文
        Enumeration<String> attrs = context.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            // log.debug(attr + ":" + context.getAttribute(attr));
            if (attr.indexOf(CTX_KEY) != -1) {
                actx = (ApplicationContext) context.getAttribute(attr);
                break;
            }
        }

        if (actx == null) {
            // 再取得ROOT 上下文
            actx = WebApplicationContextUtils.getWebApplicationContext(context);
        }
    }

    /**
     * 取得SpringBean
     */
    public static Object take(String beanName) {
        if (sbf.actx == null) {
            // 检查servlet context
            if (sbf.sctx == null) {
                throw new RuntimeException("ServletContext未初始化~!");
            }
            sbf.initApplicationContext(sbf.sctx);
        }
        if (sbf.actx == null) {
            log.warn("ApplicationContext is null~~!");
            throw new RuntimeException("ApplicationContext未初始化~!");
        }
        return sbf.actx.getBean(beanName);
    }

    public static <T> T getBean(String beanName) {
        Object bean = take(beanName);
        return (T) bean;
    }

    // spring mvc 配置空间在上下文中的 key前缀
    private static final String CTX_KEY = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.";
    private ServletContext sctx = null;
    // 应用上下文环境
    private static ApplicationContext actx = null;
    private static final BaseContext sbf = new BaseContext();
    private static Log log = LogFactory.getLog(BaseContext.class);
    private volatile static boolean contextLoadFinished = false;

    public static boolean isLoadOver() {

        return contextLoadFinished;
    }

    public static void setLoadOver(boolean finished) {

        contextLoadFinished = finished;
    }

    public static String getTop1sql() {
        return top1sql;
    }

    public static void setTop1sql(String top1sql) {
        BaseContext.top1sql = top1sql;
    }

    public static String getRownum1oracle() {
        return rownum1oracle;
    }

    public static void setRownum1oracle(String rownum1oracle) {
        BaseContext.rownum1oracle = rownum1oracle;
    }

    public static String getRownum1oracleOrderby() {
        return rownum1oracleOrderby;
    }

    public static void setRownum1oracleOrderby(String rownum1oracleOrderby) {
        BaseContext.rownum1oracleOrderby = rownum1oracleOrderby;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        BaseContext.host = host;
    }
}
