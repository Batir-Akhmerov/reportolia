/**
 * 
 */
package com.reportolia.core.sql;

import com.reportolia.core.sql.query.QC;

/**
 * The QueryGenerationCommand class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public class QueryGenerationCommand {
	
	private int aliasCounter = 1;
	
	public int nextAliasCounter() {
		return this.aliasCounter++;
	}
	
	public String nextAlias() {
		return QC.TBL_ALIAS + nextAliasCounter();
	}

}
