/**
 * 
 */
package com.reportolia.core.web.controllers.report;

import com.reportolia.core.web.controllers.base.datatable.JsonForm;

/**
 * The ReportListJsonForm class
 *
 * @author Batir Akhmerov
 * Created on Jan 2, 2017
 */
public class ReportListJsonForm extends JsonForm {
	
	private long folderId;

	public long getFolderId() {
		return this.folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	

}
