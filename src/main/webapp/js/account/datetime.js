"use strict";

var datetime = datetime || {};

(function(exports) {

	(function(exports) {

		var api = {
			parse : function(dateTimeString) {

				function decreaseMonth(month) {
					var m = parseInt(month, 10);
					return --m;
				};

				var splittedLocalDateTime = dateTimeString.split(" ");
				var localDate = splittedLocalDateTime[0];
				var splittedDate = localDate.split("/");
				var date = splittedDate[0];
				var month = splittedDate[1];
				var year = splittedDate[2];
				var time = splittedLocalDateTime[1];
				var splittedTime = time.split(":");
				var hh = splittedTime[0];
				var mm = splittedTime[1];
				var dateTimeInMillis = new Date(year, decreaseMonth(month), date, hh, mm);

				return dateTimeInMillis.getTime();
			},
			toDate : function(dateTimeString) {
				var dateTimeInMillis = this.parse(dateTimeString);
				var date = new Date(dateTimeInMillis);
				return date;
			},
			toLocalDateTime : function(dateTimeInMillis) {

				function formatMinute(minute) {
					if (minute >= 0 && minute <= 9) {
						minute = "0" + minute;
					}
					return minute;
				};

				function formatHour(hour) {
					if (hour >= 0 && hour <= 9) {
						hour = "0" + hour;
					}
					return hour;
				};

				function formatDate(date) {
					if (date >= 1 && date <= 9) {
						date = "0" + date;
					}
					return date;
				};

				function formatMonth(month) {
					if (month >= 1 && month <= 9) {
						month = "0" + month;
					}
					return month;
				};

				function increaseMonth(month) {
					var m = parseInt(month, 10);
					return ++m;
				};

				var d = new Date(dateTimeInMillis);
				var date = d.getDate();
				var month = d.getMonth();
				var fullYear = d.getFullYear();
				var hh = d.getHours();
				var mm = d.getMinutes();
				var localDateTimeString = formatDate(date)+ "/" + formatMonth(increaseMonth(month)) + "/" + fullYear + " " + formatHour(hh) + ":" + formatMinute(mm);

				return localDateTimeString;
			}
		};

		$.extend(exports, api);
	}((typeof exports === "undefined") ? window : exports));

}(datetime));