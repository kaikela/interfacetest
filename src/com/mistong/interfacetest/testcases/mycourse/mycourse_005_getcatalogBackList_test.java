package com.mistong.interfacetest.testcases.mycourse;

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
 * @接口     /api/request.do?msgId=20005
 * @描述               获取课程包列表
 * @author  YY
 * 
 */
public class mycourse_005_getcatalogBackList_test extends BaseParpare {
	String memberId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(mycourse_005_getcatalogBackList_test.class.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
		
		String qsql1 = "select id from T_MEMBER where MOBILE_NO= '13900000001'";
		// 查询
		ResultSet qrs1 = DBHelper.executeQuery("ikukocesi", qsql1);
		// 输出
		try {
			while (qrs1.next()) {
				memberId = qrs1.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test(dataProvider = "testData")
	public void testCase(Map<String, String> data) {
		
		// 进行用户登录操作
		UnirestUtil
				.PostJson(
						"http://auth.kaike.la/auth/backend_login.do",
						StrToMap("loginName:yy0011,password:e10adc3949ba59abbe56e057f20f883e"),
						StrToJson("null"));		
		
		/*
		 * 进行接口测试，并验证测试结果
		 */
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")),
				data.get("expect"));
	}
}
