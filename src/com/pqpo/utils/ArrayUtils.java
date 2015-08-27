package com.pqpo.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	private ArrayUtils() {}

	public  static <T> List<T> arrayToList(T[] array){
		List<T> list = new ArrayList<T>();
		if(array==null||array.length==0){
			return list;
		}
		for(T t:array){
			list.add(t);
		}
		return list;
	}
}
