var r3p = (function(){
	var _isPageModified = false
		;
	
	// validation
	if (!self.form2js) {
		alert('form2js utility is expected!');
		return;
	}
	
	
	
	// protected methods
	function _form2JsInterceptor(node) {
		var $node = $(node),
			tagName = $node.prop('tagName');
			name = $node.attr('name') || '';
		tagName = tagName || '';
		
		if (tagName.toLowerCase() == 'input') {
			var type = $node.prop('type');
			type = type || '';
			
			if (type.toLowerCase() == 'checkbox') {
				return {name: name, value: $node.prop('checked')};
			}
		}
        return false;
    };
    
   
    // public methods
	return {
		
		/******************************************/
	    /**   AJAX   ***************************/
	    /******************************************/
		ajax: function(conf, fnSuccess, fnError) {
			var dfd  = $.Deferred();
			var data = conf.json || {};
			delete conf.json;
			
			conf = $.extend({
					contentType: 'application/json;charset=UTF-8',
					dataType: 'json',
					type : 'POST',
					processData : false,
					cache: false,
			        data: JSON.stringify(data)
				}, conf);
			
			$.ajax(conf)
				.done(function(resp) { 
					dfd.resolve(resp);
					if (fnSuccess) fnSuccess(resp);
				})
				.fail(function(resp) {
					dfd.reject(resp);
					if (fnError) fnError(resp);
					else r3p.showError(r3pMsg.ERR_LABEL + (resp && resp.error ? resp.error : r3pMsg.ERR_UNEXPECTED_NAME));
				});
			return dfd.promise();
		},
		
		/******************************************/
	    /**   DIALOGS   ***************************/
	    /******************************************/		
		showConfirm: function(msg, btnConf, dlgConf, title) {
			var dfd  = $.Deferred();
			btnConf = btnConf || {};
			var defaultBtnConf = {
				noCancelBtn: true,
				buttons: [{
						label: btnConf.yesBtnLabel || r3pMsg.BTN_YES,
						cssClass: btnConf.yesBtnClass,
	  					handler: function() {
	  						dfd.resolve(true);
	  						if (btnConf.fnYes) btnConf.fnYes();
	  						r3p.closeDialog(this);
	  					}
	  				},
	  				{
	  					label: btnConf.noBtnLabel || r3pMsg.BTN_NO,
	  					cssClass: btnConf.noBtnClass,
	  					handler: function() {
	  						dfd.resolve(false);
	  						if (btnConf.fnNo) btnConf.fnNo();
	  						r3p.closeDialog(this);
	  					}
	  				}
	  			]
			};
			btnConf = $.extend({}, defaultBtnConf, btnConf);
			var id = 'dlg-confirm',
				div = r3p.createDialogDiv(id, title || r3pMsg.TLT_CONFIRM, msg, dlgConf, btnConf);
						
			r3p.showDialog(id, div);
			
			return dfd.promise();
		},
		
		showError: function(msg, dlgConf, btnConf, title) {
			var dfd  = $.Deferred(),
				id = 'dlg-error';
			dlgConf = $.extend({}, {'data-type': 'alert'}, dlgConf);
			
			var defaultBtnConf = {
				noCancelBtn: true,
				buttons: [{
					label: r3pMsg.BTN_CLOSE,
  					handler: function() {
						dfd.resolve(true);
						r3p.closeDialog(this);
					}
				}]
			};
			btnConf = $.extend({}, defaultBtnConf, btnConf);
			var div = r3p.createDialogDiv(id, title || r3pMsg.TLT_ERROR, msg, dlgConf, btnConf);
			
			r3p.showDialog(id, div);
						
			return dfd.promise();
		},
		
		showDialog: function(id, div) {
			var div = div || r3p.jq(id);
			if (!div.prop('isDlgInitialized')) {
				div.prop('isDlgInitialized', true);
				$.Metro.initWidgets(div);
			}
			var dlg = div.data('dialog');
			if (!dlg) {
				alert('Cannot find dialog ' + id);
				return;
			}
			if (div.prop('overlay')) div.prop('overlay').show();
			dlg.open();
		},
		
		closeDialog: function(obj) {
			var btn = null;
			if (!obj || obj.target) btn = $(this);
			else btn = $(obj);
			var div = btn.closest('[data-role="dialog"]');
			if (div.prop('overlay')) div.prop('overlay').hide();
			hideMetroDialog(div);
		},
		
		createDialogDiv: function (id, title, msg, dlgConf, btnConf) {
			var div = r3p.jq(id);
			if (r3p.isNullJq(div)) {
				btnConf = btnConf || {}; 
				var defaultConf = {
					id: id, 
					'data-role': 'dialog'
				};
				dlgConf = $.extend({}, defaultConf, dlgConf);				
				var overlay = r3p.createOverlay(dlgConf.id);
				div = r3p.createEl('div', 'body', dlgConf, null, 'padding20');
				div.prop('overlay', overlay);
				r3p.createEl('h1', div);
				r3p.createEl('div', div).addClass('clsDialogContent');
				r3p.createEl('div', div).addClass('clsDialogButtons');
			}
			else {
				$('body').append(div.prop('overlay')).append(div);
			}
			div.children('h1').html(title);
			div.children('div.clsDialogContent').html(msg);
			
			// buttons
			var btnDiv = div.children('div.clsDialogButtons').empty();				
			if (btnConf.buttons) {
				$.each(btnConf.buttons, function(i, btn){
					r3p.createDialogButton(btnDiv, btn);
				});
			}
			if (!btnConf.noCancelBtn) {
				r3p.createDialogButton(btnDiv, {
					label: btnConf.cancelBtnLabel || r3pMsg.BTN_CANCEL,
					handler: r3p.closeDialog
				});
			}
			
			return div;
		},
		changeDialogDiv: function (id, title, msg) {
			var div = r3p.jq(id);
			if (r3p.isNullJq(div)) return;
			if (title) div.children('h1').html(title);
			if (msg) div.children('div.clsDialogContent').html(msg);			
		},
		createOverlay: function(dlgId){
	        var overlayId = dlgId + '_overlay';
	        var overlay = r3p.jq(overlayId);

	        if (overlay.length === 0) {
	            overlay = $("<div/>").attr('id', overlayId).addClass('clsOverlay').appendTo('body');
	        }
	        
	        return overlay;
	    },
		createDialogButton: function(div, btn) {
			var btnEl = r3p.createEl('button', div).html(btn.label);
			 btnEl.addClass('button');
			if (btn.cssClass) btnEl.addClass(btn.cssClass);
			if (btn.handler) btnEl.click(btn.handler);
		},
		
		/******************************************/
	    /**   FORM   ***************************/
	    /******************************************/
		form2js: function(node) {
			return form2js(node, '.', true, _form2JsInterceptor);
		},
		
		urlToJson: function(url) {
			if (url) { 
				return JSON.parse(
					'{"' + url.replace(/&/g, '","').replace(/=/g,'":"') + '"}',
					function(key, value) { 
						return key==='' ? value : decodeURIComponent(value);
					}
				);
			}
			return {};
		},
		urlToJsonSafe: function(urlOrJson) {
			var json = urlOrJson;
			if (r3p.isString(urlOrJson)) json = r3p.urlToJson(urlOrJson);
			return json;
		},
		
		
		/******************************************/
	    /**   HTML   ***************************/
	    /******************************************/
		createEl: function(tag, parent, attrs, props, cls) {
	    	var el = $('<' + tag + '></' + tag + '>');
	    	if (parent) el.appendTo(parent);
	    	if (attrs) {
	    		$.each(attrs, function(name, val) {
	    			if (attrs.hasOwnProperty(name)) el.attr(name, val);
	    		});
	    	}
	    	if (props) {
	    		$.each(props, function(name, val) {
	    			if (attrs.hasOwnProperty(name)) el.prop(name, val);
	    		});
	    	}
	    	if (cls) el.addClass(cls);
	    	return el;
	    },
	    
	    
		
		sticky: function(el, bottomGap, rightGap) {
			el = $(el);
			//var offset = el.offset();
			if (r3p.isNotNull(bottomGap)) {
				//console.log(wnd.height());
				el.height($(window).height() - el.offset().top - bottomGap);
			}
			if (r3p.isNotNull(rightGap)) {
				el.width($(window).width() - el.offset().left - rightGap);
			}
		},
		
		onWindowResize: function(fnResize) {
			$(window).resize(
				r3p.getSafeTimeout(fnResize, {delay: 10})
			);
		},
		
	    /******************************************/
	    /**   PAGE   ***************************/
	    /******************************************/
		navigate: function(url) {
			window.location.href = url;
		},
		openWindow: function(url) {
			
		},
		isPageModified: function() {
			return _isPageModified;
		},
		setPageModified: function(modified) {
			_isPageModified = r3p.isNull(modified) ? true : modified;
		},
		
		/******************************************/
	    /**   UTILS   ***************************/
	    /******************************************/
		jq: function(idName) {
			idName = idName.replace( /(:|\.|\[|\]|,|=)/g, '\\$1' );
			var el = $('#' + idName);
			if (el.length == 0) el = $('[name=' + idName + ']');
			return el;
		},
		isEmpty: function(list) {
			return r3p.isNull(list) || list.length == 0;
		},
		isNullJq: function(el) {
			return !el || el.length == 0;
		},
		isNull: function(o) {
			return o == null || o == undefined;
		},
		isNotNull: function(o) {
			return !r3p.isNull(o);
		},
		isBlank: function(s) {
			return r3p.isNull(s) || s.length == 0;
		},
		isNotBlank: function(s) {
			return !r3p.isBlank(s);
		},
		isString: function(v) {
			return $.type(v) == 'string';
		},
		scriptSafe: function(txt) {
			return $('<div/>').text(txt).html();
		},
		strFormat: function(str){
			var args = arguments;
			return str.replace(/\{\{|\}\}|\{(\d+)\}/g, function (m, n) {
				if (m == "{{") { return "{"; }
				if (m == "}}") { return "}"; }
				return args[ parseInt(n) + 1 ];
			});
		},
		getSize: function(list) {
			return list && !r3p.isNull(list.length) ? list.length : 0;
		},
		findByProperty: function(list, propName, value) {
			if (r3p.isEmpty(list)) return null;
			var res = null;
			$.each(list, function(i, o){
				if (o[propName] == value) {
					res = o;
					return false;
				}
			});
			return res;
		},
		getSafeTimeout: function(fnHandler, conf) {
			if (!conf) {
				alert('Argument conf is required!');
			}
			return function() {
				if (conf.isSet) return;
				conf.isSet = setTimeout(function(){
					fnHandler();
					conf.isSet = null;
				}, conf.delay || 200);
			};
		},
		
		coalesceObjFn: function() {
			var res = null;
			for (var ind in arguments) {
				if (!arguments.hasOwnProperty(ind)) continue;
				var objFn = arguments[ind];
				if (!p3r.isNotNull(objFn) && $.isFunction(objFn)) {
					objFn = objFn();
				}
				if (!p3r.isNotNull(objFn)) {
					res = objFn;
					break;
				}
			}
			return res;
		}
		
		/*,
		renameJsonField: function(json, fld, newFld) {
			if (!json || json[fld] === undefined) return;
			json[newFld] = json[fld];
			delete json[fld];
		},
		subJsonField: function(json, fld, subFld) {
			if (!json || json[fld] === undefined) return;
			var v = json[fld];
			json[fld] = {};
			json[fld][subFld] = v;
		}
		*/
	};
}());

