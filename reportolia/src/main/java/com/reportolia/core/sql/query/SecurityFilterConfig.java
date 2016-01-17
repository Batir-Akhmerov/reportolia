/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The SecurityFilterConfig class
 *
 * @author Batir Akhmerov
 * Created on Dec 23, 2015
 */
public class SecurityFilterConfig {
	
	private List<SecurityFilterLink> linkList = new ArrayList<>();
	private Set<Long> uniqueSetOfRelationships = new HashSet<>();
	private boolean filterFoundButRemoved;
	
	
	
	public boolean addToUniqueSet(long relationshipId) {
		if (this.uniqueSetOfRelationships.contains(relationshipId)) {
			return false;
		}
		this.uniqueSetOfRelationships.add(relationshipId);
		return true;
	}

	public void removeLinkFromList(SecurityFilterLink link){
		if (this.linkList != null) {
			this.linkList.remove(link);
			this.filterFoundButRemoved = true;
		}
	}


	public List<SecurityFilterLink> getLinkList() {
		return this.linkList;
	}



	public void setLinkList(List<SecurityFilterLink> linkList) {
		this.linkList = linkList;
	}

	public boolean isFilterFoundButRemoved() {
		return this.filterFoundButRemoved;
	}
	
	
}
