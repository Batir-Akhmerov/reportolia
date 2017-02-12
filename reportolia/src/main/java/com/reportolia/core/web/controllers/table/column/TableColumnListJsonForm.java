/**
 * 
 */
package com.reportolia.core.web.controllers.table.column;

import com.reportolia.core.web.controllers.base.datatable.JsonForm;

/**
 * The ReportListJsonForm class
 *
 * @author Batir Akhmerov
 * Created on Dec 23, 2016
 */
public class TableColumnListJsonForm extends JsonForm {
	
	private long tableId;

	public long getTableId() {
		return this.tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

}
