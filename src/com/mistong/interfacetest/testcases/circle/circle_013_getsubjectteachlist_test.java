package com.mistong.interfacetest.testcases.circle;


import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口 /api/request.do?msgId=12013
 * @描述  科目老师列表
 * @author wudingfei
 * 
 */

public class circle_013_getsubjectteachlist_test extends BaseParpare {
	String memberId = null;
	String topicId = null;
	int i;
	String parentId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(circle_013_getsubjectteachlist_test.class
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
	public void testCase(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));

	}
}
