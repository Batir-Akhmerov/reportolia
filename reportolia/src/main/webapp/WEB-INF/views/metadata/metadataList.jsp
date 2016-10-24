<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html:page>
	<jsp:attribute name="pageTitle"><spring:message code="sysTable.title" /></jsp:attribute>
	<jsp:attribute name="scriptBody">
		
		function onLoad() {
			$('#tabs').tabs({
			  active: 1
			});
		
			var form = $('table > tbody').r3pListForm({
				url: URL_SAVE_DB_META,
				itemSelector: 'tr', 
 				idField: 'name',
 				fnAddToList: selectedRowsToJson
			});
			
			$('#btnSave').click(form.save);
			
			//$('#tableList').dataTable();
			
			$('#metadataList').jtable({
		            title: 'Tables Metadata found in Database',
		            //paging: true, //Enable paging
		            //pageSize: 10, //Set page size (default: 10)
		            sorting: true, //Enable sorting
		            defaultSorting: 'name ASC', //Set default sorting
		            selecting: true, //Enable selecting
		            multiselect: true, //Allow multiple selecting
		            selectingCheckboxes: true, //Show checkboxes on first column
		            actions: {
		                listAction: 'r3pDbMetadataLoad.go' //,
		                //deleteAction: '/Demo/DeleteStudent',
		                //updateAction: '/Demo/UpdateStudent',
		                //createAction: '/Demo/CreateStudent'
		            },
		            fields: {
		                name: {
		                	key: true,
		                    title: 'Name',
		                    width: '23%'
		                },
		                type: {
		                    title: 'Type'
		                },
		                schema: {
		                    title: 'Schema'
		                }
		            }
		        });
		 
		        $('#metadataList').jtable('load');
		}
		
		function selectedRowsToJson() {
			var $selectedRows = $('#metadataList').jtable('selectedRows'),
				jsonList = [];
			
			if ($selectedRows.length == 0) return jsonList;
			
		    $selectedRows.each(function () {
		        var record = $(this).data('record');
		        jsonList.push({selected: true, name: record.name});
		        
		    });
			return jsonList; 
		}
		
	</jsp:attribute>
	<jsp:attribute name="body">
		
		<div id="tabs">
		
			<jsp:include page="../tableTabs.jsp"/>
			
			<div id="tabMetadata">
				<div id="metadataList"></div>
			</div>
			
		</div>
	
		<%--
		<table id="tableList" class="display">
			<thead>
				<tr>
					<th><spring:message code="sysTable.table.name" /></th>
					<th><spring:message code="form.type.name" /></th>
					<th><spring:message code="form.schema.name" /></th>
				</tr>
			</thead>
			<c:if test="${sysTableList != null}">
				<tbody>
				
				<c:forEach items="${sysTableList}" var="tb" varStatus="loop">
					<tr>
						<td>
							<label>
								<input type="checkbox" name="selected" class="noPlugin" checked="true" />
								<c:out value="${tb.name}" />
								<input type="hidden" name="name" value="${tb.name}" />
								<input type="hidden" name="type" value="${tb.type}" />
								<input type="hidden" name="schema" value="${tb.schema}" />
							</label>
						</td>
						<td><c:out value="${tb.type}" /></td>
						<td><c:out value="${tb.schema}" /></td>
					</tr> 
				</c:forEach>
				
				</tbody>
			</c:if>
		</table>
		 --%>
		<div class="clsTbButtons">
			<button id="btnSave"><spring:message code="sysTable.button.addSelected" /></button>
		</div>
	</jsp:attribute>
</html:page>
