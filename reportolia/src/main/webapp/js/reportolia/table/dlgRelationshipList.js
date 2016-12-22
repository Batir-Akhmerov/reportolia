	
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
	
	function initDlg_TableRelationshipList(tableId, tableLabel, divId, url) {
		setOwnerTable(tableId);		
		
		var tbConf = {
		        ajax: url,		       
		        r3pAjaxDelete: 'r3pTableRelationshipDelete.go',
		        r3pAjaxSave: 'r3pTableRelationshipSave.go',
		        r3pNewRow: {tableId: tableId},
		        r3pGetFormTitle: function(rowData) {
		        	return tableLabel;
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
		            	r3pSelectUrl: 'r3pOptionsTableColumnsLoad.go?tableId=' + tableId,
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
		        	r3p.showDialog(divId);
		        }
		    };
			
			self.tableRelationshipList = r3pDtb.init('relationshipList', tbConf);
		
		
		
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
	