/**
 * Reportolia's Datatables Utils
 */

var r3pDtb = (function(){
	
	var defaultConf = {
	};
	
	function _prepareColumns(id, dbConf) {
		var hasButtons = false;
		if (dbConf.columns) {
			for (var i = 0, size = dbConf.columns.length; i < size; i++) {
				col = dbConf.columns[i];
				if (r3p.isNull(col.r3p)) {
					col.r3p = {type: 'LBL'};
				}
				if ($.type(col.r3p) == 'string') {
					col.r3p = {type: col.r3p};
				}					
				col = _buildColConf(col.r3p.type, col);
				if (col.r3p.isButton) hasButtons = true;
				dbConf.columns[i] = col;
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
	
	function _prepareAjax(dbConf) {
		if (!dbConf && !dbConf.ajax) return;
		
		var defaultAjax = {
			error: r3p.ajaxErrorCallback
		};
		if ($.type(dbConf.ajax) == 'string') dbConf.ajax = {url: dbConf.ajax};
		else if ($.type(dbConf.ajax) == 'function') dbConf.ajax = {url: dbConf.ajax()};
		dbConf.ajax = $.extend({}, defaultAjax, dbConf.ajax);
	}
	
	function _createTable(id, containerId, dbConf) {
		var div = r3p.jq(containerId),
			cssClass = (dbConf.r3pIsStriped != false ? ' striped ' : '') + (dbConf.r3pIsHovered != false ? ' hovered ' : '') + (dbConf.r3pCssClass ? ' ' + dbConf.r3pCssClass : '');
		// button toolbar
		if (dbConf.r3pAjaxSave && dbConf.r3pNewRow) {
			var	btnNew = $( r3p.strFormat(r3p.TMPL_BTN, r3pMsg.BTN_CREATE, 'mif-plus', 'primary') )
				.appendTo(div);
			var newRowData = $.isPlainObject(dbConf.r3pNewRow) ? dbConf.r3pNewRow : {id: 0};	
			btnNew.click(function(){
				r3pDtb.openRowForm(newRowData, dbConf.r3pAjaxSave, r3p.jq(id).DataTable());
			});		
			$('<hr class="thin bg-grayLighter">').appendTo(div);
		}
		
		var	tbl = r3p.createEl('table', div, {id: id, 'data-autowidth': false, style: 'width:100%'}, null, 'dataTable table ' + cssClass);
			thead = r3p.createEl('thead', tbl),
			tr = r3p.createEl('tr', thead);
		if (dbConf.columns) {
			for (var i = 0, size = dbConf.columns.length; i < size; i++) {
				var colConf = dbConf.columns[i],
					r3 = colConf.r3p;
				var th = r3p.createEl('th', tr).html(r3.label || ' ');
				if(r3 && r3.thRenderer) {
					r3.thRenderer(th, tr, tbl, dbConf);
				}
			}
		}
		
		return tbl;
	}
	
	function _postDraw(_tb, _dtbl, hasButtons) {
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
		col = col || {r3p: {}};
		if (actionUrl) col.actionUrl = actionUrl;
		_toR3pProperty(col, 'r3pLabel', 'label');
		_toR3pProperty(col, 'r3pSelectUrl', 'selectUrl');
		_toR3pProperty(col, 'r3pSelectOptions', 'selectOptions');
		var defConf = r3pCol[type] || {},
			templateConf = defConf && defConf.r3p ? (r3pCol[defConf.r3p.templateType] || {}) : {};
		return $.extend(true, {}, templateConf, defConf, col);
	}
	
	function _toR3pProperty(colConf, propName, r3pName) {
		if (colConf[propName]) {
			colConf.r3p[r3pName] = colConf[propName];
			delete colConf[propName];
		}
	}
	
	function _selectRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList) {
		var fldName = tbColConf.data,
			type = '',
			ajaxUrl = r3pConf.selectUrl,
			optionValue = trData[tbColConf.data],
			optionLabel = tbColConf.render ? tbColConf.render(optionValue, type, trData) : optionValue;
		$(r3p.strFormat(r3p.TMPL_SELECT, fldName, optionValue, label, optionLabel, 'clsBlock clsMarginBottom20')).appendTo($panel);
		
	    afterShowCallbackList.push(function(){
	    	var sel = r3p.jq(fldName),
				widgetDiv = sel.closest('.input-control');
	    	$.Metro.initWidgets(widgetDiv);
	    	r3p.initSelect(sel, r3pConf.selectUrl, r3pConf);
	    });
	}
	
	function _hiddenRenderer($panel, trData, tbColConf, r3pConf, afterShowCallbackList) {
		var value = trData[tbColConf.data];
		value = r3p.isBlank(value) ? '' : value;
		$(r3p.strFormat(r3p.TMPL_HIDDEN, tbColConf.data, value)).appendTo($panel);
	}
	
	function _inputRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList, isDisabled) {
		var value = trData[tbColConf.data],
			isDisabled = isDisabled ? ' disabled' : '';
		value = r3p.isBlank(value) ? '' : value;
		$(r3p.strFormat(r3p.TMPL_INPUT, tbColConf.data, value, label, 'clsBlock clsMarginBottom20', isDisabled)).appendTo($panel);
	}
	
	function _inputDisabledRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList) {
		var value = trData[tbColConf.data];
		value = r3p.isBlank(value) ? '' : value;
		$(r3p.strFormat(r3p.TMPL_INPUT_DISABLED, tbColConf.data, value, label, 'clsBlock clsMarginBottom20')).appendTo($panel);
	}
	
	function _checkboxRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList, isDisabled) {
		var fldName = tbColConf.data,
			value = trData[tbColConf.data],
			isChecked = value == true || value == 'true' ? 'checked' : '',
			isDisabled = isDisabled ? ' disabled' : '';
		$(r3p.strFormat(r3p.TMPL_CHECKBOX, fldName, isChecked, label, 'clsBlock clsMarginBottom20', isDisabled)).appendTo($panel);
	}
		
	function _radioRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList) {
		if (r3p.getSize(r3pConf.options) == 0) {
			alert('R3p Radio Options expected!');
			return;
		}
		var fldName = tbColConf.data,
			value = trData[tbColConf.data],
			div = $('<div class="clsBlock clsMarginBottom20"></div>').appendTo($panel);
			
		for (var i = 0, size = r3pConf.options.length; i < size; i++) {
			var opt = r3pConf.options[i],
				isChecked = opt.value == value ? 'checked' : '';
			$(r3p.strFormat(r3p.TMPL_RADIO, fldName, opt.value, opt.label, isChecked, 'clsMarginRight20', opt.title ? 'title="' + opt.title + '"' : '')).appendTo(div);
		}
		
	}
   
    // public methods
	return {
		init: function(containerId, dbConf) {
			dbConf = $.extend({}, defaultConf, dbConf);
			
			var tblId = r3pDtb.toTableId(containerId);
			var hasButtons = _prepareColumns(tblId, dbConf);
			
			_prepareAjax(dbConf);
			
			var _tb = _createTable(tblId, containerId, dbConf), 
				_dtbl = _tb.DataTable(dbConf)
					.on( 'init.dt', function(e, settings, data){
						if (_tb.prop('isInitizlized')) return;						
						if (dbConf.r3pAfterInit) dbConf.r3pAfterInit(e, settings, data);
						_tb.prop('isInitizlized', true);
					}).on( 'draw.dt', function(e, settings, data){						
						_postDraw(_tb, _dtbl, hasButtons);						
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
		},
		openRowForm: function(trData, saveUrl, _dtbl) {	    			
			
			var $panel = $('<div></div>').addClass('content');
			
			var conf = _dtbl.context[0],
				initConf = conf.oInit ? conf.oInit : {},
				afterShowCallbackList = [],
				dlgTitle = initConf.r3pGetFormTitle ? initConf.r3pGetFormTitle(trData) : null;
			for (var i = 0, size = conf.aoColumns.length; i < size; i++) {
				var tbColConf = conf.aoColumns[i];

				var r3pConf = tbColConf.r3p;
				if (r3pConf.isButton) continue;
				label = r3pConf.label;
				
				if (r3pConf.selectUrl) _selectRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList);
				else if (r3pConf.type == 'FLD_CHECK' || r3pConf.type == 'LBL_CHECK') _checkboxRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList, tbColConf.readOnly);
				else if (r3pConf.type == 'LBL_RADIO') _radioRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList);
				else if (!tbColConf.readOnly && tbColConf.visible == false) _hiddenRenderer($panel, trData, tbColConf, r3pConf, afterShowCallbackList);				
				else _inputRenderer($panel, trData, tbColConf, label, r3pConf, afterShowCallbackList, tbColConf.readOnly);
			}
			
			r3p.showDetail('row-form', dlgTitle, $panel, null, null,
    			function(dlg){							
					var jsonData = r3p.form2js($panel.get(0));
					r3p.ajax(
						{url: saveUrl, json: jsonData}, 
						function(resp){
							if (r3p.isAjaxErrorShown(resp)) return;
							r3p.setPageModified();
							_dtbl.ajax.reload();
							r3p.closeDialog(dlg);
						},
						r3p.ajaxErrorCallback
					);
				}
			); 
			for(var i = 0, size = afterShowCallbackList.length; i < size; i++) {
				afterShowCallbackList[i].call();
			}
		}
	};
}());


