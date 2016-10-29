/**
 * 
 */
package com.reportolia.base.model;

/**
 * The KeyLabel class
 *
 * @author Batir Akhmerov
 * Created on Oct 25, 2016
 */
public class KeyLabel {
	
	private long id;
	private String label;
	
	public KeyLabel() {
		//
	}
	
	public KeyLabel(long id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
