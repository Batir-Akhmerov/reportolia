/**
 * 
 */
package com.reportolia.core.utils.block;

import java.util.ArrayList;
import java.util.List;

import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.sqlitem.SqlItem;
import com.reportolia.core.sql.query.model.QueryOperand;

import junit.framework.TestCase;

/**
 * The BlockUtilsTests class
 *
 * @author Batir Akhmerov
 * Created on Mar 11, 2016
 */
public class BlockUtilsTests extends TestCase {
	
	protected List<QueryOperand> list;
	
	protected void setUp(){
		
		
	}
	
	/**
	 * TXT + NUM
	 */
	public void test_simpleBlock() {
		this.list = new ArrayList<>();
		this.list.add(createOperand("tb.name", DataType.TXT));
		this.list.add(createOperand(null, false, false));
		this.list.add(createOperand("tb.amount", DataType.NUM));
		
		BlockUtils.parseBlocks(this.list);
		
		assertTrue(this.list.get(0).isStringType());
		assertFalse(this.list.get(1).isConvertToString());
		assertTrue(this.list.get(2).isConvertToString());
	}
	
	/**
	 * NUM + (NUM + TXT)
	 */
	public void test_operandPlusBlock() {
		this.list = new ArrayList<>();
		this.list.add(createOperand("tb.amount", DataType.NUM));
		this.list.add(createOperand(null, false, false)); // +
		this.list.add(createOperand(null, true, false)); // (
		this.list.add(createOperand("tb.rate", DataType.NUM));
		this.list.add(createOperand(null, false, false)); // +
		this.list.add(createOperand("tb.name", DataType.TXT));
		this.list.add(createOperand(null, false, true)); // )
		
		
		BlockUtils.parseBlocks(this.list);
		
		assertTrue(this.list.get(0).isConvertToString());
		assertFalse(this.list.get(1).isConvertToString());
		assertTrue(this.list.get(2).isStringType());
		assertTrue(this.list.get(3).isConvertToString());
		assertFalse(this.list.get(4).isConvertToString());
		assertTrue(this.list.get(5).isStringType());
		assertTrue(this.list.get(6).isConvertToString());
	}
	
	/**
	 * (NUM + DATE) + (NUM + TXT)
	 */
	public void test_blockPlusBlock() {
		this.list = new ArrayList<>();
		this.list.add(createOperand(null, true, false)); // (
		this.list.add(createOperand("tb.quote", DataType.NUM));
		this.list.add(createOperand(null, false, false)); // +
		this.list.add(createOperand("tb.date", DataType.DATE));
		this.list.add(createOperand(null, false, true)); // )
		this.list.add(createOperand(null, false, false)); // +
		this.list.add(createOperand(null, true, false)); // (
		this.list.add(createOperand("tb.rate", DataType.NUM));
		this.list.add(createOperand(null, false, false)); // +
		this.list.add(createOperand("tb.name", DataType.TXT));
		this.list.add(createOperand(null, false, true)); // )
		
		
		BlockUtils.parseBlocks(this.list);
		
		assertTrue(this.list.get(0).isConvertToString());
		assertFalse(this.list.get(1).isConvertToString());
		assertFalse(this.list.get(2).isConvertToString());
		assertFalse(this.list.get(3).isConvertToString());
		assertTrue(this.list.get(4).isConvertToString());
		assertFalse(this.list.get(5).isConvertToString());
		assertTrue(this.list.get(6).isStringType());
		assertTrue(this.list.get(7).isConvertToString());
		assertFalse(this.list.get(8).isConvertToString());
		assertTrue(this.list.get(9).isStringType());
		assertTrue(this.list.get(10).isConvertToString());
	}
	
	protected QueryOperand createOperand(String sql, DataType type) {
		QueryOperand o = new QueryOperand();
		o.setSql(sql);
		o.setDataType(type);
		return o;
	}

	
	protected QueryOperand createOperand(DataType type, boolean isStartBlock, boolean isEndBlock) {
		QueryOperand o = new QueryOperand();
		o.setDataType(type);
		SqlItem item = new SqlItem();
		o.setSqlItem(item);
		if (!isEndBlock)   item.setBlock(isStartBlock);
		if (!isStartBlock) item.setBlockEnd(isEndBlock);
		return o;
	}

}