var r3pCol = ( function(){
	
	function _btnRenderer(data, type, full, meta) {
		var conf = meta.settings;
		var colConf = conf.aoColumns[meta.col];
		if (!colConf.r3p || !colConf.r3p.isButton) return '***';
		var c = colConf.r3p,
			btn = r3p.strFormat(r3p.TMPL_BTN_CELL, 'cellBtn cellBtnHover ' + c.icon);
		if (c.tooltip) {
			return r3p.strFormat(r3p.TMPL_BTN_CELL_HINT, 'cellBtn cellBtnHover ' + c.icon, c.tooltip);
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
	    		return isChecked ? '<span class="oi oi-check"></span>' : '';
	    	} 
		},
		LBL_RADIO : {
			render: function(data, type, full, meta) {
				var colConf = _getColConf(meta);
				if (!colConf.r3p || r3p.getSize(colConf.r3p.options) == 0) return data;
				var opt = r3p.findByProperty(colConf.r3p.options, 'value', data);
				if (!opt) return data;
	    		return '<span '+(opt.title? 'title="' + opt.title+ '"' : '')+'>'+opt.label+'</span>' ;
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
    				var btn = r3p.strFormat(r3p.TMPL_CHECK_NO_LBL, 'selectAll', 'true', 'checked');
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
				
				var btn = r3p.strFormat(r3p.TMPL_CHECK_NO_LBL, name, value, checked, cssClass);
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
					r3p.showConfirm(r3pMsg.BTN_INFO_DELETE, {yesBtnClass: 'danger'})
						.then(function(confirm){
							if (!confirm) return;
							r3p.ajax({
								url: colConf.actionUrl,
								json: {
									id: row.data().id
								}
							})
							.then(function(resp) {
								if (r3p.isAjaxErrorShown(resp)) return;
								row.remove().draw(false);
				    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
				    			r3p.setPageModified();
							});
						});
	    		}
			}
		},
		BTN_EDIT: {
			r3p: {
				type: 'BTN_EDIT',
				templateType: '_BTN_TEMPLATE',
				tooltip: r3pMsg.BTN_INFO_EDIT,
				icon: 'mif-pencil',
				handler: function(td, cell, colConf, _tb, _dtbl){	    			
	    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    			var trData = _dtbl.row($(td).closest('tr')).data();
	    			
	    			r3pDtb.openRowForm(trData, colConf.actionUrl, _dtbl)
	    			
	    		}
			}
		},
		BTN_EXPAND: {
			className:      'details-control',
			orderable:      false,
			data:           null,
			width: 30,
			defaultContent: r3p.strFormat(r3p.TMPL_BTN_CELL_HINT, 'cellBtn cellBtnExpander oi oi-caret-right', r3pMsg.BTN_INFO_EXPAND),
			r3p: {
				type: 'BTN_EXPAND',
				isButton: true,
				onClick: null,
				tooltip: r3pMsg.BTN_INFO_EXPAND_COLLAPSE,
				handler: function(td, cell, colConf, _tb, _dtbl){
					var spanBtn = td.find('span.cellBtnExpander');
					spanBtn.toggleClass('oi-caret-right');
					spanBtn.toggleClass('oi-caret-bottom');
					var spanHint = td.find('span.cellBtnHint');
					if (spanBtn.hasClass('oi-caret-right')) spanHint.prop('title', r3pMsg.BTN_INFO_EXPAND);
					else spanHint.prop('title', r3pMsg.BTN_INFO_COLLAPSE);
					
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
				handler: function(td, cell, colConf, _tb, _dtbl){
	    			alert('open?');
	    			if (colConf.r3p.onClick) colConf.r3p.onClick(td, cell, colConf);
	    		}
			}
		}
		
		
		
	};
}());

