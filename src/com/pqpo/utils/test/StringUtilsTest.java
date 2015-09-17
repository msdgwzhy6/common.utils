package com.pqpo.utils.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pqpo.utils.StringUtils;

public class StringUtilsTest {
	List<String> list;

	@Before
	public void setUp() throws Exception {
		list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
	}

	@Test
	public void testJoinCollectionOfQString() {
		String join = StringUtils.join(list, "and");
		assertTrue("aandbandc".equals(join));
	}

	@Test
	public void testJoinObjectArrayString() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsBlank() {
		fail("Not yet implemented");
	}

}
