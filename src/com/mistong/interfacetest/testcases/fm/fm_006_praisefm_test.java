package com.mistong.interfacetest.testcases.fm;

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
 * @接口 /api/request.do?msgId=13006
 * @描述 心理FM点赞
 * @author wudingfei
 * 
 */

public class fm_006_praisefm_test extends BaseParpare {
	String memberId = null;
	String topicId = null;
	int i;
	String parentId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(fm_006_praisefm_test.class
			.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		// 查询用户的memberId
		String qsql1 = "select id from T_MEMBER where MOBILE_NO= '13900000001'";
		ResultSet qrs1 = DBHelper.executeQuery("ikukocesi", qsql1);
		try {
			while (qrs1.next()) {
				memberId = qrs1.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		logger.info("清除FM点赞数据，请等待......");
		String del1;
		del1 = "delete from fm_up_info where date_format(CREATE_DATE,'%y-%m-%d') = curdate() and member_id = "
				+ memberId;
		DBHelper.executeNonQuery("ikukocesi", del1);
		logger.info("清除FM点赞数据完成");

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
