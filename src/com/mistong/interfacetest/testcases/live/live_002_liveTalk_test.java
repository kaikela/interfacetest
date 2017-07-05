package com.mistong.interfacetest.testcases.live;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mistong.interfacetest.base.BaseParpare;
import com.mistong.interfacetest.util.UnirestUtil;

/**
 * 接口   /api/request.do?msgId=03011
 * 描述  直播课聊天室参数
 * @author YY
 *
 */
public class live_002_liveTalk_test extends BaseParpare {
	
	//日志初始化   输出log
	static Logger logger = Logger.getLogger(live_002_liveTalk_test.class.getName());
	
	@BeforeClass
	/**
	 * 接口测试前,模块数据初始化
	 */
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum+ "接口测试开始");
				
	}
	
	@Test(dataProvider = "testData")
	private void testcase(Map<String, String>data) {
		// TODO Auto-generated method stub
		//进行接口测试,并验证测试结果
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")), StrToJson(data.get("json")), data.get("expect"));
	}
	
	
}
