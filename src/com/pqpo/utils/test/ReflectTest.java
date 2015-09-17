package com.pqpo.utils.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import com.pqpo.utils.ReflectUtils;

public class ReflectTest extends Sup{
	
	private String ss="dd";
	public int  f=100;
	private int vv=10;
	
	public static void main(String[] args) {
		ReflectTest t = new ReflectTest();
		List<Field> fields = ReflectUtils.getAllFields(ReflectTest.class);
		for(Field f:fields){
			System.out.println(f.getName());
		}
	}

	
}
class Sup {
	private String ddd;
	private int ddda;
}
