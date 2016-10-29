<%@ page contentType="text/javascript" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

var r3pMsg = {
	TLT_CONFIRM: '<spring:message code="title.confirm" />',
	TLT_ERROR:   '<spring:message code="title.error" />',
	TLT_QUESTION: '<spring:message code="title.question" />',
	
	BTN_APPLY:    '<spring:message code="button.apply" />',
	BTN_CANCEL: '<spring:message code="button.cancel" />',
	BTN_CLOSE: '<spring:message code="button.close" />',
	BTN_OK:    '<spring:message code="button.ok" />',
	BTN_SAVE:    '<spring:message code="button.save" />',
	BTN_TBL_FROM_DB: '<spring:message code="dbTables.button.retrieveFromDb" />',
	BTN_TBL_ADD_MANUALLY: '<spring:message code="dbTables.button.addManually" />',
	
	LBL_LABEL: '<spring:message code="form.label.name" />',
	LBL_NAME: '<spring:message code="form.name.name" />',
	
	OPT_NO:   '<spring:message code="option.no" />',
	OPT_YES:  '<spring:message code="option.yes" />',
	OPT_TRUE: '<spring:message code="option.true" />',
	OPT_FALSE:'<spring:message code="option.false" />'
	
};

var r3pJtableMsg = {
	defaultDateFormat: '<spring:message code="jtable.defaultDateFormat" />',

	serverCommunicationError: '<spring:message code="jtable.serverCommunicationError" />',
	loadingMessage: '<spring:message code="jtable.loadingMessage" />',
	noDataAvailable: '<spring:message code="jtable.noDataAvailable" />',
	areYouSure: '<spring:message code="jtable.areYouSure" />',
	addNewRecord: '<spring:message code="jtable.addNewRecord" />',
	editRecord: '<spring:message code="jtable.editRecord" />',
	deleteConfirmation: '<spring:message code="jtable.deleteConfirmation" />',
	save: '<spring:message code="jtable.save" />',
	saving: '<spring:message code="jtable.saving" />',
	cancel: '<spring:message code="jtable.cancel" />',
	deleteText: '<spring:message code="jtable.deleteText" />',
	deleting: '<spring:message code="jtable.deleting" />',
	error: '<spring:message code="jtable.error" />',
	close: '<spring:message code="jtable.close" />',	
	cannotLoadOptionsFor: '<spring:message code="jtable.cannotLoadOptionsFor" />',
	pagingInfo: '<spring:message code="jtable.pagingInfo" />',
	pageSizeChangeLabel: '<spring:message code="jtable.pageSizeChangeLabel" />',
	gotoPageLabel: '<spring:message code="jtable.gotoPageLabel" />',
	canNotDeletedRecords: '<spring:message code="jtable.canNotDeletedRecords" />',
	deleteProggress: '<spring:message code="jtable.deleteProggress" />'
};