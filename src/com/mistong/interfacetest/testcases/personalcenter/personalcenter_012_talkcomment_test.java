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
 * @接口 /api/request.do?msgId=05012
 * @描述 评论
 * @author wudingfei
 * 
 */
public class personalcenter_012_talkcomment_test extends BaseParpare {
	String memberId = null;
	String talkId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(personalcenter_012_talkcomment_test.class.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info("清除说说及评论，请等待......");
		String del1, del2;
		del1 = "delete from T_CMT_TALK WHERE content='test123'";
		del2 = "delete from T_CMT_TALK_COMMENT where talk_ID in (select t2.id from T_CMT_TALK t2 WHERE t2.CONTENT = 'test123')";
		DBHelper.executeNonQuery("ikukocesi", del2);
		DBHelper.executeNonQuery("ikukocesi", del1);
		logger.info("清除说说及评论完成");
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
		// 发表说说
		UnirestUtil.PostJson("http://10.0.0.224:4080/app/api/request.do",
				StrToMap("msgId:05013"), StrToJson("memberId:" + memberId
						+ ",content:test123,image:null,type:0"));
		// 获取说说ID
		String qsql2 = "select id from T_CMT_TALK where content = 'test123'";
		ResultSet qrs2 = DBHelper.executeQuery("ikukocesi", qsql2);
		try {
			while (qrs2.next()) {
				talkId = qrs2.getString(1);
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
				StrToJson(data.get("json").replace("{memberId}", memberId)
						.replace("{talkId}", talkId)), data.get("expect"));
	}
}
