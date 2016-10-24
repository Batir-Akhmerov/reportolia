/**
 * 
 */
package com.reportolia.core.utils.functions;

/**
 * The Mapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 13, 2016
 */
public interface Mapper<F, T> {
	
	public T map(F bean);

}
