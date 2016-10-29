/**
 * 
 */
package com.reportolia.core.web.controllers.jtable;

import org.springframework.util.StringUtils;

/**
 * The JsonSearchForm class
 *
 * @author Batir Akhmerov
 * Created on Oct 19, 2016
 */
public class JsonSearchForm {
	
	public static final String SORT_SPACE = " ";
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";
	
	public static final int PAGE_SIZE = 10;
	
	private int jtStartIndex = 0;
	private int jtPageSize = PAGE_SIZE;
	private String jtSorting;
	
	
	public int getJtStartIndex() {
		return this.jtStartIndex;
	}
	public void setJtStartIndex(int jtStartIndex) {
		this.jtStartIndex = jtStartIndex;
	}
	public int getJtPageSize() {
		return this.jtPageSize;
	}
	public void setJtPageSize(int jtPageSize) {
		this.jtPageSize = jtPageSize;
	}
	public String getJtSorting() {
		return this.jtSorting;
	}
	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}

	public String getSortingField() {
		if (StringUtils.isEmpty(this.jtSorting)) {
			return null;
		}
		int index = this.jtSorting.lastIndexOf(SORT_SPACE);
		if (index == -1) {
			return null;
		}
		return this.jtSorting.substring(0, index-1);
	}
	
	public boolean isSortingAsc() {
		if (StringUtils.isEmpty(this.jtSorting)) {
			return false;
		}
		return -1 != this.jtSorting.lastIndexOf(SORT_SPACE + SORT_ASC);
	}
	public boolean isSortingDesc() {
		if (StringUtils.isEmpty(this.jtSorting)) {
			return false;
		}
		return -1 != this.jtSorting.lastIndexOf(SORT_SPACE + SORT_DESC);
	}
}
