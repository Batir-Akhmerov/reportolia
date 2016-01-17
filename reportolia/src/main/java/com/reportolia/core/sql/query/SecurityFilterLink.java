/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.ArrayList;
import java.util.List;

import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.utils.ListUtils;

/**
 * The SecurityFilterLink class is a base object used to build chains to the table security filters 
 *
 * @author Batir Akhmerov
 * Created on Jan 14, 2016
 */
public class SecurityFilterLink {
	
	DbTableRelationship relationship;
	List<SecurityFilterLink> linkList;
	SecurityFilterLink prevLink;
	SecurityFilterConfig conf;
	
	public SecurityFilterLink(DbTableRelationship relationship, SecurityFilterLink prevLink) {
		this.relationship = relationship;
		this.prevLink = prevLink;
	}
	
	public SecurityFilterLink(DbTableRelationship relationship, SecurityFilterConfig conf) {
		this.relationship = relationship;
		this.conf = conf;
	}
	
	public SecurityFilterLink addNewLink(DbTableRelationship rel) {
		if (this.linkList == null) {
			this.linkList = new ArrayList<>();
		}
		SecurityFilterLink link = new SecurityFilterLink(rel, this);
		this.linkList.add(link);
		return link;
	}
	
	public void removeChain(){
		int nextLinksCount = ListUtils.getSize(this.linkList);
		if (nextLinksCount < 1) {
			if (this.prevLink != null) {
				this.prevLink.removeLinkFromList(this);
			}
			else if (this.conf != null) {
				this.conf.removeLinkFromList(this);
			}
		}		
	}
	
	public void removeLinkFromList(SecurityFilterLink link){
		if (this.linkList != null) {
			this.linkList.remove(link);
		}
		removeChain();
	}
	
	
	
	
	public DbTableRelationship getRelationship() {
		return this.relationship;
	}
	public void setRelationship(DbTableRelationship relationship) {
		this.relationship = relationship;
	}
	public List<SecurityFilterLink> getLinkList() {
		return this.linkList;
	}
	public void setLinkList(List<SecurityFilterLink> linkList) {
		this.linkList = linkList;
	}

	public SecurityFilterLink getPrevLink() {
		return this.prevLink;
	}

	public void setPrevLink(SecurityFilterLink prevLink) {
		this.prevLink = prevLink;
	}

	@Override
	public int hashCode() {
		return (int) (this.getRelationship() != null ? this.getRelationship().getId() : -1L);
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	public SecurityFilterConfig getConf() {
		return this.conf;
	}

	public void setConf(SecurityFilterConfig conf) {
		this.conf = conf;
	}

	@Override
	public String toString() {
		return this.relationship.toString();
	}

}
