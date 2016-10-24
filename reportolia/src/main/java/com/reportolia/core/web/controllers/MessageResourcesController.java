package com.reportolia.core.web.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the dynamic message resources used in JS code
 */
@Controller
public class MessageResourcesController {
	
	@RequestMapping(value = "/js/reportolia/r3pMessagesLoad")
	public String show(Locale locale, Model model) {
		return "messages/r3pMsg";
	}
	
}
