/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reportolia.core.model.table.DbTableRelationship;

/**
 * The SecurityFilterConfig class
 *
 * @author Batir Akhmerov
 * Created on Dec 23, 2015
 */
public class SecurityFilterConfig {
	
	private List<List<DbTableRelationship>> listOfLinksToFilters = new ArrayList<>();
	private Set<Long> uniqueSetOfRelationships = new HashSet<>();
	
	
	
	public boolean addToUniqueSet(long relationshipId) {
		if (this.uniqueSetOfRelationships.contains(relationshipId)) {
			return false;
		}
		this.uniqueSetOfRelationships.add(relationshipId);
		return true;
	}
	
	public void addAnotherListOfLinks(List<DbTableRelationship> list) {
		this.listOfLinksToFilters.add(list);
	}
	
	public List<List<DbTableRelationship>> getListOfLinksToFilters() {
		return this.listOfLinksToFilters;
	}
	
	
}
