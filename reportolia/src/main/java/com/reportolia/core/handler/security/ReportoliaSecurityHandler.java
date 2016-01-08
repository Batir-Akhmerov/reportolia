/**
 * 
 */
package com.reportolia.core.handler.security;

/**
 * The ReportoliaSecurityHandler class
 *
 * @author Batir Akhmerov
 * Created on Dec 18, 2015
 */
public interface ReportoliaSecurityHandler {
	
	/**
	 * Implement this method to return current User's ID at runtime
	 * @return
	 */
	public int getUserId();
	
	/**
	 * Implement this method to tell the system whether current User has Admin Permissions
	 * @return
	 */
	public boolean isAdmin();

}
