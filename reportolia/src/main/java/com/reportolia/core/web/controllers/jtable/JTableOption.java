/**
 * 
 */
package com.reportolia.core.web.controllers.jtable;

/**
 * The JTableOption class  is used as data transfer object between MVC and OptionList of JQuery JTable plugin.
 * Sorry, class fields do not meet Java Bean Naming Convention and violate Java incapsulation rules 
 * in order to satisfy ASP-oriented names expected by JTable. 
 *
 * @author Batir Akhmerov
 * Created on Oct 25, 2016
 */
public class JTableOption {
	public String DisplayText;
	public long Value;
	
	public JTableOption(){
		//
	}
	
	public JTableOption(String d, long v){
		this.DisplayText = d;
		this.Value = v;
	}
}
