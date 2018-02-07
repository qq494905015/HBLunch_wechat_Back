package com.bbsoft.common.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页类
 * ClassName: Page 
 * @Description: 分页类
 */
public class PageUtil<T> {
	
	//当前页
	private Integer pageNum;
	//页大小
	private Integer pageSize;
	//记录数
	private Integer total;
	//页数量
	@SuppressWarnings("unused")
	private Integer totalPage;
	//开始行数
	@SuppressWarnings("unused")
	private Integer startItem;
	//分页的数据集
	private List<T> items = new ArrayList<T>();
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getTotalPage() {
		return totalPage = this.total / pageSize.intValue() + (this.total % pageSize.intValue() != 0 ? 1 : 0);
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getStartItem() {
		return (pageNum.intValue() - 1) * pageSize.intValue();
	}
	public void setStartItem(Integer startItem) {
		this.startItem = startItem;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public PageUtil() {
		super();
	}
	public PageUtil(Integer pageNum, Integer pageSize, Integer total) {
		super();
		this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
		this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
		this.total = total;
	}
	
	
}
