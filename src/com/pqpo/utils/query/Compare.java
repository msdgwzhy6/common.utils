package com.pqpo.utils.query;

/**
 * 查询条件枚举类，用于Query注解
 * @see Query
 * @author qiulinmin
 *
 */
public enum Compare {
	
	Equal("=?"),
	NotEqual("<>?"),
	MoreThan(">?"),
	LessThan("<?"),
	MoreThanOrEqual(">=?"),
	LessThanOrEqual("<=?"),
	Like(" like ?"),
	NotLike(" not like ?");
	
	private String operators = "";
	private Compare(String operators) {
		this.operators = operators;
	}
	public String getOperators() {
		return operators;
	}
}
