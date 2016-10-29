/**
 * 
 */
package com.reportolia.core.utils.functions;

/**
 * The InfoMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 27, 2016
 */
public interface InfoMapper<F, I, T> {
	
	public T map(I info, F bean);

}
