package com.mistong.interfacetest.testcases.personalcenter;

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
 * @接口 /api/request.do?msgId=05011
 * @描述 点赞
 * @author wudingfei
 * 
 */
public class personalcenter_011_thumbup_test extends BaseParpare {
	String memberId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(personalcenter_011_thumbup_test.class.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info("清除说说的点赞数，请等待......");
		String del1;
		del1 = "delete from T_CMT_TALK_PRAISE where TALK_ID = '7700'";
		DBHelper.executeNonQuery("ikukocesi", del1);
		logger.info("清除说说的点赞数完成");
		logger.info("更新说说的点赞数开始，请等待......");
		String updataSql;
		updataSql = "UPDATE T_CMT_TALK set PRAISE_COUNT = '0' where id = '7700'";
		DBHelper.executeNonQuery("ikukocesi", updataSql);
		logger.info("更新说说的点赞数完成");
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
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
	}

	@Test(dataProvider = "testData")
	public void testCase(Map<String, String> data) {
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json").replace("{memberId}", memberId)),
				data.get("expect"));
	}
}
