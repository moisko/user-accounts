"use-strict";

var app = app || {};

(function(exports) {

	(function(exports) {

		var api = {
			clearAddAccountForm: function clearAddAccountForm() {
				$("#first-name").val("");
				$("#last-name").val("");
				$("#email").val("");
				$("#date-of-birth").val("");
			}
		};

		$.extend(exports, api);
	}((typeof exports === "undefined") ? window : exports));

}(app));