/**
 * 
 */
package com.reportolia.core.utils.block;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The BlockCommand class
 *
 * @author Batir Akhmerov
 * Created on Mar 11, 2016
 */
public class BlockCommand {
	
	private Deque<Block> stack = new ArrayDeque<>();
	private Deque<Block> stringBlocks = new ArrayDeque<>();

	public Deque<Block> getStack() {
		return this.stack;
	}

	public void setStack(Deque<Block> stack) {
		this.stack = stack;
	}

	public Deque<Block> getStringBlocks() {
		return this.stringBlocks;
	}

	public void setStringBlocks(Deque<Block> stringBlocks) {
		this.stringBlocks = stringBlocks;
	}
	/*
	public Block peekFirstBlock() {
		return this.stack.peekFirst();
	}
*/
}
