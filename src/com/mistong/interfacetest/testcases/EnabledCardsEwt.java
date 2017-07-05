package com.mistong.interfacetest.testcases;

import java.security.MessageDigest;

import org.testng.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EnabledCardsEwt {

	public static void main(String[] args) {
		HttpResponse<JsonNode> jsonResponse = null;
		JsonNode responseBody = null;
		boolean flag = true;
		String url, cardNoStart, cardNoEnd, schoolId, endDate, tp, ticket;
		int cardCount;
		url = "http://bss.kaike.la/bss/cardEnabled/enabledCardsEwt.do";
		cardCount = 1;
		cardNoStart = "201670000219";
		cardNoEnd = "201670000219";
		schoolId = "1";
		endDate = "2016-08-30";
		tp = new Long(System.currentTimeMillis() / 1000).toString();
		System.out.println("tp:"+tp);
		ticket = getStrLower("1!hong3@wen2#" + cardCount + cardNoStart
				+ cardNoEnd + schoolId + endDate + tp + "1!hong3@wen2#");
		System.out.println("ticket:"+ticket);
		System.out.println("post请求数据开始");
		try {
			jsonResponse = Unirest.post(url)
					.header("accept", "application/json")
					.field("cardCount", cardCount)
					.field("cardNoStart", cardNoStart)
					.field("cardNoEnd", cardNoEnd).field("schoolId", schoolId)
					.field("endDate", endDate).field("tp", tp)
					.field("ticket", ticket).asJson();
			responseBody = jsonResponse.getBody();
			System.out.println("post请求数据完毕");
			System.out.println("Response Body:" + responseBody);
		} catch (UnirestException e) {
			flag = false;
			System.out.println("接口发生异常,请求连接失败！");
			Assert.assertTrue(flag);
		}

	}

	public static String getStrLower(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("utf-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
