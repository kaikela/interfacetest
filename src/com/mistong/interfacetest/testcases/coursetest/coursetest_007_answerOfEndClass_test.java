package com.mistong.interfacetest.testcases.coursetest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mistong.interfacetest.base.BaseParpare;
import com.mistong.interfacetest.util.DBHelper;
import com.mistong.interfacetest.util.UnirestUtil;

/**
 * 接口   /api/request.do?msgId=14007
 * 
 * 描述    请求课后考试习题答案
 * @author YY
 *
 */

public class coursetest_007_answerOfEndClass_test extends BaseParpare{

	//日志初始化   输出log
	static Logger logger = Logger.getLogger(coursetest_007_answerOfEndClass_test.class.getName());
	
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
//		
//		DBHelper.executeNonQuery("ikukocesi", del1);
//		DBHelper.executeNonQuery("ikukocesi", del2);
		
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum+ "接口测试开始");
				
	}
	
	@Test(dataProvider = "testData")
	private void testcase(Map<String, String>data) {
		// TODO Auto-generated method stub
		//进行接口测试,并验证测试结果
		//提交课后诊断答案,解析responbody,并且解析得到batchId参数
//		 String reString = UnirestUtil.PostJson("http://mobile.kaike.la/app/api/request.do", 
//				 StrToMap("{'memberId':'3071734','lessonId':'88','startTime':'1483951871475','endTime':'1483951889652','userAnswers':''}"),
//				 StrToJson("14006")).toString();
//		 String value = UnirestUtil.GetJsonValue(reString, "data.batchId");
//		 System.out.println("参数:batchId"+":"+value);
//		 if (value==null||value.isEmpty()){
//			 logger.error("数据获取异常,请检查执行replace方法传入数据的有效性...");
//		} else {
		 	UnirestUtil.InterfaceTest(url, StrToMap(data.get("msgId")),
		 			data.get("json"),
		 			data.get("expect"));
//		}

	}

}
