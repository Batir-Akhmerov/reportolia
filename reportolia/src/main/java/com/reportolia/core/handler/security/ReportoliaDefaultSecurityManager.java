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
	public Long getUserId() {
		return 13l;
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
