/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.util.List;

import com.reportolia.core.driver.metadata.SysColumn;
import com.reportolia.core.driver.metadata.SysTable;
import com.reportolia.core.web.controllers.JsonListForm;

/**
 * The metadataHandler class
 *
 * @author Batir Akhmerov
 * Created on Oct 10, 2016
 */
public interface MetadataHandler {

	
	public List<SysTable> getSysTableList();
	
	public void saveSysTableList(JsonListForm<SysTable> json);
	
	public List<SysColumn> getSysColumnList(String tableName);
	
	//public void saveSysColumnList(JsonListForm<SysColumn> json);
}
