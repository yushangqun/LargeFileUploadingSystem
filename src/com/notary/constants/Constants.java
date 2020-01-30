package com.notary.constants;

import java.io.File;

public class Constants {
	
	public final static String ENCODE = "UTF-8";
	
	public final static String MEMBER_SESSION_ADMIN = "currentMember";
	
	public final static String VERIFICATION_CODE = "checkCode";
	
	public final static String MESSAGE_PROPERTY_PATH = "/message.properties";
	
	public final static String SQL_ABSENCE_PATH = "/sql.properties";
	
	public final static String CONFIG_PROPERTY_PATH = "/config.properties";
	
	public static final String UPLOAD_PATH = File.separator + "upload";
	
	public final static int PAGE_SIZE = 20;
	
//	public final static String LOG_PATH = "../logs/fee";
	
	//seed
	public final static String SEED = "{B44E1DE3-330F-4F31-B596-BFC876EFBE7E}";
	
	//检验数据完整性的KEY
	public final static String CHECK_POST_SEED = "#1%4^4@2&#3*^^2%";
	
	//code
	public final static int NO_ERROR = 1000;
	public final static int ERROR_DB = NO_ERROR + 1;	//数据库异常
	public final static int ERROR_VERIFYCODE_TIMEOUT = NO_ERROR +2;	//校验码过期
	public final static int ERROR_VERIFYCODE_ERROR = NO_ERROR + 3;	//身份校验错误
	public final static int ERROR_SEARCHTYPE_ERROR = NO_ERROR + 4;	//查询类型错误
	public final static int ERROR_SERVER_ERROR = NO_ERROR + 5;	//服务器异常
	public final static int ERROR_NO_RESULT = NO_ERROR + 6;		//查询无结果
	public final static int ERROR_SYNC_ERROR = NO_ERROR + 7;	//同步出错，要求接口方重发数据
	public final static int ERROR_CERT_NOT_EXISTS = NO_ERROR + 8;	
	public final static int ERROR_NO_COMPANY = NO_ERROR + 9;	//没有该用户单位
	public final static int ERROR_NO_COMPANY_MOBILE = NO_ERROR + 10;	//该用户单位无此手机号
	public final static int ERROR_NO_SEARCH_TIMES_LIMITS = NO_ERROR + 11;	
	
	//查询分类等变量申明
	public final static String SEARCH_TYPE_PROCESS = "1";
	public final static String SEARCH_TYPE_BOOK = "2";	
	public final static String SEARCH_SOURCE_USER = "1";	//查询来源:普通用户
	public final static String SEARCH_SOURCE_COMPANY = "2";	//查询来源:单位用户
	public final static String IS_PASSVERIFY_PASS = "1";	//是否通过校验码验证:通过
	public final static String IS_PASSVERIFY_NOT_PASS = "2";	//是否通过校验码验证:未通过
	
	
	
}
