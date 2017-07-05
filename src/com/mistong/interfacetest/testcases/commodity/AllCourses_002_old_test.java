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
 * @接口     /api/request.do?msgId=03001
 * @描述               所有课程(废弃接口) 兼容之前老版本
 * @author  YY
 *
 */
public class AllCourses_002_old_test extends BaseParpare {
	String memberId = null;
	int i = 0 ;
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(AllCourses_002_old_test.class
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
		/**
		 * 进行接口测试，并验证测试结果
		 */
		
		UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
				StrToJson(data.get("json")), data.get("expect"));
		
		
		
//		i = i + 1;
//		/********************************************************************************
//		 ********************* 如果接口返回的success为true,则对其返回的具体内容进行校验******************
//		 ********************************************************************************/
//		if (i == 1) {
//			String sqlStr;
//			sqlStr = "SELECT IF (( SELECT COUNT(1) FROM T_COMMODITY_LESSION_FREE t5 WHERE t5.COMMODITY_ID = t.id ) = 0, 'false', 'true' ) AS freeFlag,"
//					+ " cast(t3.BASE_PRICE AS SIGNED) AS suggestedPrice, ( t2.FACT_COUNT + t2.VIRTUAL_COUNT ) AS numOfLearned, t4. NAME AS teacherName, "
//					+ "'0' AS courseBuyStatus, t. NAME AS name, t.id FROM T_COMMODITY t, T_COMMODITY_STUDY_STATIS t2, T_COMMODITY_PRICE t3, T_TEACHER t4 "
//					+ "WHERE t.id = t2.commodity_id AND t.id = t3.commodity_id AND t.TEACH_ID = t4.ID AND t.SUBJECT_ID = 3 AND t.grade = '0000000001110000' "
//					+ "AND t.deleted = 'N' AND t.PLAY_TYPE = '1' ORDER BY suggestedPrice,id";
//			UnirestUtil.checkArrJsonData("ikukocesi", "commodityInfo", sqlStr);
//		}
	}
}
