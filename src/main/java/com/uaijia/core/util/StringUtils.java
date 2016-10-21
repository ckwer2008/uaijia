package com.uaijia.core.util;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public abstract class StringUtils extends org.apache.commons.lang.StringUtils {
	private final static int[]  Coefficient = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,9, 10, 5, 8, 4, 2 };
    private final static char[] idNumberLast = new char[] { '1', '0', 'X', '9', '8', '7', '6','5', '4', '3', '2' };
	
	public static String createUUID(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str = StringUtils.replace(str, "-", "");
		
		return str;
	}
	
	public static String createTimestamp(){
		Date now = DateUtils.now();
		String r = DateUtils.toString(now, "yyyyMMddHHmmssS");
		r += RandomStringUtils.randomNumeric(4);
		return r;
	}
	
	public static boolean validateEmail(String email){
		return email.matches("^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$");
	}
	
	public static boolean validateCellphone(String cellphone){
		return cellphone.matches("^(13|14|15|17|18|19)\\d{9}$");
	}
	
	//用户名校验
	public static boolean validateNickname(String nickname){
		//校验长度20个字符
/*		if(nickname != null){
			//汉字个数
			int count = nickname.length() - nickname.replaceAll("[\u4e00-\u9fa5]*", "").length();
			if(nickname.length() + count > 20){
				return false;
			}
		}else{
			return false;
		}*/
		//20个汉字或字母（可以包含.）
		return nickname.matches("^[\u4e00-\u9fa5|\uFF00-\uFFFF|.|a-z|A-Z|0-9]{1,20}$");
	}
	
	public static boolean validateMoney(String money){
		return money.matches("^\\d*(\\.[0-9]{1,2}0*){0,1}$");
	}
	
	//统计汉字个数
	public static int countChinaAmount(String china){
		//汉字个数
		int count = 0;
		if(china != null || "".equals(china)){
			count = china.length() - china.replaceAll("[\u4e00-\u9fa5]*", "").length();
		}
		return count;
	}
	
	//校验座机区号
	public static boolean validateTelephoneAreaCode(String areaCode){
		return areaCode.matches("^(0[1-9]{1})([0-9]{1,2})$");
	}
	
	//校验座机主号
	public static boolean validateTelephone(String telephone){
		return telephone.matches("^[0-9]{5,8}$");
	}
	
	//校验座机分机号
	public static boolean validateTelephoneExt(String telephone){
		return telephone.matches("^[0-9]{1,6}$");
	}
	
	//校验拼接电话号码格式，比如0571-88888888-5223
	public static boolean validateTelephoneJoin(String telephone){
		return telephone.matches("^0[0-9]{2,3}-[0-9]{5,8}-[0-9]{1,5}?$");

	}
	
	//数字，14位整数加随意位数小数
	public static boolean validateNumberHaveDecimal(String numberHaveDecimal){
		return numberHaveDecimal.matches("^([0-9]{1,14})(.[0-9]{1,10})?$");
	}
	
	//数字，14位整数
	public static boolean validateNumberNoDecimal(String numberNoDecimal){
		return numberNoDecimal.matches("^([0-9]{1,14})?$");
	}
	public static boolean validateRealName(String realName) {
		return realName.matches("^[\u4e00-\u9fa5]{2,}$");
	}
	
	public static boolean validateIdNumber(String idNumber){
		// TODO 校验身份证号
		//return true;
		if (null == idNumber || idNumber.length() != 18) {
			return false;
		}
		char[] idNumberString = idNumber.toCharArray();
		int sum = 0;
		for (int i = 0; i < idNumberString.length - 1; i++) {
			sum += (idNumberString[i] - '0') * Coefficient[i];
		}
		return idNumberLast[sum % 11] == idNumberString[17];
	}
	
	public static String area (String province,String city,String districts) {
		
		StringBuffer address  = new StringBuffer();

		if(province!=null){
		address.append(province+"-");
		}

		if(city!=null){
		address.append(city+"-");
		}
		
		if(districts!=null){
		address.append(districts);
		}
		
		return address.toString();
	}
	
	public static String telePhone (String areaNumber,String phoneNumber,String ext) {
		
		StringBuffer telePhone  = new StringBuffer();

		if(areaNumber!=null){
			telePhone.append(areaNumber+"-");
		}

		if(phoneNumber!=null){
			telePhone.append(phoneNumber+"-");
		}
		
		if(ext!=null){
			telePhone.append(ext);
		}
		
		return telePhone.toString();
	}
	
	/*身份证打码42324456665454*****/
	public static String markIdNumber(String idNumber){
	   if(!StringUtils.isEmpty(idNumber)){
		   idNumber = idNumber.substring(0, idNumber.length()-4) + "****";
	   }
	   return idNumber;
	}
	
	public static String formatToHtml(String html){
		html = html.replace("<", "&lt;");
		html = html.replace(">", "&gt;");
		html = html.replace("\"", "&quot;");

		html = html.replace("\n", "<p>");
		html = html.replace(" ", "&nbsp;");
		html = html.replace("　", "&nbsp;");
		
		return html;
	}

    public static boolean validateCommonString(String str){
        return str.matches("^[\\w\\u4e00-\\u9fa5]+$");
    }

    public static String filterForXSS(String str){
        str = str.replace("<","&lt;");
        str = str.replace(">","&gt;");
        return str;
    }
    
    public static int stringToInteger(String str){
    	return Integer.parseInt(str);
    }
    
    /**
     * 将手机号码第4-7位用*号代替
     * eg：158****5436
     * @param cellphone
     * @return cellphone为空或格式不对均返回null
     */
    public static String cellphoneAddStar(String cellphone){
    	if(cellphone == null || !validateCellphone(cellphone)){
    	return null;
    	}
    	
    	return cellphone.substring(0,3)+"****"+cellphone.substring(7);
    }
   
    /**
     * 校验输入的数值，保留两位小数
     * @param number
     * @return
     */
    public static boolean validateNumber(String number){
    	if(StringUtils.isEmpty(number)){
    		return false;
    	}
    	return number.matches("^[0-9]+(.[0-9]{0,2})?$");
    }
    
    /**
     * 校验输入的利率，保留一位小数
     * @param number
     * @return
     */
    public static boolean validateRate(String number){
    	if(StringUtils.isEmpty(number)){
    		return false;
    	}
    	return number.matches("^[0-9]{1,2}(.[0-9]{1})?$");
    	//return number.matches("^[0-9]+(.[0-9]{2})?$");
    }
}
