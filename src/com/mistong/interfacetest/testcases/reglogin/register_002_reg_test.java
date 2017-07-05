package com.mistong.interfacetest.testcases.reglogin;

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
 * @接口 /api/request.do?msgId=01003
 * @描述 注册
 * @author wudingfei
 * 
 */
public class register_002_reg_test extends BaseParpare {
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(register_002_reg_test.class
			.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
	}

	@Test(dataProvider = "testData")
	public void registerSuccessFunction(Map<String, String> data)
			throws SQLException {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		logger.info("清除更新用户数据开始，请等待");
		String del1, del2, del3, del4, del5, updataSql;
		del1 = "delete from T_MEMBER_IDENTITY where SOURCE_ID in (select id from T_MEMBER where MOBILE_NO ='13900000001')";
		del2 = "delete from T_MEMBER where MOBILE_NO ='13900000001'";
		del3 = "delete from T_SEND_SMS_LOG where MOBILE_NO = '13900000001'";
		del4 = "delete from T_CARD_MEMBER_BIND where CARD_NO = '201560001512'";
		del5 = "delete from T_CARD_PKG_BIND where CARD_NO = '201560001512'";
		updataSql = "update T_CARD_DETAIL set STATUS = 1 where CARD_NO= '201560001512'";
		DBHelper.executeNonQuery("ikukocesi", del1);
		DBHelper.executeNonQuery("ikukocesi", del2);
		DBHelper.executeNonQuery("kaikelacesi_stat", del3);
		DBHelper.executeNonQuery("ikukocesi", del4);
		DBHelper.executeNonQuery("ikukocesi", del5);
		DBHelper.executeNonQuery("ikukocesi", updataSql);
		logger.info("清除更新用户数据完成");
		logger.info("对用户发送验证码，请稍后");
		UnirestUtil.PostJson(url, StrToMap("msgId:01001"),
				StrToJson("mobileNo:13900000001"));
		logger.info("验证码发送完成");
		logger.info("获取验证码，请稍后");
		String qsql1 = "select left(right(substring_index(t.REQUEST,'[',1),11),6) from T_SEND_SMS_LOG t where t.MOBILE_NO = '13900000001'";
		// 查询
		ResultSet qrs1 = DBHelper.executeQuery("kaikelacesi_stat", qsql1);
		// 输出
		String verificationCode = null;
		try {
			while (qrs1.next()) {
				// System.out.println("验证码为：" + qrs1.getString(1));
				verificationCode = qrs1.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("获取验证码完毕，验证码为：" + verificationCode);
		// 关闭
		DBHelper.free(qrs1);

		UnirestUtil
				.InterfaceTest(url, StrToMap(data.get("msgId")), StrToJson(data
						.get("json").replace("{Code}", verificationCode)), data
						.get("expect"));
	}

}
