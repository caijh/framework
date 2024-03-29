package com.github.caijh.framework.web.util;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import com.github.caijh.framework.web.constant.Constants;
import io.micrometer.core.instrument.util.StringUtils;

public class RequestUtils {

    private RequestUtils() {

    }

    /**
     * extract Header.
     *
     * @param request HttpServletRequest
     * @param param   参数
     * @return 查找请求是否有参数，有返回；无则查找header。
     */
    public static String extractHeader(HttpServletRequest request, String param) {
        String headValue = request.getParameter(param);
        if (null != headValue) {
            return headValue;
        }
        return request.getHeader(param);
    }

    /**
     * 获取真实IP地址.
     *
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getIp(HttpServletRequest request) {
        String clientIp = RequestUtils.getIpFromRequestHeader(request);
        if (StringUtils.isBlank(clientIp) || Constants.UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        /*
         * 对于获取到多ip的情况下，找到公网ip.
         */
        if (clientIp != null && !clientIp.contains(Constants.UNKNOWN)) {
            String[] ips = clientIp.split(",");
            clientIp = Arrays.stream(ips).map(String::trim)
                             .filter(trim -> !RequestUtils.isInnerIP(trim))
                             .findFirst().orElse(ips[0].trim());
        }
        if (clientIp != null && clientIp.contains(Constants.UNKNOWN)) {
            clientIp = clientIp.replace("unknown,", "").trim();
        }
        return clientIp != null && !"".equals(clientIp) ? clientIp : "127.0.0.1";
    }

    private static String getIpFromRequestHeader(HttpServletRequest request) {
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
        for (String header : headers) {
            String clientIP = request.getHeader(header);
            if (StringUtils.isNotBlank(clientIP) && !Constants.UNKNOWN.equalsIgnoreCase(clientIP)) {
                return clientIP;
            }
        }
        return null;
    }

    /**
     * 判断IP是否是内网地址
     * <p>
     * 私有IP：A类  10.0.0.0-10.255.255.255
     * B类  172.16.0.0-172.31.255.255
     * C类  192.168.0.0-192.168.255.255
     * 当然，还有127这个网段是环回地址
     * </p>
     *
     * @param ipAddress ip地址
     * @return 是否是内网地址
     */
    public static boolean isInnerIP(String ipAddress) {
        long ipNum = RequestUtils.getIpNum(ipAddress);

        return RequestUtils.isInner(ipNum, RequestUtils.getIpNum("10.0.0.0"), RequestUtils.getIpNum("10.255.255.255"))
                || RequestUtils.isInner(ipNum, RequestUtils.getIpNum("172.16.0.0"), RequestUtils.getIpNum("172.31.255.255"))
                || RequestUtils.isInner(ipNum, RequestUtils.getIpNum("192.168.0.0"), RequestUtils.getIpNum("192.168.255.255"))
                || ipAddress.equals("127.0.0.1");
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

}
