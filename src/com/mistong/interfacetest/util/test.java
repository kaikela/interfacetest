package com.mistong.interfacetest.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class test {

	/**
	 * @Description: TODO
	 * @param @param args   
	 * @return void  
	 * @throws
	 * @author 吴丁飞
	 * @date 2017-1-6
	 */
	public static void main(String[] args) {
		HttpResponse<JsonNode> jsonResponse = null;
		try {
			jsonResponse = Unirest.post("http://mobile.kaike.la/app/api/request.do")
					.header("accept", "application/json").queryString("msgId","07004")
					.field("request", "{\"os\":\"1\",\"version\":\"2.1.0\"}").asJson();
		System.out.println(jsonResponse.getHeaders());
		System.out.println(substr(substr(jsonResponse.getHeaders().toString(),"X-HOST=[",""),"","]"));
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	

}
