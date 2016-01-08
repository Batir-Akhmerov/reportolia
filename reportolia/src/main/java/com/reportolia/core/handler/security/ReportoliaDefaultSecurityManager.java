/**
 * 
 */
package com.reportolia.core.handler.security;

/**
 * The DefaultSecurityManager class
 *
 * @author Batir Akhmerov
 * Created on Dec 18, 2015
 */
public class ReportoliaDefaultSecurityManager implements ReportoliaSecurityHandler {

	@Override
	public int getUserId() {
		return 13;
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
