	function initMetadataTable() {
		
		var tbConf = { 
		        ajax: 'r3pDbMetadataLoad.go',
		        order: [[ 2, "asc" ]],
		        r3pIsStriped: false,
		        r3pIsHovered: false,
		        columns: [
		            {data: 'selected', r3p: {type: 'FLD_CHECK', isRowSelector: true}},
		            {
		            	r3p: {
		            		type: 'BTN_EXPAND',
		            		onClick: expandColumns
		            	}
		            },
		            {data: 'name', r3pLabel: COL_TABLE_NAME},
		            {data: 'type', r3pLabel: COL_TYPE}, 
		            {data: 'schema', r3pLabel: COL_SCHEMA}
		        ],
		        r3pAfterInit: function (e, settings, data) {
		        	self.formMetadata = $('#metadataDiv table > tbody').r3pListForm({
	        			url: URL_SAVE_DB_META,
	        			itemSelector: 'tr', 
	        				idField: 'name',
	        				fnAddToList: selectedRowsToJson
	        		});				
			    }
		    };
			
		self.metadataList = r3pDtb.init('metadataDiv', tbConf);
		
	}
	
	var childCounter = 0;
	function initChildTable(divId, tr, row) {
		r3pDtb.selectRow(tr, true);
		var tbConf = {
			info: false,
			paging: false,
			searching: false,
			r3pIsStriped: false,
	        r3pIsHovered: false,
	        order: [[ 1, "asc" ]],
	        ajax: 'r3pDbMetadataColumnsLoad.go?tableName=' + row.data().name,
	        columns: [
	            {data: 'selected', r3p: {
		            	type: 'FLD_CHECK', 
		            	isRowSelector: true,
		            	handler: function(td, cell, colConf, isChecked) {
		            		var nestedSelectedTrs = r3pDtb.toDtbl(divId).rows('.selected' ).nodes().to$(),
		            			selectedCount = r3p.getSize(nestedSelectedTrs),
		            			isAnyChecked = isChecked || selectedCount > 1; 
		            		r3pDtb.selectRow(tr, isAnyChecked); 
		            	}
	            	}
	            },
	            {data: 'name', r3pLabel: COL_COLUMNS},
	            {data: 'type', r3pLabel: COL_DATA_TYPE},
	            {data: 'size', r3pLabel: COL_LENGTH, r3p: { type: 'LBL_NUMBER' }},
	            {data: 'tableName', visible: false, searchable: false},
	            {data: 'isColumn', visible: false, searchable: false, defaultValue: 'true'}	           
	        ]
	    };
		r3pDtb.init(divId, tbConf);
	}
	
	function expandColumns(td, cell, colConf) {
			var tr = td.closest('tr'),
			childId = tr.attr('childId'),
	    	row = self.metadataList.row( tr );
	
		if (childId) {
			var childTr = r3p.jq(childId).closest('tr');
			if (childTr.is(':visible')) childTr.hide();
			else childTr.show();
		}		            			
	    else {
	    	var index = childCounter++;
	    	childId = 'childTbl'+index;
	    	tr.attr('childId', childId);
	        row.child( '<div id="'+childId+'" class="childContainer"></div>' ).show();
	        initChildTable(childId, tr, row);
	    }
	}
	
		
	function selectedRowsToJson() {		
		var rows = self.metadataList.rows('.selected');
		
		var jsonList = [];
		
		
		if (rows.length == 0) return jsonList;
		
		rows.every( function ( rowIdx, tableLoop, rowLoop ) {
		    var record = this.data(),
		    	tr = this.node(),
		    	childId = $(tr).attr('childId'),
	        	tableName = record.name;
	        
	        var tblBean = r3p.findByProperty(jsonList, 'name', tableName);
        	if (!tblBean) {
        		tblBean = createTableBean(tableName);
        		jsonList.push(tblBean);
        	}
	        
	        if (childId) {
	        	var colRows = r3p.jq(childId + 'List').DataTable().rows('.selected');
	        	if (colRows.length > 0) {
	        		colRows.every( function () {
		    		    var childRecord = this.data();
		    	        tblBean.columnList.push({selected: true, name: childRecord.name, type: childRecord.type});
		    	    });
	        	}
	        }
	    });
	    
		return jsonList; 
	}
	
	function createTableBean(name) {
		return {selected: true, name: name, columnList: []};
	}
	
	function retrieveMetadata(forwardTo) {		
		r3p.showConfirm(MSG_ADD_SELECTED, {
			fnYes: function(){
				self.formMetadata.save(function(){
					if(forwardTo == 'TBL') openTableList();
				});
			}
		});
	}

	