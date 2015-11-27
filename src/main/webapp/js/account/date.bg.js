"use strict";

$.fn.dataTableExt.oSort["date-bg-asc"] = function(dateTimeStringA, dateTimeStringB) {
	var dateA = LocalDateTime.toDate(dateTimeStringA);
	var dateB = LocalDateTime.toDate(dateTimeStringB);
	if(dateA > dateB) return 1;
	if(dateA < dateB) return -1;
	return 0;
};

$.fn.dataTableExt.oSort["date-bg-desc"] = function(dateTimeStringA, dateTimeStringB) {
	var dateA = LocalDateTime.toDate(dateTimeStringA);
	var dateB = LocalDateTime.toDate(dateTimeStringB);
	if(dateA > dateB) return -1;
	if(dateA < dateB) return 1;
	return 0;
};