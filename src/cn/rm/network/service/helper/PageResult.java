package cn.rm.network.service.helper;


import java.util.List;

public class PageResult {

	private Integer total = 0;
	private Integer pageSize = 0;
	private Integer page = 1;
	private Integer totalPage = 1;
	
	private List result;
	//分页页脚
	private String footer;
	
	public PageResult(Integer page, Integer pageSize, Integer total, Integer totalPage, List result) {
		super();
		this.total = total;
		this.pageSize = pageSize;
		this.page = page;
		this.totalPage = totalPage;
		this.result = result;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}