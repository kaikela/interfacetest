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
 * @接口 /api/request.do?msgId=12012
 * @描述  主题帖发表（校园那些事、师生关系圈、兴趣交流圈）
 * 备注	主题帖发表（校园2期）新增
 * @author yy
 * 
 */

public class circle_012_publishtopic_test extends BaseParpare {
	String memberId = null;
	int i = 0 ;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(circle_012_publishtopic_test.class
			.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
//		// 查询用户的memberId
//		String qsql1 = "select id from T_MEMBER where MOBILE_NO= '13900000001'";
//		ResultSet qrs1 = DBHelper.executeQuery("ikukocesi", qsql1);
//		try {
//			while (qrs1.next()) {
//				memberId = qrs1.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

//		logger.info("清除主题帖数据，请等待......");
//		String del1;
//		del1 = "delete from T_CIRCLE_TOPIC where TITLE = '接口测试12012发表主题帖'";
//		DBHelper.executeNonQuery("ikukocesi", del1);
		logger.info("清除主题帖数据完成");
		
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
	}

	@Test(dataProvider = "testData")
	public void testCase(Map<String, String> data) {
		/**
		 * 进行接口测试，并验证测试结果
		 */
		// 进行用户登录操作
//		UnirestUtil
//				.PostJson(
//						"http://auth.kaike.la/auth/backend_login.do",
//						StrToMap("loginName:654321,password:e10adc3949ba59abbe56e057f20f883e"),
//						StrToJson("null"));	
		i = i+1;
		logger.info("12012接口第"+i+"个case进行请求:**********************************************");
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
//				StrToJson(data.get("json").replace("{memberId}", memberId)),
				StrToJson(data.get("json")),
				data.get("expect"));
	}
}
