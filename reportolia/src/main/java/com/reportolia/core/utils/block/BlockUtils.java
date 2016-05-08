/**
 * 
 */
package com.reportolia.core.utils.block;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.reportolia.core.sql.query.model.QueryOperand;

/**
 * The QueryOperandUtils class
 *
 * @author Batir Akhmerov
 * Created on Mar 11, 2016
 */
public class BlockUtils {
	
	public static void parseBlocks(List<QueryOperand> list) {
		parseBlocks(list, null, new BlockCommand());
	}
	
	public static void parseBlocks(List<QueryOperand> list, Block block, BlockCommand command) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		if (block == null) {
			block = new Block(null, null);
			command.getStack().addFirst(block);
		}
		boolean isFirst = true;
		Block loopBlock = block;
		for (QueryOperand operand: list) {
			if (operand.isBlockStart()) {
				loopBlock = new Block(loopBlock, operand);
				command.getStack().addFirst(loopBlock);
			}
			else if (operand.isBlockEnd()) {
				Assert.isTrue(!isFirst);
				Assert.isTrue(!CollectionUtils.isEmpty(command.getStack()));
				Assert.isTrue(loopBlock.getStartOperand() != null);
				loopBlock.setEndOperand(operand);
				command.getStack().removeFirst();
				loopBlock = command.getStack().peekFirst();
				
			}
			else {
				loopBlock.addOperand(operand);
				if (!loopBlock.isStringIncluded() && operand.isStringType()) {
					loopBlock.setStringIncluded(true);
					command.getStringBlocks().addFirst(loopBlock);
				}
			}
			
			isFirst = false;
		}
		
		if (!CollectionUtils.isEmpty(command.getStringBlocks())) {
			
			
			while(!command.getStringBlocks().isEmpty()) { 
				Block stringBlock = command.getStringBlocks().removeFirst();
				stringBlock.refreshBlock();
			}
		}
		
	}

}
