package com.uaijia.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 资源工具类
 * @author jmy
 *
 */
public abstract class ResourceUtil {	
	
	/**
	 * 以输入流的形式载入资源
	 * @param resourceName
	 * @return
	 */
	public static InputStream loadResource(String resourceName){
		return ResourceUtil.class.getResourceAsStream(resourceName);
	}
	
	/**
	 * 获取Jar包外部资源，相对于程序运行目录。
	 * @param resourceName
	 * @return
	 * @throws java.io.FileNotFoundException
	 */
	public static InputStream loadOuterResource(String resourceName) throws FileNotFoundException{
		String filename = getUserDir() + resourceName;
		return new FileInputStream(filename);
	}
	
	private static String getUserDir(){
		return System.getProperty("user.dir") + File.separatorChar;
	}
	
	/**
	 * 获取路径的真实路径
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path){
		return ResourceUtil.class.getResource("/") + path;
	}
	
}
