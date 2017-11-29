/*----------------------------------------------------------------
 *  Copyright (C) 2016山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：CommonTool
 * 文件功能描述：公共工具类
 *
 * 
 * 创建标识：konglm20161026
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.goldeneyes.util.CommonTool;

/**
 * @author konglm
 *
 */
public class CommonTool {

	/**
	 * 测试用main函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(formatDate("20160102"));
	}

	/**
	 * 计算总页数
	 * 
	 * @param totalCnt
	 * @param pageSize
	 * @return
	 */
	public static int getTotalPage(int totalCnt, int pageSize) {
		if (pageSize == 0) {
			return 1;
		} else if (totalCnt % pageSize == 0) {
			return totalCnt / pageSize;
		} else {
			return totalCnt / pageSize + 1;
		}
	}

	/**
	 * http输出json字符串
	 * 
	 * @param response
	 * @param str
	 */
	public static void outJsonString(HttpServletResponse response, String str) {
		PrintWriter out = null;
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Int转Byte
	 * 
	 * @param res
	 * @return
	 */
	public static byte int2byte(int res) {
		byte[] targets = new byte[1];

		targets[0] = (byte) (res & 0xff);// 最低位
		return targets[0];
	}

	/**
	 * 封装JSON返回
	 * 
	 * @param jArray
	 * @return
	 */
	public static JSONObject outJson(JSONObject jObject, String outResult) {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("RspData", jObject);
		switch (outResult) {
		case "0000": {
			jsonobj.put("RspCode", "0000");
			jsonobj.put("RspTxt", "正常");
			break;
		}
		case "1001": {
			jsonobj.put("RspCode", "1001");
			jsonobj.put("RspTxt", "数据库连接异常");
			break;
		}
		case "1002": {
			jsonobj.put("RspCode", "1002");
			jsonobj.put("RspTxt", "数据库更新失败");
			break;
		}
		case "1003": {
			jsonobj.put("RspCode", "1003");
			jsonobj.put("RspTxt", "参数类型错误");
			break;
		}
		case "1004": {
			jsonobj.put("RspCode", "1004");
			jsonobj.put("RspTxt", "参数不全");
			break;
		}
		case "1005": {
			jsonobj.put("RspCode", "1005");
			jsonobj.put("RspTxt", "参数值为空");
			break;
		}
		case "1006": {
			jsonobj.put("RspCode", "1006");
			jsonobj.put("RspTxt", "分页相关参数值必须大于零");
			break;
		}
		case "1007": {
			jsonobj.put("RspCode", "1007");
			jsonobj.put("RspTxt", "上传档案失败");
			break;
		}
		case "1008": {
			jsonobj.put("RspCode", "1008");
			jsonobj.put("RspTxt", "Url被篡改");
			break;
		}
		case "1009": {
			jsonobj.put("RspCode", "1009");
			jsonobj.put("RspTxt", "令牌已过期或不存在");
			break;
		}
		case "1010": {
			jsonobj.put("RspCode", "1010");
			jsonobj.put("RspTxt", "Url连接失败");
			break;
		}
		case "1011": {
			jsonobj.put("RspCode", "1011");
			jsonobj.put("RspTxt", "缺少验证参数");
			break;
		}
		case "1012": {
			jsonobj.put("RspCode", "1012");
			jsonobj.put("RspTxt", "工作流异常");
			break;
		}
		case "1013": {
			jsonobj.put("RspCode", "1013");
			jsonobj.put("RspTxt", "数据库或redis缓存连接失败");
			break;
		}
		case "1014": {
			jsonobj.put("RspCode", "1014");
			jsonobj.put("RspTxt", "工作流启动失败");
			break;
		}
		case "1015": {
			jsonobj.put("RspCode", "1015");
			jsonobj.put("RspTxt", "任务查询失败");
			break;
		}
		case "1016": {
			jsonobj.put("RspCode", "1016");
			jsonobj.put("RspTxt", "任务完成失败");
			break;
		}
		case "1017": {
			jsonobj.put("RspCode", "1017");
			jsonobj.put("RspTxt", "驳回请求失败");
			break;
		}
		case "1018": {
			jsonobj.put("RspCode", "1018");
			jsonobj.put("RspTxt", "新增用户失败");
			break;
		}
		case "1019": {
			jsonobj.put("RspCode", "1019");
			jsonobj.put("RspTxt", "删除用户失败");
			break;
		}
		}

		return jsonobj;
	}

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 描述:获取 post 请求内容
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	/**
	 * post调用其他的接口，传入json，接收json
	 * 
	 * @param request
	 * @param urlStr
	 * @param obj
	 * @return
	 */
	public static String getJsonFromRequest(String urlStr, JSONObject obj) {
		String returnJson = "";

		try {
			// 创建url资源
			URL url = new URL(urlStr);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);

			conn.setDoInput(true);

			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");
			// 转换为字节数组
			byte[] data = (obj.toString()).getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));

