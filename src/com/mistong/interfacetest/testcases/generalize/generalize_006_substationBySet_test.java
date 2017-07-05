package com.mistong.interfacetest.testcases.generalize;

import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口     /api/request.do?msgId=18002
 * @描述         手动设置分站站点
 * @author  YY
 * 
 */
public class generalize_006_substationBySet_test extends BaseParpare {
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(generalize_006_substationBySet_test.class.getName());

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
		//进行接口测试，并验证测试结果
//		String res = UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
//				StrToJson(data.get("json")), data.get("expect")).toString();
		
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));
		
	}
}
