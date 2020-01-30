package com.notary.utils;

import java.util.Hashtable;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	private static Hashtable<String, Properties> pptContainer = new Hashtable<String, Properties>();
	private static Logger _log = Logger.getLogger(PropertiesUtil.class);
	
	/**  
	*   
	* 方法用途和描述: 获得属性     
	* @param propertyFilePath 属性文件(包括类路径)  
	* @param key  属性键  
	* @return 属性值  
	* @author jinxiaochen 新增日期：2010-12-08 
	* @author jinxiaochen 修改日期：2010-12-08  
	*/  
	public final static String getValue(String propertyFilePath, String key) {
		Properties ppts = getProperties(propertyFilePath);
		return ppts == null ? null : ppts.getProperty(key);
	}
	
	
	/**  
	*   
	* 方法用途和描述: 获得属性文件的属性   
	*   
	* @param propertyFilePath 属性文件(包括类路径)  
	* @return 属性  
	* @author jinxiaochen 新增日期：2010-12-08  
	* @author jinxiaochen 修改日期：2010-12-08  
	*/  
	public final static Properties getProperties(String propertyFilePath) {
		if (propertyFilePath == null) {   
			_log.error("propertyFilePath is null!");
			return null;   
		}   
		Properties ppts = pptContainer.get(propertyFilePath);   
		if (ppts == null) {   
			ppts = loadPropertyFile(propertyFilePath);   
			if (ppts != null) {   
				pptContainer.put(propertyFilePath, ppts);   
			}   
		}   
		return ppts;   
	}
	
	/**  
	 *   
	 * 方法用途和描述: 加载属性   
	 *   
	 * @param propertyFilePath  属性文件(包括类路径)  
	 * @return 属性  
	 * @author jinxiaochen 新增日期：2010-12-08  
	 * @author jinxiaochen 修改日期：2010-12-08 
	 */  
	private static Properties loadPropertyFile(String propertyFilePath) {   
		java.io.InputStream is = PropertiesUtil.class.getResourceAsStream(propertyFilePath);   
		if (is == null) {   
			return loadPropertyFileByFileSystem(propertyFilePath);   
		}   
		Properties ppts = new Properties();   
		try {   
			ppts.load(is);   
			return ppts;   
		} catch (Exception e) {   
			_log.debug("加载属性文件出错:" + propertyFilePath, e);
			return null;   
		}   
	}
	
	/**  
	 *   
	 * 方法用途和描述: 从文件系统加载属性文件   
	 *   
	 * @param propertyFilePath 属性文件(文件系统的文件路径)  
	 * @return 属性  
	 * @author jinxiaochen 新增日期：2010-12-08  
	 * @author jinxiaochen 修改日期：2010-12-08  
	 */  
	private static Properties loadPropertyFileByFileSystem(final String propertyFilePath) {   
		try {   
			Properties ppts = new Properties();   
			ppts.load(new java.io.FileInputStream(propertyFilePath));   
			return ppts;   
		} catch (java.io.FileNotFoundException e) {   
			_log.error("FileInputStream(\"" + propertyFilePath   
					+ "\")! FileNotFoundException: " + e);   
			return null;   
		} catch (java.io.IOException e) {   
			_log.error("Properties.load(InputStream)! IOException: " + e);   
			return null;   
		}   
	}
}
