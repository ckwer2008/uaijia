package com.uaijia.core.util;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static final String PROP_FILE_NAME		 = "/app.properties";
	
	static final Logger logger = LogManager.getLogger(PropertiesUtils.class);
	
	private static Properties prop;
	
	static{
		try{
			prop = new Properties();
			prop.load(ResourceUtil.loadResource(PROP_FILE_NAME));
		}catch(Exception e){
			logger.error("载入配置资源失败",e);
		}
	}
	
	public static String getProperty(String key){
		if(prop != null){
			return prop.getProperty(key);
		}else{
			return null;
		}
	}
	
}
