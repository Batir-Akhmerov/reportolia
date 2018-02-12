	
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
		
		
		r3p.showDetail(divId, dlgTitle, '<div id="relationshipList"/></div>', {
				yesBtnLabel: r3pMsg.BTN_CLOSE,
				fnSave: function(dlg, e, dfd){
					if (r3p.isPageModified() && self.refreshMe) {
		                self.refreshMe();
					}
					dfd.resolve(true);
					r3p.closeModal(e);					
					return false;
				}
			},
			{size: 'large'}
		); 
		
		initDlg_TableRelationshipList(tableId, label, divId, url);
		//self.dlgRelationshipListInitialized = true;
	}
	
	
	