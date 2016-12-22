/**
 * 
 */
package com.reportolia.core.web.controllers.base.datatable;

import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

/**
 * The JsonSelectOptionsForm class
 *
 * @author Batir Akhmerov
 * Created on Dec 4, 2016
 */
public class JsonSelectOptionsForm extends PageRequest{
	
	public static final int FIRST_PAGE = 0;
	public static final int PAGE_SIZE = 30;
	private String q;
	

	public JsonSelectOptionsForm(){
		this(FIRST_PAGE, PAGE_SIZE);
	}
	public JsonSelectOptionsForm(int page, int size) {
		super(page, size);
	}

	public String getSearchTerm() {
		return getQ();
	}

	public String getQ() {
		return StringUtils.isEmpty(this.q) ? "" : this.q;
	}


	public void setQ(String q) {
		this.q = q;
	}

}
