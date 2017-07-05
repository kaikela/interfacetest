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
 * @接口     /api/request.do?msgId=11001
 * @描述               生成订单
 * @author  wudingfei
 * 
 */
public class payment_001_ordergenerate_test extends BaseParpare {
	String memberId = null;
	int i = 0;
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(payment_001_ordergenerate_test.class.getName());

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

		logger.info("插入用户的购买课程信息，请等待......");
		String ins1, ins2, ins3, ins4, ins5, ins6, ins7;
		ins1 = "INSERT INTO T_PAYMENT (ID, SERIAL_NO, ORDER_ID, T_SYSTEM, BUSSINESS_TYPE, TRANSACTION_NO, PLATFORM, COIN_AMOUNT, "
				+ "UPMP_AMOUNT, ALIPAY_AMOUNT, TOTAL_AMOUNT, CREATOR, UPDATOR, UPDATE_DATE, CREATE_DATE, STATUS, TRADING_HOUR) VALUES "
				+ "(19267, '201608044007781007781007781', '34247', 1, 1, '0', 4, 20.00, 0.00, 0.00, 20.00, NULL, NULL, '2016-8-4 11:15:35',"
				+ " '2016-8-4 11:15:34', 2, NULL)";
		ins2 = "INSERT INTO T_ORDER (ID, ORDER_NO, PAY_SERIAL_NO, MEM_ID, COMMODITY_COUNT, TOTAL_DISCOUNT, TOTAL_AMOUNT, KUKO_COINS_PAID_AMOUNT, "
				+ "REMAINING_AMOUNT, STATUS, CREATOR, UPDATOR, CREATE_DATE, UPDATE_DATE, DELETED, REMARK, pay_count, PAY_SUCCESS_TIME, AGENT_ID) "
				+ "VALUES (34247, '2016080400011413011413011413', NULL, "
				+ memberId
				+ ", NULL, 0.0000, 20.0000, 0.0000, 20.0000, 3, 0, 0, '2016-8-4 11:15:28','2016-8-4 11:15:35', 'N', '', NULL, '2016-8-4 11:16:29', NULL)";
		ins3 = "INSERT INTO T_ORDER_DETAIL (ID, ORDER_NO, ORDER_ID, MEMBER_ID, LESSION_ID, AMOUNT, DISCOUNT_AMOUNT, CREATOR, UPDATOR, CREATE_DATE, "
				+ "UPDATE_DATE, STATUS, DELETED, COMMODITY_ID) VALUES (44832, '2016080400011413011413011413', 34247, "
				+ memberId
				+ ", NULL, 20.0000, 0, 0, 0, '2016-8-4 11:15:28', NULL, 1, 'N', 456)";
		ins4 = "INSERT INTO T_PAY_LOG_FOR_KB (ID, PAY_MENT_ID, MEMBER_ID, LOGIN_NAME, RECHARGE_RECORD_ID, SERIAL_NO, PLATFORM, "
				+ "TRANSACTION_NO, AMOUNT, STATUS, CREATOR, UPDATOR, CREATE_DATE, UPDATE_DATE, DELETED, BING_ID) VALUES (10339, 19267,"
				+ memberId
				+ ", '13900000001', 34247, '201608044007781007781007781', '4', '0', 20.0000, 2, 0, 0, '2016-8-4 11:15:35', NULL, 'N', '0')";
		ins5 = "INSERT INTO T_KUKO_COIN_DETAILS (ID, MEMBER_ID, LOGIN_NAME, FLAG, AMOUNT_TYPE, AMOUNT, BALANCE, REMARK, CREATOR, CREATE_DATE, "
				+ "ACCOUNT_TYPE, ORDER_ID) VALUES (4456709, "
				+ memberId
				+ ", '13900000001', '10', 2, 20.00, 0.00, '秒杀中考题的诀窍', NULL, '2016-8-4 11:15:35', '2', 34247)";
		ins6 = "INSERT INTO T_MEMBER_COURSE (member_id, course_id, lession_id, unit) VALUES ("
				+ memberId + ", 515, NULL, 1)";
		ins7 = "INSERT INTO T_MEMBER_COMMODITY (member_id, commodity_id, TEACHER_ID, BUY_TIME) VALUES ("
				+ memberId + ", 456, 59, '2016-8-4 11:16:29')";
		DBHelper.executeNonQuery("ikukocesi", ins1);
		DBHelper.executeNonQuery("ikukocesi", ins2);
		DBHelper.executeNonQuery("ikukocesi", ins3);
		DBHelper.executeNonQuery("ikukocesi", ins4);
		DBHelper.executeNonQuery("ikukocesi", ins5);
		DBHelper.executeNonQuery("ikukocesi", ins6);
		DBHelper.executeNonQuery("ikukocesi", ins7);
		logger.info("插入用户的购买课程信息完成");

		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");

	}

	@Test(dataProvider = "testData")
	public void testCase(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json").replace("{memberId}", memberId)),
				data.get("expect"));
		i = i + 1;
		if (i == 1) {
			// 获取订单信息
			String orderNo;
			orderNo = UnirestUtil.GetJsonValue(
					UnirestUtil.responseBody.toString(), "data.orderNo");
			// 取消订单，以便重复执行
			UnirestUtil.PostJson("http://mobile.kaike.la/app/api/request.do",
					StrToMap("msgId:11004"), StrToJson("orderNo:" + orderNo
							+ ",memberId:" + memberId));
		}

	}
}
