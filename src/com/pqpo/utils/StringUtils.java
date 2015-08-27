package com.pqpo.utils;

import java.util.Collection;

/**
 * 字符串工具
 * @author qiulinmin
 *
 */
public class StringUtils {
	private StringUtils(){}
	
	public static final String EMPTY = "";
	
	public static String join(Collection<?> array,String separator){
		if(array==null){
			return null;
		}
		return join(array.toArray(),separator);
	}
	
	public static String join(Object[] array,String separator){
		if(array==null){
			return null;
		}
		if(array.length==0){
			return EMPTY;
		}
		if(separator==null){
			separator = EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for(Object obj:array){
			sb.append(obj==null?EMPTY:obj.toString()).append(separator);
		}
		sb.delete(sb.lastIndexOf(separator), sb.length());
		return sb.toString();
	}
	
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}
		return str.length()==0;
	}
	
	public static boolean isBlank(String str){
		if(str==null){
			return true;
		}
		str = str.trim();
		return str.length()==0;
	}
}
