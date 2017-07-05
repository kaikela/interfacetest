package com.mistong.interfacetest.util;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestUtil {
	public static Logger logger = Logger.getLogger(UnirestUtil.class);
	public static boolean testResult;
	public static JsonNode responseBody = null;

	/**
	 * 发送post请求
	 * @param url
	 * @param queryStr
	 * @param jsonStr
	 * @param expect
	 * @return
	 */
	public static JsonNode InterfaceTest(String url,
			Map<String, Object> queryStr, String jsonStr, String expect) {
		HttpResponse<JsonNode> jsonResponse = null;
		boolean flag = true;
		logger.info("post请求数据开始");
		try {
			jsonResponse = Unirest.post(url)
					.header("accept", "application/json").queryString(queryStr)
					.field("request", jsonStr).asJson();
			responseBody = jsonResponse.getBody();
			logger.info("请求参数"+":"+jsonStr);
			logger.info("post请求数据完毕");
			
			// if (responseBody.toString().contains("true")) {
			// logger.info("接口正常情况测试,post请求正常响应，数据正常返回");
			// } else {
			// if (getErrorCode(responseBody.toString()) >= 246
			// && getErrorCode(responseBody.toString()) <= 255) {
			// flag = false;
			// logger.error("接口测试,接口发生异常,异常信息为：" + responseBody.toString());
			// Assert.assertTrue(flag);
			// } else {
			// logger.info("接口异常情况测试,post请求正常响应，正常返回异常信息");
			// }
			// }
			if (responseBody.toString().contains(expect)) {
				testResult = true;
				logger.info("接口测试,post请求正常响应,数据正确返回");
				logger.info("Response Body:" + responseBody);
			} else {
				flag = false;
				logger.error("接口测试,接口发生异常或存在异常数据进行接口注入,预期结果为:" + expect
						+ ",而实际结果为:" + responseBody.toString());
				
				Assert.assertTrue(flag);

			}
			
		} catch (UnirestException e) {
			flag = false;
			logger.error("接口测试发生异常,请求连接失败!");
			logger.info("Response Body:" + responseBody);
			Assert.assertTrue(flag);
		}
		return responseBody;
	}

	/**
	 * 发送post请求
	 * @param url
	 * @param queryStr
	 * @param jsonStr
	 * @return
	 */
	public static JsonNode PostJson(String url, Map<String, Object> queryStr,
			String jsonStr) {
		HttpResponse<JsonNode> jsonResponse = null;
		JsonNode responseBody = null;
		boolean flag = true;
		logger.info("post请求数据开始");
		try {
			jsonResponse = Unirest.post(url)
					.header("accept", "application/json").queryString(queryStr)
					.field("request", jsonStr).asJson();
			responseBody = jsonResponse.getBody();
			logger.info("post请求数据完毕");
//			打印手动组装的请求参数
//			System.out.println(url+queryStr+jsonStr);
//			logger.info("Response Body:" + responseBody);
		} catch (UnirestException e) {
			flag = false;
			logger.error("接口发生异常,请求连接失败!");
			Assert.assertTrue(flag);
		}
		return responseBody;
	}

	/**
	 * 获取responseBody中返回的ErrorCode
	 * 
	 * @param responseBody
	 * @return
	 */
	public static int getErrorCode(String responseBody) {
		int ErrorCode;
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(responseBody);
		ErrorCode = Integer.parseInt(m.replaceAll("").trim());
		return ErrorCode;
	}

	/**
	 * 截取字符串中的字符
	 * 
	 * @param sourceStr
	 *            源字符串
	 * @param startStr
	 *            起始字符
	 * @param endStr
	 *            截止字符
	 * @return
	 */
	public static String substr(String sourceStr, String startStr, String endStr) {
		String str;
		if (startStr.length() > 0 && endStr.length() > 0) {
			str = sourceStr.substring(
					sourceStr.indexOf(startStr) + startStr.length(),
					sourceStr.indexOf(endStr));
		} else if (startStr.length() == 0 && endStr.length() > 0) {
			str = sourceStr.substring(0, sourceStr.indexOf(endStr));
		} else if (startStr.length() > 0 && endStr.length() == 0) {
			str = sourceStr.substring(
					sourceStr.indexOf(startStr) + startStr.length(),
					sourceStr.length());
		} else {
			str = sourceStr;
		}
		return str;
	}

	/**
	 * 获取json字符串里面具体的key的值
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param object
	 *            json层级字符串，以"."分割，如：data.courseList.courseName
	 * @return
	 */
	public static String GetJsonValue(String jsonStr, Object object) {
		String JsonValue = null;
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		if (object.toString().contains(".")) {
			String[] temp = object.toString().split("\\.");
			for (int i = 0; i < temp.length; i++) {
				JsonValue = jsonObj.getString(temp[i]);
				if (JsonValue.substring(0, 1).equals("[")) {
					JsonValue = JsonValue.substring(1, JsonValue.length() - 1);
				}
				if (i < temp.length - 1) {
					jsonObj = JSONObject.fromObject(JsonValue);
				}
			}
		} else {
			JsonValue = jsonObj.getString(object.toString());
		}
		return JsonValue;
	}
	
	/**
	 * 将response里面中的数组json存储到一个子json中
	 * @param arrName response里面中的含有数组json的字段名
	 * @return
	 */
	public static JSONObject arrJson(String arrName){
		JSONObject root = JSONObject.fromObject(responseBody.toString());//返回的response
		JSONObject jsonObj = null;
		if (arrName != null && arrName.length() > 0) {
			JSONObject root_data = root.getJSONObject("data");//返回的response的data部分数据
			JSONArray arrInfo = root_data.getJSONArray(arrName);//response中的arrName数组
			 jsonObj = new JSONObject();//存放commodityInfo数组的json
			JSONObject arrayJson = new JSONObject();//存放commodityInfo数组里面的json
			JSONArray array = new JSONArray();//存放commodityInfo数组
			//循环遍历内容并存放到对应的json数组中
			for (int i = 0; i < arrInfo.size(); i++) {
				JSONObject obj = arrInfo.getJSONObject(i);
				Iterator<?> iter = obj.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					arrayJson.put(key, obj.getString(key));
				}
				array.add(arrayJson);
			}
			jsonObj.put("array", array);
		} else {
			jsonObj = root.getJSONObject("data");
		}
		return jsonObj;
	}
	
	/**
	 * 将response里面中的含有数组的子json和数据库查询出来转换为json的数据进行比较。
	 * @param database 连接数据库名
	 * @param arrName  response里面中的含有数组json的字段名
	 * @param sqlStr  SQL字符串
	 */
	public static void checkArrJsonData(String database,String arrName,String sqlStr) {
		JSONObject jsonObj = null;
		JSONObject sqlJson = null;
		jsonObj = arrJson(arrName);
		logger.info("json为：" + jsonObj);
		sqlJson = DBHelper.sqlToJson(database,sqlStr);// 将SQL结果存放到json中
		logger.info("sqlJson:" + sqlJson);
		System.out.println("查询数据库的结果"+":"+sqlJson);
		boolean flag;
		if (jsonObj.equals(sqlJson)) {
			logger.info("接口测试数据正常返回,数据一致");
		} else {
			flag = false;
			logger.error("接口测试数据正常返回,但返回的数据不正确");
			Assert.assertTrue(flag);
		}
	}
	
}
