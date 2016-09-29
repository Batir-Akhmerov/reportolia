/**
 * 
 */
package com.reportolia.core.config;

import com.reportolia.core.handler.security.ReportoliaSecurityHandler;

/**
 * The DefaultSecurityManager class
 *
 * @author Batir Akhmerov
 * Created on Dec 18, 2015
 */
public class ReportoliaTestSecurityManager implements ReportoliaSecurityHandler {

	private Long userId = 13l;
	private boolean admin;
	
	@Override
	public Long getUserId() {
		return this.userId;
	}

	@Override
	public boolean isAdmin() {
		return this.admin;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
