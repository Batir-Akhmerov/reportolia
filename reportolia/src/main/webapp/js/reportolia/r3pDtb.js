/**
 * Reportolia's Datatables Utils
 */

var r3pDtb = (function(){
	
	var defaultConf = {
		//scrollY: '420px'
	};
	
	function _prepareColumns(id, dbConf) {
		var hasButtons = false;
		if (dbConf.columns) {
			for (var i = 0, size = dbConf.columns.length; i < size; i++) {
				col = dbConf.columns[i];
				if (col.r3p) {
					if ($.type(col.r3p) == 'string') {
						col.r3p = {type: col.r3p};
					}					
					col = _buildColConf(col.r3p.type, col);
					if (col.r3p.isButton) hasButtons = true;
					dbConf.columns[i] = col;
				}
			}
		}
		
		if (dbConf.r3pAjaxCopy) {
			dbConf.columns.push(_buildColConf('BTN_COPY', null, dbConf.r3pAjaxCopy));
			hasButtons = true;
		}
		if (dbConf.r3pAjaxSave) {
			dbConf.columns.push(_buildColConf('BTN_EDIT', null, dbConf.r3pAjaxSave));
			hasButtons = true;
		}
		if (dbConf.r3pAjaxDelete) {
			dbConf.columns.push(_buildColConf('BTN_DELETE', null, dbConf.r3pAjaxDelete));
			hasButtons = true;
		}
		if (dbConf.r3pAjaxOpen) {
			dbConf.columns.push(_buildColConf('BTN_OPEN', null, dbConf.r3pAjaxOpen));
			hasButtons = true;
		}
		return hasButtons;
	}
	
	
	function _createTable(id, containerId, dbConf) {
		var div = r3p.jq(containerId),
			cssClass = (dbConf.r3pIsStriped != false ? ' striped ' : '') + (dbConf.r3pIsHovered != false ? ' hovered ' : '') + (dbConf.r3pCssClass ? ' ' + dbConf.r3pCssClass : ''),
			tbl = r3p.createEl('table', div, {id: id, 'data-autowidth': false, style: 'width:100%'}, null, 'dataTable table ' + cssClass);
			thead = r3p.createEl('thead', tbl),
			tr = r3p.createEl('tr', thead);
		if (dbConf.columns) {
			for (var i = 0, size = dbConf.columns.length; i < size; i++) {
				var colConf = dbConf.columns[i],
					r3 = colConf.r3p;
				var th = r3p.createEl('th', tr).html(colConf.r3pLabel || ' ');
				if(r3 && r3.thRenderer) {
					r3.thRenderer(th, tr, tbl, dbConf);
				}
			}
		}
		return tbl;
	}
	
	function _postInit(_tb, _dtbl, hasButtons) {
		var __checkboxCkicker = function() {
			var el = $(this);
			_clickCheckbox(el, _tb, _dtbl);
		};
		var __buttonClicker = function() {
			var el = $(this);
			_clickCellButton(el, _tb, _dtbl)
		};
		
		_dtbl.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
		    var rec = this.data(),
		    	tr = this.node();
		    $(tr).on( 'click', 'td input[type=checkbox].clsRowSelector', __checkboxCkicker);
		    if (hasButtons) $(tr).on( 'click', 'td > span > span.cellBtn',__buttonClicker);
		});
	}
	
	function _clickCheckbox(el, _tb, _dtbl) {
		var td = el.closest('td'),
	    	cell = _dtbl.cell(td),
	    	isChecked = el.prop('checked'),
	    	colConf = cell.context[0].aoColumns[cell.index().column],
	    	clickHandler = colConf.r3p.handler; 
		if (clickHandler) {
			clickHandler(td, cell, colConf, isChecked);
	    }
		r3pDtb.markRowSelected(el.closest('tr'), isChecked);
    }
	
	function _clickCellButton(el, _tb, _dtbl) {
		var td = el.closest('td'),
	    	cell = _dtbl.cell(td),
	    	colConf = cell.context[0].aoColumns[cell.index().column],
	    	btnHandler = colConf.r3p.handler; 
	    if (btnHandler) {
	    	btnHandler(td, cell, colConf, _tb, _dtbl);
	    }
	}
	
	
	
	function _buildColConf(type, col, actionUrl) {
		col = col || {};
		if (actionUrl) col.actionUrl = actionUrl;
		var defConf = r3pCol[type] || {},
			templateConf = defConf && defConf.r3p ? (r3pCol[defConf.r3p.templateType] || {}) : {};
		return $.extend(true, {}, templateConf, defConf, col);
	}
	
	
   
    // public methods
	return {
		init: function(containerId, dbConf) {
			dbConf = $.extend({}, defaultConf, dbConf);
			
			var tblId = r3pDtb.toTableId(containerId);
			var hasButtons = _prepareColumns(tblId, dbConf);
			
			var _tb = _createTable(tblId, containerId, dbConf), 
				_dtbl = _tb.DataTable(dbConf).on( 'init.dt', function(e, settings, data){
					if (_tb.prop('isInitizlized')) return;
					_postInit(_tb, _dtbl, hasButtons);
					if (dbConf.r3pAfterInit) dbConf.r3pAfterInit(e, settings, data);
					_tb.prop('isInitizlized', true);
				});
			return _dtbl;
		},
		toTableId: function(containerId) {
			return containerId + '_tbl';
		},
		toDtbl: function(containerId) {
			return r3p.jq(r3pDtb.toTableId(containerId)).DataTable();
		},
		selectRow: function(tr, selected, cellIndex) {
			selected = r3p.isNull(selected) ? true : selected;
			tr = $(tr);
			if ((tr.hasClass('selected') && selected) || (!tr.hasClass('selected') && !selected)) return;
			r3pDtb.clickCheckbox(tr, cellIndex);
		},
		selectAllRows: function(tbl, selected, cellIndex){
			var checkTrs = tbl.DataTable().rows( selected ? ':not(.selected)' : '.selected' ).nodes().to$();
			if (checkTrs.length > 0) {
				checkTrs.each(function() {
					r3pDtb.clickCheckbox(this, cellIndex);
				});
			}
			
		},		
		clickCheckbox: function(tr, cellIndex) {
			cellIndex = r3p.isNull(cellIndex) ? 0 : cellIndex
			$('input[type=checkbox]', $(tr).find('td')[cellIndex]).trigger('click');
		},
		markRowSelected: function(tr, selected) {
			tr = $(tr);
			if (selected) {
				if ( !tr.hasClass('selected') ) { 
					tr.addClass('selected');
		        }
			}
			else tr.removeClass('selected');
		}
	};
}());


