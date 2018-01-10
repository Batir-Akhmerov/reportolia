<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	
	function initDlgMetadata() {
		if (self.dlgMetadataInitialized) return;
		
		self.formMetadata = $('table > tbody').r3pListForm({
			url: URL_SAVE_DB_META,
			itemSelector: 'tr', 
				idField: 'name',
				fnAddToList: selectedRowsToJson
		});
		
		//$('#btnSave').click(form.save);
		r3p.jTable('metadataList', {
            //title: 'Tables Metadata found in Database',
            //paging: true, //Enable paging
            //pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'name ASC', //Set default sorting
            selecting: true, //Enable selecting
            multiselect: true, //Allow multiple selecting
            selectingCheckboxes: true, //Show checkboxes on first column
            selectOnRowClick: false,
            height: 400,
            actions: {
                listAction: 'r3pDbMetadataLoad.go' 
            },
            selectionChanged: function(evt, data) {
            	var pTable = $('#metadataList'),
            		$rows =$('.jtable-wrapper > .jtable > tbody > tr:not(.jtable-child-row)', pTable) ;
            	
                $.each($rows, function () {
                	var tr = $(this),
	                	isChildRowOpen = pTable.jtable("isChildRowOpen", tr);
	            		childRow = pTable.jtable("getChildRow", tr),
	            		isSelected = $('[type=checkbox]', tr).is(':checked'),
	            		wasSelected = tr.prop('isSelected');
	            	
	            	// select or deselect all rows of a childTable
                	if (r3p.isNull(wasSelected) || wasSelected != isSelected) { // found clicked row
                		var nestedRows = $('tbody > tr', childRow);
                		selectRows(nestedRows, isSelected);
                		selectRows($('thead > tr', childRow), isSelected, true);
                	}
                	tr.prop('isSelected', isSelected);
                });
            },
            fields: {
                name: {
                	key: true,
                    title: 'Name',
                    width: '23%',
                	display: function (tableData) {
                		var pTable = $('#metadataList'),
                        	$div = $('<a href="javascript:void(0)" title="Open Columns"></a>').text(tableData.record.name);
                        
                        $div.click(function () {
                        	var tr = $(this).closest("tr"),
                            	isChildRowOpen = pTable.jtable("isChildRowOpen", tr),
                            	childRow = pTable.jtable("getChildRow", tr);
                        	$(this).addClass('clsBold');
                        	
                        	if (isChildRowOpen || (childRow && !r3p.isNullJq(childRow.find('table')))) {
                        		childRow.toggleClass('clsHiddenForce');
                        		return;
                        	}
                        	
                        	pTable.jtable('openChildTable',
                    			tr,
                                {   
                                    sorting: false, 
                                    defaultSorting: 'name ASC', 
                                    selecting: true, 
                                    multiselect: true, 
                                    selectingCheckboxes: true, 
                                    showCloseButton: false,
                                    actions: {
                                        listAction: 'r3pDbMetadataColumnsLoad.go?tableName=' + tableData.record.name
                                    },
                                    fields: {
                                        tableName: {
                                            type: 'hidden'                                            
                                        },                                        
                                        isColumn: {
                                            type: 'hidden'
                                        },
                                        name: {
                                            title: 'Columns',
                                            width: '30%'
                                        },
                                        type: {
                                            title: 'Data Type',
                                            width: '30%'
                                        },
                                        size: {
                                            title: 'Length',
                                            width: '20%'
                                        }
                                    }
                                }, 
                              	//opened handler
                                function (data) { 
                                    data.childTable.jtable('load', {}, 
                                    	// after table load
                                    	function(){
	                                    	var nestedCheckboxes = $('[type=checkbox]', data.childTable);
	                                    	var nestedRows = $('tr', data.childTable);
	                                    	selectRows(nestedRows, true);
	                                    	selectRows(nestedRows, true, true);
	                                    	
	                                        nestedCheckboxes.click(function(){
	                                        	setParentRowCheckbox(tr, nestedCheckboxes);
	                                        });
	                                        setParentRowCheckbox(tr, nestedCheckboxes);
	                                    }
                                   	);
                                    
                                }
                            );
                        });
                        //Return image to show on the person row
                        return $div;
                    }
                },
                type: {
                    title: 'Type'
                },
                schema: {
                    title: 'Schema'
                }
            },
            recordsLoaded: function(){
            	if (self.onLoad_Metadata) self.onLoad_Metadata();
            }
        });
		self.dlgMetadataInitialized = true;
	}
	
	function setParentRowCheckbox(tr, nestedCheckboxes){
    	var isAtLeastOneChecked = nestedCheckboxes.filter(function() {
    		return this.checked;
    	}).length > 0;
    	
    	selectRows(tr, isAtLeastOneChecked);
    }
	
	function selectRows(rows, isSelected, isHeader) {
		if (isHeader) {
			rows.children('th').find('[type=checkbox]').prop('checked', isSelected);	
		}
		else {
			rows.children('td').children('[type=checkbox]').prop('checked', isSelected);	
		}
		
		
		if (!isHeader) {
			if (isSelected) {
				rows.addClass('jtable-row-selected');
				rows.addClass('ui-state-highlight');
			}
			else {
				rows.removeClass('jtable-row-selected');
				rows.removeClass('ui-state-highlight');
			}
		}
	}
	
	function loadMetadataList() {
		$('#metadataList').jtable('load', {}, 
			function(data){
				selectRows($('#metadataList tbody > tr'), true);
				selectRows($('#metadataList thead > tr'), true, true);
			}
		);
	}
	
	function openDlgMetadata() {
		initDlgMetadata();
		loadMetadataList();
		var div = r3p.createDialogDiv('metadataList', '<spring:message code="sysTable.title" />');
		var conf = {width: 600};
		conf.buttons = {
			'<spring:message code="sysTable.button.addSelected" />': function() {
				var dlgAddSelected = this;
				r3p.showConfirm('Save?', 'Save changes?', {}, function(){
						self.formMetadata.save(loadTableList);						
						r3p.closeDlg(dlgAddSelected);
					},
					function(){
						r3p.closeDlg(dlgAddSelected);
					}
				);
				
				
			},
			Cancel: function() {
				
				r3p.closeDlg(this);
			}
		};
		
		conf.id = div.attr('id');
		self.onLoad_Metadata = function(){
			r3p.showDialog(conf);
		};
	}
	
	function selectedRowsToJson() {
		var pTable = $('#metadataList'),
			$rows =$('.jtable-wrapper .jtable  tbody > tr.jtable-row-selected', pTable) ;
		
		var //$selectedRows = $('#metadataList').jtable('selectedRows'),
			jsonList = [];
		
		if ($rows.length == 0) return jsonList;
		
	    $rows.each(function () {
	        var record = $(this).data('record'),
	        	tableName = record.isColumn ? record.tableName : record.name;
	        
	        var tblBean = r3p.findByProperty(jsonList, 'name', tableName);
        	if (!tblBean) {
        		tblBean = createTableBean(tableName);
        		jsonList.push(tblBean);
        	}
	        
	        if (record.isColumn) {
	        	tblBean.columnList.push({selected: true, name: record.name, type: record.type});
	        }
	    });
		return jsonList; 
	}
	
	function createTableBean(name) {
		return {selected: true, name: name, columnList: []};
	}
</script>
	
<div id="metadataList"></div>
	