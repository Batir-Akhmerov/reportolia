package com.reportolia.core.repository.sqlitem;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.reportolia.core.model.sqlitem.SqlItem;
import com.reportolia.core.model.sqlitem.SqlItemType;
import com.reportolia.core.repository.base.ReadonlyRepository;

/**
 * 
 * The SqlItemRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface SqlItemRepository extends ReadonlyRepository<SqlItem, Long> {

	@Query(
			"select s FROM SqlItem s WHERE s.aggregateFunction = true"
    )
    public List<SqlItem> findAggregateFunctions();
	
	public List<SqlItem> findBySqlItemTypeAndSql(SqlItemType type, String sql);
}
