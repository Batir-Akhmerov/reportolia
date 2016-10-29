/**
 * 
 */
package com.reportolia.core.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * The MessageResourceHandler class
 *
 * @author Batir Akhmerov
 * Created on Oct 28, 2016
 */
@Component
public class MessageResourceHandler {
	
    @Autowired
    private MessageSource messageSource;
    
    public String get(String key, Object...args) {
        Locale locale = LocaleContextHolder.getLocale();                        
        return messageSource.getMessage(key, args, locale);
    }

}
