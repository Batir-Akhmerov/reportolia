	var URL_HOME = '/reportolia',
		URL_SHOW_DB_META = 'r3pDbMetadataShow.go',
		URL_SAVE_DB_META = 'r3pDbMetadataSave.go',
		URL_SHOW_DB_META_POPULATOR = 'r3pDbMetadataPopulateShow.go',
		URL_SHOW_TABLES = 'r3pTableListShow.go',
		URL_SHOW_TABLE  = 'r3pTableShow.go',
		URL_SHOW_TABLE_COLUMNS = 'r3pTableColumnListShow.go',
		URL_SHOW_TABLE_RELATIONSHIPS = 'r3pTableRelationshipsShow.go',
		URL_SHOW_RELATIONSHIPS_MAP = 'r3pRelationshipMapShow.go',
		URL_SHOW_REPORT_LIST = 'r3pReportListShow.go',
		URL_SHOW_REPORT = 'r3pReportShow.go',
		URL_SHOW_REPORT_COLUMN = 'r3pReportColumnShow.go',
		
		URL_ = '';
		

	function afterDomReady() {
		/*
		$('button').button();
		$('select:not(.noPlugin)').selectmenu();
		$('input[type=checkbox]:not(.noPlugin)').checkboxradio();
		$('input[type=radio]:not(.noPlugin)').checkboxradio();
		 */
	}
	
	function openReportList() {
		r3p.navigate(URL_SHOW_REPORT_LIST);
	}
	function openReport(id) {
		id = r3p.key(id);
		r3p.navigate(URL_SHOW_REPORT + '?id=' + id);
	}
	function openReportColumn(id) {
		id = r3p.key(id);
		r3p.navigate(URL_SHOW_REPORT_COLUMN + '?id=' + id);
	}
	

	function openDbMetadata() {
		r3p.navigate(URL_SHOW_DB_META);
	}

	var FWD_TABLES = 'TBL',
		FWD_RELATIONSHIPS = 'REL';
	function openDbMetadataPopulator(forwardTo) {
		r3p.navigate(URL_SHOW_DB_META_POPULATOR + '?p=' +forwardTo);
	}
	
	
	function openTableList() {
		r3p.navigate(URL_SHOW_TABLES);
	}
	function openTable(tableId) {
		r3p.navigate(URL_SHOW_TABLE + '?id=' + tableId);
	}
	function openTableColumns(tableId) {
		r3p.navigate(URL_SHOW_TABLE_COLUMNS + '?id=' + tableId);
	}
	function openTableRelationships(tableId) {
		r3p.navigate(URL_SHOW_TABLE_RELATIONSHIPS + '?id=' + tableId);
	}
	
	
	
	function openRelationshipMap() {
		r3p.navigate(URL_SHOW_RELATIONSHIPS_MAP);
	}
	
	function openVariableList() {
		alert("Implement me!");
	}