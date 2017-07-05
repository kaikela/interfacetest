package com.mistong.interfacetest.testcases.circle;

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
 * @接口 /api/request.do?msgId=12009
 * @描述 主题帖点赞
 * @author wudingfei
 * 
 */

public class circle_009_praisetopic_test extends BaseParpare {
	String memberId = null;
	String topicId = null;
	int i;
	String parentId = null;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(circle_009_praisetopic_test.class
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

		logger.info("清除主题帖相关数据，请等待......");
		String del1, del2;
		del1 = "delete from T_CIRCLE_TOPIC_PROP where date_format(CREATE_DATE,'%y-%m-%d') = curdate() and SOURCE_ID in ("
				+ "select id from T_CIRCLE_TOPIC where date_format(CREATE_DATE,'%y-%m-%d') = curdate() and MEM_ID= "
				+ memberId + ")";
		del2 = "delete from T_CIRCLE_TOPIC where date_format(CREATE_DATE,'%y-%m-%d') = curdate() and MEM_ID= "
				+ memberId;
		DBHelper.executeNonQuery("ikukocesi", del1);
		DBHelper.executeNonQuery("ikukocesi", del2);
		logger.info("清除主题帖相关数据完成");
		// 发布主题贴
		UnirestUtil
				.PostJson(
						"http://10.0.0.224:4080/app/api/request.do",
						StrToMap("msgId:12007"),
						StrToJson("circleId:13,title:test,content:testtesttesttesttest,imgUrl:null,circleType:2"
								+ ",memberId:" + memberId));
		// 查询主题帖的topicId
		String qsql2 = "select id from T_CIRCLE_TOPIC where TITLE = 'test' and MEM_ID ='"
				+ memberId + "'";
		ResultSet qrs2 = DBHelper.executeQuery("ikukocesi", qsql2);
		try {
			while (qrs2.next()) {
				topicId = qrs2.getString(1);
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
						.replace("{topicId}", topicId)), data.get("expect"));

	}
}
