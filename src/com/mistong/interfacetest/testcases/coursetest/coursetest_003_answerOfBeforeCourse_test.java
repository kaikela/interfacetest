package com.mistong.interfacetest.testcases.coursetest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.base.BaseParpare;
import com.mistong.interfacetest.util.DBHelper;
import com.mistong.interfacetest.util.UnirestUtil;

/**
 * 接口   /api/request.do?msgId=14003
 * 
 * 描述    请求课前诊断答案
 * @author YY
 *
 */

public class coursetest_003_answerOfBeforeCourse_test extends BaseParpare{
//		String courseId ;
	//日志初始化   输出log
	static Logger logger = Logger.getLogger(coursetest_003_answerOfBeforeCourse_test.class.getName());
	
	@BeforeClass
	/**
	 * 接口测试前,模块数据初始化
	 */
	public void startTest(ITestContext context) {
		moduleName = getModuleName();
		functionName = getFunctionName();
		caseNum = getCaseNum();
		
		//清除用户课前诊断答题记录
		logger.info("清除用户课前诊断答题记录，请等待......");
//		String del1, del2;
//		del1 = "DELETE FROM TK_USER_ANSWER_BATCH WHERE TK_USER_ANSWER_BATCH.MEMBER_ID = 3071734 AND TK_USER_ANSWER_BATCH.CATEGORY = 1";
//		del2 = "DELETE FROM TK_USER_ANSWER_REC WHERE TK_USER_ANSWER_REC.MEMBER_ID = 3071734 AND TK_USER_ANSWER_REC.CATEGORY = 1";
//		DBHelper.executeNonQuery("ikukocesi", del1);
//		DBHelper.executeNonQuery("ikukocesi", del2);
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum+ "接口测试开始");
		//查询课程Id
		String sel1 = "SELECT * FROM T_COURSE WHERE TITLE = '初中语文'";
		ResultSet val1 = DBHelper.executeQuery("ikukocesi", sel1);
//		try {
//			courseId = val1.getString(1);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			while (val1.next()) {
//				courseId = val1.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(courseId);
	}
	
	@Test(dataProvider = "testData")
	private void testcase(Map<String, String>data) {
		// TODO Auto-generated method stub
		//进行接口测试,并验证测试结果
		//取的14002接口返回参数batchId
//		 String reString = UnirestUtil.PostJson("http://mobile.kaike.la/app/api/request.do", 
//				 StrToMap("msgId:14002"),
//				 StrToJson("memberId"+":"+"3071734"+","+
//						 "courseId"+":"+"49"+","+
//						 "startTime"+":"+"1483951871475"+","+
//						 "endTime"+":"+"1483951889652"+","+"userAnswers"+":"+"[{'questionId':'2754','answer':'A'},{'questionId':'2755','answer':'B'},{'questionId':'2756','answer':'C'}]"+","+
//						 "sourceType"+":"+"2")).toString();
//		 System.out.println("memberId"+":"+"3071734"+","+
//						 "courseId"+":"+"49"+","+
//						 "startTime"+":"+"1483951871475"+","+
//						 "endTime"+":"+"1483951889652"+","+"userAnswers"+":"+"[{'questionId':'2754','answer':'A'},{'questionId':'2755','answer':'B'},{'questionId':'2756','answer':'C'}]"+","+
//						 "sourceType"+":"+"2");
//		 System.out.println("14002接口返回"+":"+reString);
//		 String value = UnirestUtil.GetJsonValue(reString, "data.batchId");
//		 System.out.println("参数:batchId"+":"+value);
		 //进行解析到的参数或者查询数据库的数据是否为空判断
//		 if (courseId.isEmpty()) {
//			 logger.error("数据获取异常,请检查执行replace方法传入数据的有效性...");		
//		}else {
		 	UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
//		 			StrToJson(data.get("json").replace("{courseId}", courseId).replace("{value}", value)),
		 			StrToJson(data.get("json")),
		 			data.get("expect"));
//		}
	}
}
