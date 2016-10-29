/**
 * 
 */
package com.reportolia.core.handler.db;

import java.util.ArrayList;
import java.util.List;

import com.reportolia.base.model.KeyLabel;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.utils.ListUtils;

/**
 * The SpringyDataBean class
 *
 * @author Batir Akhmerov
 * Created on Oct 24, 2016
 */
public class SpringyDataBean {
	
	class SpringyNode extends KeyLabel {
		private Boolean secured;
		private Boolean securityFilter;
		
		public SpringyNode(DbTable table) {
			super(table.getId(), table.getName());
			this.securityFilter = table.isSecurityFilter();
			this.secured = table.isSecured();
		}

		public boolean isSecurityFilter() {
			return this.securityFilter;
		}

		public void setSecurityFilter(Boolean securityFilter) {
			this.securityFilter = securityFilter;
		}

		public boolean getSecured() {
			return this.secured;
		}

		public void setSecured(boolean secured) {
			this.secured = secured;
		}
	}
	
	class SpringyEdge {
		private String parentName;
		private String childName;
		private String label;
		private Boolean linkToSecurity;
		
		public SpringyEdge(DbTableRelationship rel) {
			this.parentName = rel.getParentTable().getName();
			this.childName = rel.getChildTable().getName();
			this.linkToSecurity = rel.getLinkToSecurityFilter();
			this.label = rel.getLabel();
		}
		
		public String getParentName() {
			return this.parentName;
		}
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		public String getChildName() {
			return this.childName;
		}
		public void setChildName(String childName) {
			this.childName = childName;
		}
		public Boolean getLinkToSecurity() {
			return this.linkToSecurity;
		}

		public void setLinkToSecurity(Boolean linkToSecurity) {
			this.linkToSecurity = linkToSecurity;
		}

		public String getLabel() {
			return this.label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	private List<SpringyNode> nodes = new ArrayList<>();
	private List<SpringyEdge> edges = new ArrayList<>();;
	
	public void addNode(DbTable table) {
		if (ListUtils.containsByProperty(this.nodes, "label", table.getName())) {
			return;
		}
		this.nodes.add(new SpringyNode(table));
	}
	
	public void addEdge(DbTableRelationship rel) {		
		this.edges.add(new SpringyEdge(rel));
	}
	
	public List<SpringyNode> getNodes() {
		return this.nodes;
	}
	public void setNodes(List<SpringyNode> nodes) {
		this.nodes = nodes;
	}
	public List<SpringyEdge> getEdges() {
		return this.edges;
	}
	public void setEdges(List<SpringyEdge> edges) {
		this.edges = edges;
	}
}
