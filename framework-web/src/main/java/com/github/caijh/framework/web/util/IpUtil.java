package com.github.caijh.framework.web.util;

import javax.servlet.http.HttpServletRequest;

import com.github.caijh.framework.web.constant.Constants;
import org.apache.commons.lang3.StringUtils;

public class IpUtil {

    private IpUtil() {}

    /**
     * 获取真实IP地址.
     *
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getIp(HttpServletRequest request) {
        String clientIp = getIpFromRequestHeader(request);
        if (StringUtils.isBlank(clientIp) || Constants.UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        /*
         * 对于获取到多ip的情况下，找到公网ip.
         */
        String sIP = null;
        if (clientIp != null && !clientIp.contains(Constants.UNKNOWN)) {
            String[] ips = clientIp.split(",");
            for (String ip : ips) {
                if (!isInnerIP(ip.trim())) {
                    sIP = ip.trim();
                    break;
                }
            }
            /*
             * 如果多ip都是内网ip，则取第一个ip.
             */
            if (null == sIP) {
                sIP = ips[0].trim();
            }
            clientIp = sIP;
        }
        if (clientIp != null && clientIp.contains(Constants.UNKNOWN)) {
            clientIp = clientIp.replace("unknown,", "");
            clientIp = clientIp.trim();
        }
        if ("".equals(clientIp) || null == clientIp) {
            clientIp = "127.0.0.1";
        }
        return clientIp;
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
        boolean isInnerIp;
        long ipNum = getIpNum(ipAddress);
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");

        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");

        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
                || ipAddress.equals("127.0.0.1");
        return isInnerIp;
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
