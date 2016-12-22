/**
 * 
 */
package com.reportolia.core.web.controllers.base.datatable;

/**
 * The JsonResult class
 *
 * @author Batir Akhmerov
 * Created on Nov 24, 2016
 */
public class JsonResult {
	
	private String error;
	private int draw;
	

	public JsonResult(){
		//
	}	
	public JsonResult(String error){
		this(error, null);	
	}
	public JsonResult(String error, JsonForm form){
		this(form);
		this.error = error;		
	}
	public JsonResult(JsonForm form){
		if (form != null) {
			this.draw = form.getDraw();
		}
	}
	

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getDraw() {
		return this.draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}
	
}
