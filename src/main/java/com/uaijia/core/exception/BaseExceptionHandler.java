package com.uaijia.core.exception;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class BaseExceptionHandler implements HandlerExceptionResolver{
	
	private Logger logger = Logger.getLogger(this.getClass()); 

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();  
	    model.put("ex", ex); 
		
	    logger.info(ex.getMessage(),ex);
	    
	    
	    try {
	    	PrintWriter writer = response.getWriter();
	    	writer.format("utf-8", null);
	    	writer.write("{'code':'500','message':'系统异常'}");
	    	writer.flush();
	    	return null;
	    } catch (Exception e) {
	    }finally {
		}
	    
	    return new ModelAndView("index"); 
	}
	
	

}
