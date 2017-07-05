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
 * @接口 /api/request.do?msgId=01010
 * @描述 忘记密码获取短信验证码获取
 * @author wudingfei
 * 
 */
public class forgetpassword_001_verificationCode_test extends BaseParpare {
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(forgetpassword_001_verificationCode_test.class.getName());

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
		String del1;
		del1 = "delete from T_SEND_SMS_LOG where MOBILE_NO = '13900000001'";
		DBHelper.executeNonQuery("kaikelacesi_stat", del1);
		logger.info("清除数据完成");
	}

	@Test(dataProvider = "testData")
	public void registerSuccessFunction(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));
	}

}
