package com.mistong.interfacetest.testcases.reglogin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.util.DBHelper;
import com.mistong.interfacetest.util.UnirestUtil;
import com.mistong.interfacetest.base.BaseParpare;

/**
 * @接口 /api/request.do?msgId=01006
 * @描述 确认选择科目
 * @author wudingfei
 * 
 */
public class choosesubject_001_confirm_test extends BaseParpare {
	// 输出本页面日志 初始化
	static Logger logger = Logger
			.getLogger(choosesubject_001_confirm_test.class.getName());

	@BeforeClass
	/**接口测试前准备工作*/
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum
				+ "接口测试开始");
		logger.info("清除更新用户数据开始，请等待");
		String del1, del2, updataSql;
		del1 = "delete from T_CARD_MEMBER_BIND where CARD_NO = '201560001512'";
		del2 = "delete from T_CARD_PKG_BIND where CARD_NO = '201560001512'";
		updataSql = "update T_CARD_DETAIL set STATUS = 1 where CARD_NO= '201560001512'";
		DBHelper.executeNonQuery("ikukocesi", del1);
		DBHelper.executeNonQuery("ikukocesi", del2);
		DBHelper.executeNonQuery("ikukocesi", updataSql);
		logger.info("清除更新用户数据完成");
	}

	@Test(dataProvider = "testData")
	public void registerSuccessFunction(Map<String, String> data) {
		// 进行用户登录操作
		UnirestUtil
				.PostJson(
						"http://mobile.kaike.la/auth/backend_login.do",
						StrToMap("loginName:13900000001,password:e10adc3949ba59abbe56e057f20f883e"),
						StrToJson("null"));
		/********************************************************************************
		 *********************************** 进行接口测试，并验证测试结果***************************
		 ********************************************************************************/
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				data.get("json"), data.get("expect"));
	}

}
