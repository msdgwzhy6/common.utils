package com.pqpo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {
	private ReflectUtils() {}

	/**
	 * 获取参数（包括父类）
	 * @param clazz
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> clazz){
		return getFields(clazz);
	}
	/**
	 * 获取方法
	 * @param clazz
	 * @return
	 */
	public static List<Method> getAllMethod(Class<?> clazz){
		return getMethods(clazz);
	}

	/**
	 * 获取类属性值
	 * @param obj
	 * @param fieldName
	 * @param forceAccess
	 * @return
	 * @throws IllegalAccessException 
	 */
	public static Object getFieldValue(Object obj,String fieldName,boolean forceAccess) throws IllegalAccessException{
		if(obj==null){
			throw new IllegalArgumentException("Obj must not be null ");
		}
		if(fieldName==null){
			throw new IllegalArgumentException("FieldName must not be null ");
		}
		Class<? extends Object> clazz = obj.getClass();
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			//ignore
		}
		if(field!=null){
			return getFieldValue(obj,field,forceAccess);
		}
		return null;
	}
	
	/**
	 * 获取类属性值
	 * @param obj
	 * @param field
	 * @param forceAccess
	 * @return
	 * @throws IllegalAccessException 
	 */
	public static Object getFieldValue(Object obj,Field field,boolean forceAccess) throws IllegalAccessException{
		if(obj==null){
			throw new IllegalArgumentException("Obj cannot be null");
		}
		if(field==null){
			throw new IllegalArgumentException("Field cannot be null");
		}
		if (forceAccess && !field.isAccessible()) {
			field.setAccessible(true);
		}
		return field.get(obj);
	}

	private static List<Field> getFields(Class<?> clazz){
		ArrayList<Field> list = new ArrayList<Field>();
		if(clazz==Object.class){
			return list;
		}
		Field[] fields = clazz.getDeclaredFields();
		if(fields!=null){
			for(Field f:fields){
				list.add(f);
			}
		}
		list.addAll(getFields(clazz.getSuperclass()));//递归
		return list;		
	}

	private static List<Method> getMethods(Class<?> clazz){
		ArrayList<Method> list = new ArrayList<Method>();
		if(clazz==Object.class){
			return list;
		}
		Method[] methods = clazz.getDeclaredMethods();
		if(methods!=null){
			for(Method m:methods){
				list.add(m);
			}
		}
		list.addAll(getMethods(clazz.getSuperclass()));//递归
		return list;
	}
}
