package com.mistong.interfacetest.testcases.payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.util.DBHelper;
import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口 /api/request.do?msgId=11006
 * @描述 支付结果
 * @author wudingfei
 * 
 */
public class payment_006_paymentresult_test extends BaseParpare {
	String memberId = null;
	int i = 0;
	String orderNo, commodityName, totalPrice, outTradeNo, OrderInfo, postStr,
			payOrder;
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(payment_006_paymentresult_test.class.getName());
 
	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		// 查询用户的memberId
		String qsql1 = "select id from T_MEMBER where MOBILE_NO= '13900000001'";
		ResultSet qrs1 = DBHelper.executeQuery("ikukocesi", qsql1);
		try {
			while (qrs1.next()) {
				memberId = qrs1.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("清除用户购买课程相关数据开始，请等待......");
		String del1, del2, del3, del4, del5, del6, del7;
		del1 = "delete from T_PAYMENT where ORDER_ID in (select T_ORDER.id from T_ORDER,T_MEMBER where T_ORDER.MEM_ID = T_MEMBER.ID and MOBILE_NO = '13900000001')";
		del2 = "delete from T_ORDER where MEM_ID in (select id from T_MEMBER where MOBILE_NO = '13900000001')";
		del3 = "delete from T_ORDER_DETAIL where MEMBER_ID in (select id from T_MEMBER where MOBILE_NO = '13900000001')";
		del4 = "delete from T_PAY_LOG_FOR_KB where MEMBER_ID in (select id from T_MEMBER where MOBILE_NO = '13900000001')";
		del5 = "delete from T_KUKO_COIN_DETAILS where LOGIN_NAME = '13900000001' and flag <> '02'";
		del6 = "delete from T_MEMBER_COURSE where member_id in (select id from T_MEMBER where LOGIN_NAME = '13900000001')";
		del7 = "delete from T_MEMBER_COMMODITY where member_id in (select id from T_MEMBER where LOGIN_NAME = '13900000001')";
		DBHelper.executeNonQuery("ikukocesi", del1);
		DBHelper.executeNonQuery("ikukocesi", del2);
		DBHelper.executeNonQuery("ikukocesi", del3);
		DBHelper.executeNonQuery("ikukocesi", del4);
		DBHelper.executeNonQuery("ikukocesi", del5);
		DBHelper.executeNonQuery("ikukocesi", del6);
		DBHelper.executeNonQuery("ikukocesi", del7);
		logger.info("清除用户购买课程相关数据完成");

		logger.info("更新用户KB数据开始，请等待......");
		String updataSql, updataSql2;
		updataSql = "update T_KUKO_COIN_BALANCE set BALANCE = '30',TOTAL_REVENUE = '30',TOTAL_EXPENDITURE = 0.00,AWARD_BALANCE = '30',"
				+ "AWARD_REVENUE = '30',AWARD_EXPENDITURE=0.00  where LOGIN_NAME = '13900000001'";
		updataSql2 = "update T_KUKO_COIN_DETAILS set AMOUNT = '30',BALANCE = '30' where LOGIN_NAME = '13900000001'";
		DBHelper.executeNonQuery("ikukocesi", updataSql);
		DBHelper.executeNonQuery("ikukocesi", updataSql2);
		logger.info("更新用户KB数据完成");

		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");

	}

	@Test(dataProvider = "testData")
	public void testCase(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		i = i + 1;
		switch (i) {
		case 1:// 对支付宝支付订单进行支付结果处理
				// 生成订单
			OrderInfo = UnirestUtil.PostJson(
					"http://mobile.kaike.la/app/api/request.do",
					StrToMap("msgId:11001"),
					StrToJson("commodityId:321,memberId:" + memberId
							+ ",payType:1")).toString();
			// 获取订单信息
			orderNo = UnirestUtil.GetJsonValue(OrderInfo, "data.orderNo");
			// 获取商品名称
			commodityName = UnirestUtil.GetJsonValue(OrderInfo,
					"data.commodityName");
			// 获取商品总价格
			totalPrice = UnirestUtil.GetJsonValue(OrderInfo, "data.totalPrice");
			payOrder = UnirestUtil
					.PostJson(
							"http://mobile.kaike.la/app/api/request.do",
							StrToMap("msgId:11005"),
							StrToJson("memberId:"
									+ memberId
									+ ",orderNo:"
									+ orderNo
									+ ",totalPrice:"
									+ totalPrice
									+ ",channel:1,system:1,businessType:1,loginName:13100000009,description:"
									+ commodityName)).toString();
			System.out.println("订单确认信息：" + payOrder);
			String outTradeNo;
			// 获取虚拟订单号
			outTradeNo = UnirestUtil.substr(
					UnirestUtil.substr(payOrder, "&out_trade_no=\\\"", ""), "",
					"\\\"");
			postStr = data.get("json").replace("{outTradeNo}", outTradeNo)
					.replace("{tradeFee}", totalPrice);
			// 执行接口测试
			UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
					StrToJson(postStr), data.get("expect"));
			break;
		case 2:// 对支付宝支付订单进行重复支付结果处理
				// 生成订单
			OrderInfo = UnirestUtil.PostJson(
					"http://mobile.kaike.la/app/api/request.do",
					StrToMap("msgId:11001"),
					StrToJson("commodityId:322,memberId:" + memberId
							+ ",payType:1")).toString();
			// 获取订单信息
			orderNo = UnirestUtil.GetJsonValue(OrderInfo, "data.orderNo");
			// 获取商品名称
			commodityName = UnirestUtil.GetJsonValue(OrderInfo,
					"data.commodityName");
			// 获取商品总价格
			totalPrice = UnirestUtil.GetJsonValue(OrderInfo, "data.totalPrice");
			payOrder = UnirestUtil
					.PostJson(
							"http://mobile.kaike.la/app/api/request.do",
							StrToMap("msgId:11005"),
							StrToJson("memberId:"
									+ memberId
									+ ",orderNo:"
									+ orderNo
									+ ",totalPrice:"
									+ totalPrice
									+ ",channel:1,system:1,businessType:1,loginName:13100000009,description:"
									+ commodityName)).toString();
			System.out.println("订单确认信息：" + payOrder);
			// 获取虚拟订单号
			outTradeNo = UnirestUtil.substr(
					UnirestUtil.substr(payOrder, "&out_trade_no=\\\"", ""), "",
					"\\\"");
			postStr = data.get("json").replace("{outTradeNo}", outTradeNo)
					.replace("{tradeFee}", totalPrice);
			UnirestUtil.PostJson("http://mobile.kaike.la/app/api/request.do",
					StrToMap("msgId:11006"), StrToJson(postStr));
			// 执行接口测试
			UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
					StrToJson(postStr), data.get("expect"));
			break;

		}

	}
}
