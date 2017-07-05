package com.mistong.interfacetest.testcases.reglogin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.util.DBHelper;
import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口 /api/request.do?msgId=01001
 * @描述 注册获取短信验证码
 * @author wudingfei
 * 
 */
public class register_001_verificationCode_test extends BaseParpare {
	// 输出本页面日志 初始化
	int i = 0 ;
	static Logger logger = Logger
			.getLogger(register_001_verificationCode_test.class.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
		/********************************************************************************
		 ******************************* register接口清除相关历史数据****************************
		 ********************************************************************************/
		logger.info("清除数据开始，请等待");
		String del1, del2, del3, del4, del5, del6, del7, del8, updataSql;
		del1 = "delete from T_SEND_SMS_LOG where MOBILE_NO = '17701326261'";
		del2 = "delete from T_SEND_SMS_LOG where MOBILE_NO = '123456'";
		del3 = "delete from T_SEND_SMS_LOG where MOBILE_NO = 'aaaaa'";
		del4 = "delete from T_MEMBER_IDENTITY where SOURCE_ID in (select id from T_MEMBER where MOBILE_NO ='17701326261')";
		del5 = "delete from T_MEMBER where MOBILE_NO ='17701326261'";
		del6 = "delete from T_SEND_SMS_LOG where MOBILE_NO = '17701326261'";
		del7 = "delete from T_CARD_MEMBER_BIND where CARD_NO = '201560001512'";
		del8 = "delete from T_CARD_PKG_BIND where CARD_NO = '201560001512'";
		updataSql = "update T_CARD_DETAIL set STATUS = 1 where CARD_NO= '201560001512'";
		DBHelper.executeNonQuery("kaikelacesi_stat", del1);
		DBHelper.executeNonQuery("kaikelacesi_stat", del2);
		DBHelper.executeNonQuery("kaikelacesi_stat", del3);
		DBHelper.executeNonQuery("ikukocesi", del4);
		DBHelper.executeNonQuery("ikukocesi", del5);
		DBHelper.executeNonQuery("kaikelacesi_stat", del6);
		DBHelper.executeNonQuery("ikukocesi", del7);
		DBHelper.executeNonQuery("ikukocesi", del8);
		DBHelper.executeNonQuery("ikukocesi", updataSql);
		logger.info("清除数据完成");
	}

	@Test(dataProvider = "testData")
	public void registerSuccessFunction(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		i = i+1;
		System.out.println("第"+i+"次执行");
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));
	}

}
