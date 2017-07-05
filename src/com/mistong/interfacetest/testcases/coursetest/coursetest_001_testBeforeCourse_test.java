package com.mistong.interfacetest.testcases.coursetest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.base.BaseParpare;
import com.mistong.interfacetest.util.UnirestUtil;

/**
 * 接口   /api/request.do?msgId=14001
 *    触发课前诊断  获取课前诊断内容
 * @author YY
 *
 */

public class coursetest_001_testBeforeCourse_test extends BaseParpare{
//	String jsonObject;
	//日志初始化   输出log
	static Logger logger = Logger.getLogger(coursetest_001_testBeforeCourse_test.class.getName());
	
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
		// 进行用户登录操作
//		UnirestUtil
//				.PostJson(
//						"http://mobile.kaike.la/auth/backend_login.do",
//						StrToMap("loginName:yy0011,password:e10adc3949ba59abbe56e057f20f883e"),
//						StrToJson("null"));	
		//进行接口测试,并验证测试结果
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")), StrToJson(data.get("json")), data.get("expect"));
		
	}

}
