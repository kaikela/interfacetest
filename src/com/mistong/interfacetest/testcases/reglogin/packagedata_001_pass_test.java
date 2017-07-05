package com.mistong.interfacetest.testcases.reglogin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口     /api/request.do?msgId=01005
 * @描述               根据年级获取套餐数据
 * @author  wudingfei
 *
 */
public class packagedata_001_pass_test extends BaseParpare {
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(packagedata_001_pass_test.class
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
	public void registerSuccessFunction(Map<String, String> data) {
		// 进行用户登录操作
		UnirestUtil
				.PostJson(
						"http://mobile.kaike.la/auth/backend_login.do",
						StrToMap("loginName:13900000001,password:e10adc3949ba59abbe56e057f20f883e"),
						StrToJson("null"));
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));
	}

}
