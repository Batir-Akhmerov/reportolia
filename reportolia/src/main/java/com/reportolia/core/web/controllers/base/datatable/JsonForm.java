/**
 * 
 */
package com.reportolia.core.web.controllers.base.datatable;

/**
 * The JsonForm class
 *
 * @author Batir Akhmerov
 * Created on Nov 24, 2016
 */
public class JsonForm {
	
	
	private int draw;
	private int start;
	private int length;
	

	public JsonForm(){
		//
	}


	public int getDraw() {
		return this.draw;
	}


	public void setDraw(int draw) {
		this.draw = draw;
	}


	public int getStart() {
		return this.start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLength() {
		return this.length;
	}


	public void setLength(int length) {
		this.length = length;
	}
	
	
}
