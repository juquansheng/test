package com.malaxiaoyugan.test.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Description: IP地址工具类
 */
public class IPUtil {

	/**
	 * getLocalhostIp(获取本机ip地址)
	 * @throws UnknownHostException 
	 * @Exception 异常对象 
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String getLocalhostIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return null;
		}
		return ip;
	}
	
	public static List<String> getIpAddrs() throws Exception {
		List<String> IPs = new ArrayList<String>();
		Enumeration<NetworkInterface> allNetInterfaces = null;
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<?> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address && ip.getHostAddress().indexOf(".") != -1) {
					IPs.add(ip.getHostAddress());
				}
			}
		}
		return IPs;
	}    		

	/**
	 * 兼容Linux系统
	 * @return
	 */
	public static String getLocalIP() {
		String ip = "";
		try {
			Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
					.getNetworkInterfaces();
			while (e1.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e1.nextElement();
				Enumeration<?> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					InetAddress ia = (InetAddress) e2.nextElement();
					if (ia instanceof Inet6Address)
						continue;
					if (!ia.isLoopbackAddress()) {
						ip = ia.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			return "";
		}
		return ip;
	}


	/**
	 * 获取当前网络ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress= inet.getHostAddress();
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
			if(ipAddress.indexOf(",")>0){
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}


	public static Long getIPNum(final String IP) {
		Long IPNum = 0l;
		final String IPStr = IP.trim();
		if (IP != null && IPStr.length() != 0) {
			final String[] subips = IPStr.split("\\.");
			for (final String str : subips) {
				// 向左移8位
				IPNum = IPNum << 8;
				IPNum += Integer.parseInt(str);
			}
		}
		return IPNum;
	}
	public static String getIPString(final Long IPNum) {
		final Long andNumbers[] = { 0xff000000L, 0x00ff0000L, 0x0000ff00L, 0x000000ffL };
		final StringBuilder IPStrSb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			IPStrSb.append(String.valueOf((IPNum & andNumbers[i]) >> 8 * (3 - i)));
			if (i != 3) {
				IPStrSb.append(".");
			}
		}
		return IPStrSb.toString();
	}

	/**
	 * 根据ip获取地址信息
	 * @param ip
	 * @return
	 */
	public static String getAddressInfo(String ip){
		JSONObject jsonObject = HttpUtil.httpGet("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true");
		return jsonObject.toJSONString();
	}

}
