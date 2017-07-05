package com.mistong.interfacetest.base;

/**
 * 测试开始 和 测试结束 的操作
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.mistong.interfacetest.util.ExcelDataProvider;
import com.mistong.interfacetest.util.LogConfiguration;
import com.mistong.interfacetest.util.PropertiesDataProvider;

public class BaseParpare {
	
	// 输出本页面日志 初始化
	static Logger logger = Logger.getLogger(BaseParpare.class.getName());
	// 添加成员变量来获取beforeClass传入的context参数
	protected ITestContext testContext = null;
	public static String driverConfgFilePath = null;
	public static String url = null;
	public static String login_url = null;
	public static String moduleName = null; // 模块的名字
	public static String functionName = null; // 功能的名字
	public static String caseNum = null; // 用例编号

	@BeforeTest
	public void beforeTest(ITestContext context) {
		LogConfiguration.initLog(getModuleName(),this.getClass().getSimpleName());
		// 从testNG的配置文件读取参数driverConfgFilePath的值
		driverConfgFilePath = context.getCurrentXmlTest().getParameter(
				"driverConfgFilePath");
		url = PropertiesDataProvider.getTestData(driverConfgFilePath, "URL");
		login_url = PropertiesDataProvider.getTestData(driverConfgFilePath,
				"LoginURL");
		logger.info("====================================接口测试开始====================================");
	}

	@AfterClass
	/**接口测试完成后需要做的相关工作*/
	public void endTest() {
		logger.info(moduleName + "模块" + functionName + "功能" + caseNum + "接口测试完毕");
	}

	@AfterTest
	/**接口测试完成后需要做的相关工作*/
	public void AfterTest() {
		logger.info("====================================接口测试完毕====================================");
	}

	/**
	 * 测试数据提供者 - 方法
	 * */
	@DataProvider(name = "testData")
	public Iterator<Object[]> dataFortestMethod() throws IOException {
		// 将模块名称和用例的编号传给 ExcelDataProvider ，然后进行读取excel数据
		return new ExcelDataProvider(moduleName+"/"+functionName, caseNum);
	}

	/**
	 * 获取模块名
	 * 
	 * @return
	 */
	public String getModuleName() {
		String className = this.getClass().getName();
		String moduleName,startStr;
		int lastDotIndexNum,secondLastDotIndexNum;
		startStr = "testcases.";
		lastDotIndexNum = className.lastIndexOf("."); // 取得最后一个.的index
		secondLastDotIndexNum = className.indexOf(startStr) + startStr.length();
        moduleName = className.substring(secondLastDotIndexNum,
        		lastDotIndexNum); // 取到模块的名称
		return moduleName;
	}

	/**
	 * 获取功能名
	 * 
	 * @return
	 */
	public String getFunctionName() {
		String className = this.getClass().getName();
		int underlineIndexNum = className.indexOf("_"); // 取得第一个_的index
		if (underlineIndexNum > 0) {
			functionName = className.substring(className.lastIndexOf(".") + 1,
					underlineIndexNum); // 取到模块的名称
		}
		return functionName;
	}

	/**
	 * 获取用例编号
	 * */
	public String getCaseNum() {
		String className = this.getClass().getName();
		int underlineIndexNum = className.indexOf("_"); // 取得第一个_的index
		if (underlineIndexNum > 0) {
			caseNum = className.substring(underlineIndexNum + 1,
					underlineIndexNum + 4); // 取到用例编号
		}
		return caseNum;
	}

	/**
	 * 此方法用于解析excel文件中配置的复杂字符串，以便转换为json字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String CompStrToJson(String str) {
		if (str.indexOf(",") > 0) {
			str = str.replace(",", "\",\"");
		}
		str = "{\"" + str.replace(":", "\":\"") + "\"}";
		if (str.contains(":\"{")) {
			str = str.replace(":\"{", ":{\"");
		}
		if (str.contains(":\"{")) {
			str = str.replace(":\"{", ":{\"");
		}
		if (str.contains(":\"[{")) {
			str = str.replace(":\"[{", ":[{\"");
		}
		if (str.contains("}]}\"}")) {
			str = str.replace("}]}\"}", "\"}]}}");
		}
		return str;
	}

	/**
	 * 此方法用于解析excel文件中配置的字符串，以便转换为json字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String StrToJson(String str) {
		String[] temp = str.split("[,:]");
		JSONObject jsonObj = new JSONObject();
		for (int i = 0; i < temp.length / 2; i++) {
			if (temp[(2 * i) + 1].equals("null")) {
				jsonObj.put(temp[2 * i], "");
			} else {
				jsonObj.put(temp[2 * i], temp[(2 * i) + 1]);
			}
		}
		return jsonObj.toString();
	}

	/**
	 * 此方法用于解析excel文件中配置的字符串，以便转换为map类型的queryString字符串
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, Object> StrToMap(String str) {
		String[] temp = str.split("[,:]");
		Map<String, Object> parameters = new HashMap<String, Object>();

		for (int i = 0; i < temp.length / 2; i++) {
			if (temp[(2 * i) + 1].equals("null")) {
				parameters.put(temp[2 * i], "");
			} else {
				parameters.put(temp[2 * i], temp[(2 * i) + 1]);
			}
		}
		return parameters;
	}
	
	/**
	 * 将json字符串转换成JSONObject对象
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject convertJson(String jsonStr) {
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		return jsonObj;
		
	}
}
