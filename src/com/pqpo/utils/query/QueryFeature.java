package com.pqpo.utils.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.pqpo.utils.ReflectUtils;
import com.pqpo.utils.StringUtils;

/**
 * 查询
 * @author qiulinmin
 *
 */
public class QueryFeature {
	private Builder builder;

	protected QueryFeature(Builder builder){
		this.builder = builder;
	}

	/**
	 * 查询语句集合
	 */
	private List<String> qls;
	/**
	 * 查询参数
	 */
	private List<Object> params;

	protected List<String> getQls() {
		return qls;
	}
	protected void setQls(List<String> qls) {
		this.qls = qls;
	}
	/**
	 * 获取参数
	 * @return
	 */
	public List<Object> getParams() {
		return params;
	}
	protected void setParams(List<Object> params) {
		this.params = params;
	}
	protected String[] getQlsArray(){
		if(qls!=null){
			return qls.toArray(new String[0]);
		}
		return new String[0];
	}
	/**
	 * 获取参数数组
	 */
	public Object[] getParamsArray(){
		if(params!=null){
			return params.toArray();
		}
		return new Object[0];
	}

	/**
	 * 获取查询语句
	 * @return
	 */
	public String getQueryString(){
		String prefix = builder.getPrefix();
		if(prefix==null){
			prefix="";
		}
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(" ");
		if(!isEmpty()){
			sb.append("and ")
			.append(StringUtils.join(getQlsArray(), " and "));
		}
		sb.append(" ");
		sb.append(StringUtils.isBlank(builder.getSuffix())?"":builder.getSuffix());
		return sb.toString();
	}

	public boolean isEmpty(){
		return qls==null||qls.isEmpty();
	}

	/**
	 * 查询构造器
	 * @author qiulinmin
	 *
	 */
	public static class Builder{

		private Object query;
		private String prefix;
		private String suffix;
		private String argumentPrefix;

		/**
		 * 设置查询对象
		 * @param query
		 * @return
		 */
		public Builder setQueryObject(Object query){
			this.query = query;
			return this;
		}

		/**
		 * 设置查询语句前缀（for example : from Table s）
		 * @param prefix
		 * @param where 是否需要添加where
		 * @return
		 */
		public Builder setPrefix(String prefix,boolean where){
			if(where){
				prefix+=" where 1=1";
			}
			this.prefix = prefix;
			return this;
		}

		/**
		 * 设置查询语句后缀（for example :order by s.id）
		 * @param suffix
		 * @return
		 */
		public Builder setSuffix(String suffix){
			this.suffix = suffix;
			return this;
		}

		/**
		 * 设置参数前缀
		 * @param prefix
		 * @return
		 */
		public Builder setArgumentPrefix(String argumentPrefix){
			this.argumentPrefix = argumentPrefix;
			return this;
		}

		public String getPrefix() {
			return prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public QueryFeature build() {
			if(query==null){
				throw new IllegalArgumentException("query object cannot be null");
			}
			QueryFeature queryFeature = new QueryFeature(this);
			List<String> qlList = new ArrayList<String>();
			List<Object> paramList = new ArrayList<Object>();
			Class<?> clazz = query.getClass();
			List<Field> fields = ReflectUtils.getAllFields(clazz);
			for(Field field:fields){
				Query queryAnn = field.getAnnotation(Query.class);	
				Object fieldValue = getFieldValue(field);
				if(queryAnn==null||fieldValue==null){
					continue;
				}
				if(fieldValue instanceof String && "".equals(fieldValue)){
					continue;
				}
				Compare compare = queryAnn.compare();
				String fieldName = getFieldName(field,queryAnn.value());
				String queryLaunage = createQueryLaunage(fieldName,queryAnn.compare());				
				if(compare==Compare.Like||compare==Compare.NotLike){
					fieldValue = "%"+fieldValue+"%";
				}
				qlList.add(queryLaunage);
				paramList.add(fieldValue);
			}
			queryFeature.setQls(qlList);
			queryFeature.setParams(paramList);
			return queryFeature;
		}

		/**
		 * 获取字段名
		 * @param field
		 * @param queryAnn
		 * @return
		 */
		private String getFieldName(Field field,String queryValue) {
			String fieldName = queryValue;
			if("".equals(queryValue)){
				fieldName = field.getName();
			}
			return fieldName;
		}
		
		/**
		 * 获取字段值
		 * @param field
		 * @return
		 */
		private Object getFieldValue(Field field) {
			Object obj = null;
			try {
				obj = ReflectUtils.getFieldValue(query, field, true);
			} catch (IllegalAccessException e) {
				//ignore
			}
			return obj;
		}
		
		/**
		 * 创建查询语句
		 * @param fieldName
		 * @param queryAnn
		 * @return
		 */
		private String createQueryLaunage(String fieldName,Compare queryCompare) {
			StringBuilder sb = new StringBuilder();
			if(!StringUtils.isBlank(argumentPrefix)){
				sb.append(argumentPrefix).append(".");
			}
			sb.append(fieldName);
			String operator = queryCompare.getOperators();
			sb.append(operator);
			return sb.toString();
		}
	}

}
