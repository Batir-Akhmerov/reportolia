<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:page>
	<jsp:attribute name="pageTitle"><html:msg key="dbTable.relationship.manage.title" />: ${fn:escapeXml(dbTable.name)}</jsp:attribute>	
	<jsp:attribute name="topMenuId">tableList</jsp:attribute>
	<jsp:attribute name="scripts"></jsp:attribute>
	
	<jsp:attribute name="scriptBody">

		var REL_PAGE_TITLE = '<html:msg key="dbTable.relationship.manage.title" />',
			TH_COLUMN = '<html:msg key="form.column.name" />',
			TH_LINKED_TABLE = '<html:msg key="dbTable.relationship.linkedTable.name" />',
			TH_LINKED_COLUMN = '<html:msg key="dbTable.relationship.linkedColumn.name" />',
			LBL_LINK_TO_FILTER = '<html:msg key="dbTable.relationship.isLinkToFilter.name" />',
			LBL_LINK_TO_FILTER_INFO = '<html:msg key="dbTable.relationship.isLinkToFilter.info" />',
			LBL_REL_TYPE = '<html:msg key="dbTable.relationship.type" />',
			LBL_ONE_TO_MANY = '<html:msg key="dbTable.relationship.oneToMany" />',
			LBL_MANY_TO_ONE = '<html:msg key="dbTable.relationship.manyToOne" />'
			;
		
		var TABLE_ID = ${dbTable.id};
		
		function onLoad() {
			/*
			$('#tabs').tabs({
			  active: 0
			});
			*/
			initTable();
		}
		
		function loadTableList() {
			//$('#tableList').jtable('load');
		}
		
		function initTable() {
						
			var tbConf = {
			        ajax: 'r3pTableRelationshipsLoad.go?tableId=' + TABLE_ID,		       
			        r3pAjaxDelete: 'r3pTableRelationshipDelete.go',
			        r3pAjaxSave: 'r3pTableRelationshipSave.go',
			        r3pNewRow: {tableId: TABLE_ID},
			        r3pGetFormTitle: function(rowData) {
			        	return REL_PAGE_TITLE;
			        },
			        scrollY: '400px',
			        paging: false,
			        ordering: false,
			        searching: false,
			        columns: [
			        	{data: 'id', r3p: 'KEY'},
			        	{data: 'tableId', visible: false, searchable: false},
			            {data: 'columnId', 
			            	r3pLabel: TH_COLUMN,
			            	r3pSelectUrl: 'r3pOptionsTableColumnsLoad.go?tableId=' + TABLE_ID,
			            	render: function(data, type, full, meta) {
			            		return full.column ? full.column.label : '';
			    			}
			            },
			            {data: 'oneToMany', 
			            	r3pLabel: LBL_REL_TYPE,
			            	r3p: {
			            		type: 'LBL_RADIO',
			            		options: [
			            		    {label: '<span class="clsFontSize2x">&larr;</span>', value: true, title: LBL_ONE_TO_MANY},
			            		    {label: '<span class="clsFontSize2x">&rarr;</span>', value: false, title: LBL_MANY_TO_ONE}
			            		]
			            	}
			            },
			            {data: 'linkedTableId', 
			            	r3pLabel: TH_LINKED_TABLE,
			            	r3p: {
			            		selectUrl: 'r3pOptionsTablesLoad.go',
			            		onChange: function(fldSelect, selConf) {
				    				r3p.jq('linkedColumnId').val(null).trigger("change");
				    			}
			            	},
			            	render: function(data, type, full, meta) {
			            		return full.linkedTable ? full.linkedTable.label : '';
			    			}
			    			
			            },
			            {data: 'linkedColumnId', 
			            	r3pLabel: TH_LINKED_COLUMN,
			            	r3p: {
			            		selectUrl: function (params) {
			            			var selCol = r3p.jq('linkedColumnId'),
			            				selTable = r3p.jq('linkedTableId'),
			            				tableId = parseInt(selTable.val());
			            			if (isNaN(tableId) ||  tableId < 0) {
			            				selCol.prop('disabled', true);
			            				return null;
			            			}
			            			selCol.prop('disabled', false);
			            			return 'r3pOptionsTableColumnsLoad.go?tableId=' + tableId;
			            		}
			            	},
			            	render: function(data, type, full, meta) {
			            		return full.linkedColumn ? full.linkedColumn.label : '';
			    			}
			    		},
			    		{data: 'label', r3pLabel: r3pMsg.LBL_LABEL},
			            {data: 'linkToFilter', 
			    			r3pLabel: LBL_LINK_TO_FILTER,
			    			r3p: 'LBL_CHECK',
			            	render: function(data, type, full, meta) {
			            		if (data) return '&#10004;';
		                    	return '';
			    			}
			            }
			        ],
			        r3pAfterInit: function (e, settings, data) {
			        	
			        }
			    };
				
				self.tableRelationshipList = r3pDtb.init('relationshipList', tbConf);
			
			
			
		}
		
	</jsp:attribute>
	
	<jsp:attribute name="body">
		<%--<div id="tabs">
		
			<jsp:include page="../tableTabs.jsp"/>
		
			<div id="tabTables">
		 --%>
		<div id="relationshipList"/></div>
				
		<%--	</div> 
			
		</div>--%>
		
	</jsp:attribute>
</html:page>
