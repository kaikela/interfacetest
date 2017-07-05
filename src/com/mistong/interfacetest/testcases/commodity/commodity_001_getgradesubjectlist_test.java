package com.mistong.interfacetest.testcases.commodity;

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
 * @接口     /api/request.do?msgId=03004
 * @描述               年级科目(筛选条件) 入参memberId为空,下发所有课程的筛选条件
 * 										 入参memberId不为空,下发我的课程的筛选条件
 * @author  wudingfei
 *
 */
public class commodity_001_getgradesubjectlist_test extends BaseParpare {
	String memberId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(commodity_001_getgradesubjectlist_test.class
			.getName());

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
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		String jsonString = data.get("json");

			if (jsonString.contains("{memberId}")) {
				if (memberId == null ||memberId.isEmpty()) {
					logger.error("数据获取异常,请检查执行replace方法传入数据的有效性...");
				} else {
					UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
							StrToJson(jsonString.replace("{memberId}", memberId)),
							data.get("expect"));
				}
			} else {
				UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
						StrToJson(data.get("json")),
						data.get("expect"));
			}
		
	}
}
