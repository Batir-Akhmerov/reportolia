/**
 * 
 */
package com.reportolia.core.handler.db.mapper;

import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.utils.functions.Mapper;
import com.reportolia.core.web.controllers.jtable.JTableOption;

/**
 * The DbTableColumnToJTableOptionMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 25, 2016
 */
public class DbTableColumnToJTableOptionMapper implements Mapper<DbTableColumn, JTableOption>{

	@Override
	public JTableOption map(DbTableColumn bean) {
		return new JTableOption(bean.getName(), bean.getId());
	}

}
