package com.pqpo.utils.test;

import com.pqpo.utils.query.Compare;
import com.pqpo.utils.query.Query;

public class QueryForm {

	@Query(value="weight",compare=Compare.MoreThanOrEqual)
	private Integer minWeight;
	
	@Query(value="weight",compare=Compare.LessThanOrEqual)
	private Integer maxWerght;
	
	@Query(compare=Compare.Like)
	private String name;
	
	@Query(compare=Compare.Like)
	private String email;
	
	private Integer[] ages;
	
	
	public Integer getMinWeight() {
		return minWeight;
	}
	public void setMinWeight(Integer minWeight) {
		this.minWeight = minWeight;
	}
	public Integer getMaxWerght() {
		return maxWerght;
	}
	public void setMaxWerght(Integer maxWerght) {
		this.maxWerght = maxWerght;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer[] getAges() {
		return ages;
	}
	public void setAges(Integer[] ages) {
		this.ages = ages;
	}
	
}
