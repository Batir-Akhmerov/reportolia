<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:page icon="mif-tree">

	<jsp:attribute name="scripts">
		js/reportolia/jquery/plugins/springy/springy.js,
		js/reportolia/jquery/plugins/springy/springyui.js,
		js/reportolia/table/dlgRelationshipList.js
	</jsp:attribute>

	<jsp:attribute name="pageTitle"><html:msg key="dbTable.relationship.map.tab.name" /></jsp:attribute>
	

	
	<jsp:attribute name="scriptBody">
		
		var MSG_RETRIEVE = '<html:msg key="msg.confirm.metadata.retrieve" />',
			BTN_RETRIEVE = '<html:msg key="dbTables.button.retrieveFromDb" />',
			BTN_ADD_MANUALLY = '<html:msg key="dbTables.button.addManually" />'
			;
			
		var REL_PAGE_TITLE = '<html:msg key="dbTable.relationship.manage.title" />',
			REL_PAGE_INFO = '<html:msg key="dbTable.relationship.manage.instructions" />',
			TITLE_CURRENT_TABLE = '',
			TH_COLUMN = '<html:msg key="form.column.name" />',
			TH_LINKED_TABLE = '<html:msg key="dbTable.relationship.linkedTable.name" />',
			TH_LINKED_COLUMN = '<html:msg key="dbTable.relationship.linkedColumn.name" />',
			LBL_LINK_TO_FILTER = '<html:msg key="dbTable.relationship.isLinkToFilter.name" />',
			LBL_LINK_TO_FILTER_INFO = '<html:msg key="dbTable.relationship.isLinkToFilter.info" />',
			LBL_ONE_TO_MANY = '<html:msg key="dbTable.relationship.oneToMany" />',
			LBL_MANY_TO_ONE = '<html:msg key="dbTable.relationship.manyToOne" />'
		
			
		;
			
		var COLOR_NODE = '#848482',
			COLOR_TB_SEC_FILTER = '#348017',
			COLOR_TB_SECURED = '#9F000F', 
			COLOR_FILTER_LINK = '#4AA02C',
			FONT_TB = '14px Impact',
			FONT_LINK = null, //'10px Impact',
			FONT_TB_FILTER = '14px Impact'; 
		
		function showMe() {
			openDlg_TableRelationshipList(this.id, this.label);
		}
		
		function onLoad() {
			
			r3p.onWindowResize(resizeMap);
			resizeMap();
		    loadSpringyData();
		}
		
		function loadSpringyData() {			
			r3p.ajax({
					url: 'r3pTablesSpringyDataLoad.go'
				},
				function(resp) {
					var graph = new Springy.Graph();
					var nodeMap = [];
					if (resp && resp.nodes) {
						$.each(resp.nodes, function(i,n){
							var node = {
								id: n.id, 
								label: n.label, 
								font: FONT_TB,
								color: COLOR_NODE, 
								ondoubleclick: showMe
							};
							if (n.securityFilter) {
								node.color = COLOR_TB_SEC_FILTER;
								node.font = FONT_TB_FILTER;
							}
							else if (n.secured) {
								node.color = COLOR_TB_SECURED;
							}
							nodeMap[n.label] = graph.newNode(node);
						});
					}
					if (resp && resp.edges) {
						$.each(resp.edges, function(i, ed){
							var data = {};
							if (ed.linkToSecurity) data.color = COLOR_FILTER_LINK;
							if (ed.label) {
								data.label = ed.label;
								data.font = FONT_LINK;
							}
							graph.newEdge(nodeMap[ed.childName], nodeMap[ed.parentName], data);
						});
					}
										
					var springy = $('#datamodelMap').springy({
						graph: graph 
						/*,
						nodeSelected: function(node){
					      console.log('Node selected: ' + JSON.stringify(node.data));
					    }
					    */
					});
				}
			);
		}
		
		function resizeMap() {
			var canvas = $('#datamodelMap,#divMap');
			r3p.sticky(canvas, 50, 30);
			canvas.attr('width', canvas.width());
			canvas.attr('height', canvas.height());
		}
		
		function refreshMe(){
			openRelationshipMap();
		}
		
	</jsp:attribute>
	
	<jsp:attribute name="sidebar">
		<html:dbSidebar activeId="relationshipMap" />
	</jsp:attribute>
	
	<jsp:attribute name="body">
		<hr class="thin bg-grayLighter">
					
		<div id="tabRelationshipMap">
			<div id="divMap">
				<canvas id="datamodelMap" />
			</div>
		</div>
		
	</jsp:attribute>
</html:page>
