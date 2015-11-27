"use strict";

$.fn.dataTableExt.oSort["date-bg-asc"] = function(dateTimeStringA, dateTimeStringB) {
	var dateA = datetime.toDate(dateTimeStringA);
	var dateB = datetime.toDate(dateTimeStringB);
	if(dateA > dateB) return 1;
	if(dateA < dateB) return -1;
	return 0;
};

$.fn.dataTableExt.oSort["date-bg-desc"] = function(dateTimeStringA, dateTimeStringB) {
	var dateA = datetime.toDate(dateTimeStringA);
	var dateB = datetime.toDate(dateTimeStringB);
	if(dateA > dateB) return -1;
	if(dateA < dateB) return 1;
	return 0;
};