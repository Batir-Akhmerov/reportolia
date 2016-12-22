	var URL_HOME = '/reportolia',
		URL_SHOW_DB_META = 'r3pDbMetadataShow.go',
		URL_SAVE_DB_META = 'r3pDbMetadataSave.go',
		URL_SHOW_TABLES = 'r3pTableListShow.go',
		URL_SHOW_TABLE  = 'r3pTableShow.go',
		URL_SHOW_TABLE_COLUMNS = 'r3pTableColumnListShow.go',
		URL_SHOW_RELATIONSHIPS_MAP = 'r3pRelationshipMapShow.go',
		URL_ = '';
		

	function afterDomReady() {
		/*
		$('button').button();
		$('select:not(.noPlugin)').selectmenu();
		$('input[type=checkbox]:not(.noPlugin)').checkboxradio();
		$('input[type=radio]:not(.noPlugin)').checkboxradio();
		 */
	}
	
	function openDbMetadata() {
		r3p.navigate(URL_SHOW_DB_META);
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
	
	function openRelationshipMap() {
		r3p.navigate(URL_SHOW_RELATIONSHIPS_MAP);
	}