/**
 * List Form plugin encapsulates utilities to save/restore indexed form data from items of the html container.
 * html:
 * <form>
 * 		<div>
 * 			<input name="id" value="1" />
 * 			<input name="name" value="cat" />
 * 			<input name="type" value="animal" />
 * 		</div>
 * 		<div>
 * 			<input name="id" value="2" />
 * 			<input name="name" value="dog" />
 * 			<input name="type" value="animal" />
 * 		</div>
 * </form>
 * 
 * js:
 * 	var form = $('form').r3pListForm({
 * 		url: 'saveJsonAjax.go',
 * 		data: errorData, // form data from previous session. Used only in case of error.
 * 		jsonField: 'json', // default
 * 		itemSelector: 'div', // default
 * 		idField: 'id', // default, points to form > div > input[name=id]
 * 	});
 * 
 * saving data:
 * 	form.save();
 * 
 * sends json object:
 * 	json = {
 * 		list: [
 * 			{id: 1, name: 'cat', type: 'animal'},
 * 			{id: 2, name: 'dog', type: 'animal'}
 * 		],
 * 		removedList: [
 * 			{id: 3} // in case if item was removed
 * 		]
 * 	}
 * 
 */
(function($){
	$.fn.r3pListForm = function(conf) {
		if (!conf) return;
		
		var defaults = $.fn.r3pListForm.defaults;
		var o = $.extend({}, defaults, conf);
		return new listForm(this, o);
	};
	
	$.fn.r3pListForm.defaults = {
		jsonField: 'json', 
		itemSelector: 'div', 
		idField: 'id'
	};
	
	function listForm(form, conf){
		var that = this;
		
		// protected functions
		function _getItems() {
			return form.children(conf.itemSelector);
		};
		
		function _init() {
			if (items.length > 0) {
				$.each(items, function(i, itm){
					
				});
			}
		};
		
		function _toJson() {
			var items = _getItems();
				json = {};
			json['list'] = [];
			json['removedList'] = [];
			
			if (conf.fnAddToList) json.list = conf.fnAddToList(items);
			else if (items.length == 0) {			
				$.each(items, function(i, itm){
					json.list.push( r3p.form2js($(itm).get(0)) );
				});
			}
			
			if (conf.fnAddToRemovedList) json.removedList = conf.fnAddToRemovedList(items);
			
			
			return json;	
		};
		
		/*
		this.submitForm = function(f){
			if (!f) return;
			var jsonField = $('[name=json');
			if (jsonField.length == 0) {
				jsonField = $('<input></input>').attr('type', 'hidden').attr('name', 'json').appendTo(f);
			}
			jsonField.val(JSON.stringify(_getJson()));
			f.submit(); 
		};
		*/
		
		// public functions
		this.save = function(fnSuccess, fnError){
			var json = _toJson();
			r3p.ajax({url: conf.url, json: json}, fnSuccess, fnError);
		};
		
		
		
		return this;
	}
})(jQuery);

