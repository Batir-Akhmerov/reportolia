	
	function openDlg_TableRelationshipList(tableId, label) {
		var dlgTitle = label + ' ' + REL_PAGE_TITLE;
		
		var btnConf = {
			noCancelBtn: true,
			buttons: [{
  					label: r3pMsg.BTN_CLOSE,
  					handler: function() {
  						if (r3p.isPageModified() && self.refreshMe) {
  			                self.refreshMe();
  						}
  						r3p.closeDialog(this);
  					}
  				}
  			]
		};
		var divId = 'dlgTableRelationships';
		var url = 'r3pTableRelationshipsLoad.go?tableId=' + tableId;
		if (self.dlgRelationshipListInitialized) {
			r3p.changeDialogDiv(divId, dlgTitle);
			self.tableRelationshipList.ajax.url(url).load();
			r3p.showDialog(divId);
			return;
		}
		
		var div = r3p.createDialogDiv(divId, dlgTitle, '<div id="relationshipList"/></div>', {
			'data-windows-style': 'true'
		}, btnConf);
		initDlg_TableRelationshipList(tableId, label, divId, url);
		self.dlgRelationshipListInitialized = true;
	}
	
	function initDlg_TableRelationshipList(tableId, label, divId, url) {
		setOwnerTable(tableId);		
		
		var tbConf = {
		        ajax: url,		       
		        r3pAjaxDelete: 'r3pTableRelationshipDelete.go',
		        r3pAjaxOpen: 'r3pTableRelationshipsShow.go',
		        scrollY: '400px',
		        paging: false,
		        ordering: false,
		        searching: false,
		        columns: [
		        	{data: 'id', r3p: 'KEY'},
		            {data: 'label', r3pLabel: r3pMsg.LBL_LABEL},
		            {data: 'columnId', r3pLabel: TH_COLUMN,
		            	render: function(data, type, full, meta) {
		            		return full.column ? full.column.label : '';
		    			}
		            },
		            {data: 'oneToMany', r3pLabel: LBL_MANY_TO_ONE,
		            	render: function(data, type, full, meta) {
		            		if (data) return '<--';
		                	return '-->';
		    			}
		            },
		            {data: 'linkedTableId', r3pLabel: TH_LINKED_TABLE,
		            	render: function(data, type, full, meta) {
		            		return full.linkedTable ? full.linkedTable.label : '';
		    			}
		            },
		            {data: 'linkedColumnId', r3pLabel: TH_LINKED_COLUMN,
		            	render: function(data, type, full, meta) {
		            		return full.linkedColumn ? full.linkedColumn.label : '';
		    			}
		    		},
		            {data: 'linkToFilter', r3pLabel: LBL_LINK_TO_FILTER,
		            	render: function(data, type, full, meta) {
		            		if (data) return '&#10004;';
	                    	return '';
		    			}
		            }
		        ],
		        r3pAfterInit: function (e, settings, data) {
		        	r3p.showDialog(divId);
		        }
		    };
			
			self.tableRelationshipList = r3pDtb.init('relationshipList', tbConf);
		
		/*
		r3p.jTable('relationshipList', {
            title: TITLE_CURRENT_TABLE, 
            selectOnRowClick: false,
            height: 400,
            actions: {
                listAction: 'r3pTableRelationshipsLoad.go',
                isJsonActions: true,
                defJson: function() { 
                	return {tableId: getOwnerTable()};
                },
                deleteAction: 'r3pTableRelationshipDelete.go',
                updateAction: 'r3pTableRelationshipSave.go'
            },
            fields: {
                id: {
                	key: true,
                	create: false,
                    edit: false,
                    list: false
                },
                label: {
                	title: r3pMsg.LBL_LABEL,
                	width: '20%',
                },
                columnId: {
                    title: TH_COLUMN,
                    width: '20%',
                    display: function(data) {
                    	return data.record.column.name;
                    },
                    dependsOn: 'tableId',
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'r3pOptionsTableColumnsLoad.go?tableId=0';
                        }
                        return 'r3pOptionsTableColumnsLoad.go?tableId=' + getOwnerTable(); 
                    }
                },
                oneToMany: {
                	title: '',
                	width: '5%',
                	type: 'radiobutton',
                	options: {'true': LBL_ONE_TO_MANY, 'false': LBL_MANY_TO_ONE},
                	display: function(data) {
                    	if (data.record.oneToMany) return '<--';
                    	return '-->';
                    },
                	defaultValue: true
                },
                linkedTableId: {
                    title: TH_LINKED_TABLE,
                    width: '20%',
                    display: function(data) {
                    	return data.record.linkedTable.name;
                    },
                    options: 'r3pOptionsTablesLoad.go'
                },
                linkedColumnId: {
                	width: '20%',
                    title: TH_LINKED_COLUMN,
                    display: function(data) {
                    	return data.record.linkedColumn.name;
                    },
	                dependsOn: 'linkedTableId',
	                options: function (data) {
	                    if (data.source == 'list') {
	                        return 'r3pOptionsTableColumnsLoad.go?tableId=0';
	                    }
	                    return 'r3pOptionsTableColumnsLoad.go?tableId=' + data.dependedValues.linkedTableId;
	                }
                },
                linkToFilter: {
                	title: LBL_LINK_TO_FILTER_INFO,
                	width: '15%',
                	type: 'checkbox',
                	values: { 'false' : r3pMsg.OPT_FALSE, 'true' : LBL_LINK_TO_FILTER },
                	display: function(data) {
                    	if (data.record.linkToFilter) return '&#10004;';
                    	return '';
                    }
                }
            },
            recordsLoaded: function(){
            	if (self.onLoad_TableRelationships) self.onLoad_TableRelationships();
            }
        });
        */
		
	}
	
	function setOwnerTable(tableId) {
		r3p.jq('relationshipList').prop('ownerTableId', tableId);
	}
	
	function getOwnerTable() {
		return r3p.jq('relationshipList').prop('ownerTableId');
	}
	
	function loadRelationshipList(tableId) {
		//$('#relationshipList').jtable('load', {tableId: tableId});
	}
	