/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

import com.reportolia.core.handler.operand.OperandHandler;
import com.reportolia.core.model.base.BaseColumnPath;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.QueryOperand;
import com.reportolia.core.sql.query.model.QueryTable;

/**
 * The QueryGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface QueryGeneratorHandler {
	
	public OperandHandler getContentOperandHandler();
	public void setContentOperandHandler(OperandHandler contentOperandHandler);
	public OperandHandler getFilterOperandHandler();
	public void setFilterOperandHandler(OperandHandler filterOperandHandler);
	public OperandHandler getSortingOperandHandler();
	public void setSortingOperandHandler(OperandHandler sortingOperandHandler);
	public QueryGeneratorManager getNestedQueryGeneratorHandler();
	public void setNestedQueryGeneratorHandler(QueryGeneratorManager nestedQueryGeneratorHandler);
	
	
	
	public Query getQuery(Long ownerId, QueryTable pkQueryTable, QueryGenerationCommand command);
	
	public <T extends BaseColumnPath> QueryTable appendTablesToQuery(Query query, List<T> columnPathList, QueryGenerationCommand command);
	
	public List<QueryOperand> createQueryOperands(Query query, List<Operand> operandList, QueryGenerationCommand command);
	

}
