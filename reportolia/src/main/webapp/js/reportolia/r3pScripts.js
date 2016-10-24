	var URL_SHOW_DB_META = 'r3pDbMetadataShow.go',
		URL_SAVE_DB_META = 'r3pDbMetadataSave.go',
		URL_SHOW_TABLES = 'r3pTableListShow.go',
		
		URL_ = '';
		

	function afterDomReady() {
		
		$('button').button();
		$('select:not(.noPlugin)').selectmenu();
		$('input[type=checkbox]:not(.noPlugin)').checkboxradio();
		$('input[type=radio]:not(.noPlugin)').checkboxradio();
		 
	}
	
	function openDbMetadata() {
		r3p.navigate(URL_SHOW_DB_META);
	}
	function openTableList() {
		r3p.navigate(URL_SHOW_TABLES);
	} 