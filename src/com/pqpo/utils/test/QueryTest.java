package com.pqpo.utils.test;

import java.util.List;
import com.pqpo.utils.query.QueryFeature;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

	public void test(String[] args) {
		QueryForm queryForm = new QueryForm();
		queryForm.setMinWeight(100);
		queryForm.setMaxWerght(200);
		queryForm.setName("qlm");
		queryForm.setAges(new Integer[]{1,2,3,4,5});		
		String hql = "from Person s,Table b ";
		QueryFeature queryFeature = new QueryFeature.Builder()
				.setPrefix(hql, true)
				.setArgumentPrefix("s")
				.setQueryObject(queryForm)
				.setSuffix("order by s.id")
				.build();
		
	}
}
