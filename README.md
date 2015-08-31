# utils
工具类

qurey:使用注解的方式拼装查询语句
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

	QueryForm.java:

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

security:加密工具类