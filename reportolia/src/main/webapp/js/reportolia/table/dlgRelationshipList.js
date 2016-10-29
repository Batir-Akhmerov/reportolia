	
	function openDlg_TableRelationshipList(tableId, label) {
		TITLE_CURRENT_TABLE = label || TITLE_CURRENT_TABLE;
		initDlg_TableRelationshipList(tableId);
		loadRelationshipList(tableId);
		var div = r3p.createDialogDiv('relationshipList', TITLE_PAGE);
		var conf = {width: 900};
		conf.buttons = {};
		conf.buttons[r3pMsg.BTN_CLOSE]= function() {
			if (r3p.isPageModified() && self.refreshMe) {
                self.refreshMe();
			}
			r3p.closeDlg(this);
		};
		
		conf.id = div.attr('id');
		self.onLoad_TableRelationships = function(){
			r3p.showDialog(conf);
		};
		
	}
	
	function initDlg_TableRelationshipList(tableId) {
		setOwnerTable(tableId)
		
		if (self.dlgRelationshipListInitialized) return;
		
		
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
		self.dlgRelationshipListInitialized = true;
	}
	
	function setOwnerTable(tableId) {
		r3p.jq('relationshipList').prop('ownerTableId', tableId);
	}
	
	function getOwnerTable() {
		return r3p.jq('relationshipList').prop('ownerTableId');
	}
	
	function loadRelationshipList(tableId) {
		$('#relationshipList').jtable('load', {tableId: tableId});
	}
	