			// 设置文件类型:
			conn.setRequestProperty("Content-type", "application/json");

			// 开始连接请求
			conn.connect();
			OutputStream out = conn.getOutputStream();
			// 写入请求的字符串
			out.write((obj.toString()).getBytes());
			out.flush();
			out.close();

			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				// 请求返回的数据
				InputStream in = conn.getInputStream();

				try {
					byte[] bufferData = new byte[in.available()];
					int bytesRead = in.read(bufferData);
					// 转成字符串
					returnJson = new String(bufferData, 0, bytesRead, "UTF-8");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					return CommonTool.outJson(new JSONObject(), "1010").toString();
				}
			} else {
				return CommonTool.outJson(new JSONObject(), "1010").toString();
			}

		} catch (Exception e) {
			return CommonTool.outJson(new JSONObject(), "1010").toString();
		}
		return returnJson;
	}

	/**
	 * 将jsonArray解析为List 修改：xubs，2017-8-10 List<String>的泛型为字符串
	 * 
	 * @param jArray
	 * @return
	 */
	public static List<String> getListFromJsonArray(JSONArray jArray) {
		List<String> jsonStr = new ArrayList<String>();
		for (int i = 0; i < jArray.length(); i++) {
			jsonStr.add(jArray.getString(i));
		}
		return jsonStr;
	}

	/**
	 * 将jsonArray解析为List 修改：xubs，2017-8-10 List<String>的泛型为整型
	 * 
	 * @param jArray
	 * @return
	 */
	public static List<Integer> getListIntFromJsonArray(JSONArray jArray) {
		List<Integer> jsonStr = new ArrayList<Integer>();
		for (int i = 0; i < jArray.length(); i++) {
			jsonStr.add(jArray.getInt(i));
		}
		return jsonStr;
	}

	/**
	 * Json排序
	 * 
	 * @param obj
	 * @return
	 */
	public static TreeMap sortJsonObject(JSONObject obj) {
		TreeMap map = new TreeMap();
		Iterator<String> it = obj.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object value = obj.get(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 获取文件路径中除文件名之外的路径
	 * 
	 * @param fileAllPath
	 * @return
	 */
	public static String getFilePath(String fileAllPath) {
		return fileAllPath.substring(0, fileAllPath.lastIndexOf("/"));
	}

	/**
	 * 获取文件路径中的文件名
	 * 
	 * @param fileAllPath
	 * @return
	 */
	public static String getFileName(String fileAllPath) {
		return fileAllPath.substring(fileAllPath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 获取签名
	 * 
	 * @param urls
	 * @return
	 */
	public static String getSign(String encryptText) {
		String sign = "";
		String key = getProp("signKey");
		try {
			sign = EncryptUtil.hmacSHA1Encrypt(encryptText, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 获取配置参数
	 * 
	 * @param propName
	 * @return
	 */
	public static String getProp(String propName) {
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/common.properties");
		try {
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties.getProperty(propName);
	}
	
	/**
	 * 比较两个日期
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compareDate(String DATE1, String DATE2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dt1 = sdf.parse(DATE1);
			Date dt2 = sdf.parse(DATE2);
			if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 从日期中提取格式化的年月日
	 * @param date
	 * @return
	 */
	public static String getDayFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 时间增加分钟数
	 * @param time
	 * @return
	 */
	public static String addMinute(String time, int lateMinute){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MINUTE, lateMinute);
		return sdf.format(cal.getTime());
	}
	/**
	 * 时间减少分钟数
	 * @param time
	 * @return
	 */
	public static String minusMinute(String time, int lateMinute){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MINUTE, -lateMinute);
		return sdf.format(cal.getTime());
	}
	/**
	 * 格式化8位日期格式为标准日期格式
	 * @param originDate
	 * @return
	 */
	public static String formatDate(String originDate){
		return originDate.substring(0, 4) + "-" + originDate.substring(4, 6) + "-" + originDate.substring(6, 8); 
	}

}