var r3pCol = ( function(){
	
	var TMPL_BTN = '<span class="{0}"></span>',
		TMPL_BTN_HINT = '<span class="cellBtnHint" data-role="hint" data-hint-position="top" data-hint="{1}">'+TMPL_BTN+'</span>',
		TMPL_CHECK = '<label class="input-control checkbox small-check">' +
					    '<input type="checkbox" class="{3}" name="{0}" value="{1}" {2} />'+
					    '<span class="cellCheckFlag check"></span>'+
					'</label>';
	
	function _btnRenderer(data, type, full, meta) {
		var conf = meta.settings;
		var colConf = conf.aoColumns[meta.col];
		if (!colConf.r3p || !colConf.r3p.isButton) return '***';
		var c = colConf.r3p,
			btn = r3p.strFormat(TMPL_BTN, 'cellBtn cellBtnHover ' + c.icon);
		if (c.tooltip) {
			return r3p.strFormat(TMPL_BTN_HINT, 'cellBtn cellBtnHover ' + c.icon, c.tooltip);
		}
		return btn;
	}	
	
	function _getColConf(meta) {
		var conf = meta.settings;
		return conf.aoColumns[meta.col];
	}
	
	function _getRenderedRow(meta) {
		return meta.settings.aoData[meta.row].nTr;
	}
	
	function _markRowSelected(meta, selected) {
		var tr = $( _getRenderedRow(meta) );
		r3pDtb.markRowSelected(tr, selected);
	}

    
	return {
		KEY: {
			visible: false, 
			searchable: false
		},
		LBL: {
				
		},
		LBL_CHECK: {
			render: function(data, type, full, meta){
	    		var isChecked = data == true;
	    		return isChecked ? '<span class="mif-checkmark"></span>' : '';
	    	} 
		},
		
	// FIELDS
		FLD_CHECK: {
			orderable: false,
			width: 30,
			r3p: {
				type: 'FLD_CHECK',
				isField: true,
	    		thRenderer: function(th, tr, tbl, dbConf){
    				var btn = r3p.strFormat(TMPL_CHECK, 'selectAll', 'true', 'checked');
    				th.append(btn);
    				$('[name=selectAll]', th).click(function(){
    					var checkAll = $(this).prop('checked'),
    						cellIndex = th[0].cellIndex;    					
    					r3pDtb.selectAllRows(tbl, checkAll, cellIndex);    					
    				});
	    		}
			},
			render: function(data, type, full, meta) {
				var colConf = _getColConf(meta);
				if (!colConf.r3p) return '***';
				var c = colConf.r3p,
					fldName = colConf.data,
					name = fldName ? fldName : '',
					value = fldName ? full[fldName] : false,
					isChecked = value == false,
					checked = isChecked? 'checked' : '',
					cssClass = c.isRowSelector ? 'clsRowSelector' : '';
				
				var btn = r3p.strFormat(TMPL_CHECK, name, value, checked, cssClass);
				if (isChecked) _markRowSelected(meta, isChecked);
				
				return btn;
			}	
		},
			
	// BUTTONS
		_BTN_TEMPLATE: {
			r3p: {
				isButton: true,
				onClick: null
			},
			width: 30,
			render: _btnRenderer
		},
		
		BTN_COPY: {
			r3p: {
				type: 'BTN_COPY', 
				templateType: '_BTN_TEMPLATE',
				tooltip: r3pMsg.BTN_INFO_COPY,
				icon: 'mif-stack2',
				handler: function(td, cell, colConf){
	    			alert('copy?');
	    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    		}
			}
		},
		BTN_DELETE: {
			r3p: {
				type: 'BTN_DELETE',
				templateType: '_BTN_TEMPLATE',
				tooltip: r3pMsg.BTN_INFO_DELETE,
				icon: 'mif-bin',
				handler: function(td, cell, colConf, tb, dtbl) {
					var row = dtbl.row(td.closest('tr'));
					r3p.showConfirm(r3pMsg.BTN_INFO_DELETE)
						.then(function(confirm){
							if (!confirm) return;
							r3p.ajax({
								url: colConf.actionUrl,
								json: {
									id: row.data().id
								}
							})
							.then(function(resp) {
								if (resp.error) {
									r3p.showError(resp.error);
									return;
								}
								row.remove().draw(false);
				    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
							});
						})
					/*
					, 
						{
							fnYes: function(){	
								r3p.ajax(
									{
										url: colConf.actionUrl,
										json: {
											id: row.data().id
										}
									}
									, function(resp) {
										if (resp.error) {
											alert(resp.error);
											return;
										}
										row.remove().draw( false );
						    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
									}
								);
								
							}
						}, 
						{
							//'data-overlay': 'false',
							'data-overlay-color': 'blank'
						}
						
					);
					*/
	    		}
			}
		},
		BTN_EDIT: {
			r3p: {
				type: 'BTN_EDIT',
				templateType: '_BTN_TEMPLATE',
				tooltip: r3pMsg.BTN_INFO_EDIT,
				icon: 'mif-pencil',
				handler: function(td, cell, colConf){
	    			alert('edit?');
	    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    		}
			}
		},
		BTN_EXPAND: {
			className:      'details-control',
			orderable:      false,
			data:           null,
			width: 30,
			defaultContent: r3p.strFormat(TMPL_BTN_HINT, 'cellBtn cellBtnExpander mif-plus', r3pMsg.BTN_INFO_EXPAND),
			r3p: {
				type: 'BTN_EXPAND',
				isButton: true,
				onClick: null,
				tooltip: r3pMsg.BTN_INFO_EXPAND_COLLAPSE,
				handler: function(td, cell, colConf){
					var spanBtn = td.find('span.cellBtnExpander');
					spanBtn.toggleClass('mif-plus');
					spanBtn.toggleClass('mif-minus');
					var spanHint = td.find('span.cellBtnHint');
					if (spanBtn.hasClass('mif-plus')) spanHint.prop('data-hint', r3pMsg.BTN_INFO_EXPAND);
					else spanHint.prop('data-hint', r3pMsg.BTN_INFO_COLLAPSE);
					
					if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    		}
			}
		},
		BTN_OPEN: {
			r3p: {
				type: 'BTN_OPEN',
				templateType: '_BTN_TEMPLATE',				
				tooltip: r3pMsg.BTN_INFO_OPEN,
				icon: 'mif-chevron-thin-right',
				handler: function(td, cell, colConf){
	    			alert('open?');
	    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    		}
			}
		}
		
		
		
	};
}());

