/**
 * 
 */
package com.reportolia.core.handler.db.mapper;

import com.reportolia.core.handler.db.relationship.RelationshipInfo;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.utils.functions.InfoMapper;

/**
 * The DbTableColumnToJTableOptionMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 27, 2016
 */
public class DbTableTalationshipToRelationshipInfoMapper implements InfoMapper<DbTableRelationship, Long, RelationshipInfo>{

	@Override
	public RelationshipInfo map(Long tableId, DbTableRelationship bean) {
		return new RelationshipInfo(tableId, bean);
	}

}
