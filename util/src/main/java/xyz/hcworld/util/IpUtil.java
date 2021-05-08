package xyz.hcworld.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip获取类
 * @ClassName: IpUtil
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
public class IpUtil {
    /**
     * 未知ip
     */
    private final static String UNKNOWN = "unknown";

    /**
     * 解析Header的数据获取真实ip
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
