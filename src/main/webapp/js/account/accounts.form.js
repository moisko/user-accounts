var form = form || {};

(function(exports) {

	(function(exports) {
		"use-strict";

		var formApi = {
			clearAddAccountForm: function clearAddAccountForm() {
				$("#first-name").val("");
				$("#last-name").val("");
				$("#email").val("");
				$("#date-of-birth").val("");
			}
		};

		$.extend(exports, formApi);
	}((typeof exports === "undefined") ? window : exports));

}(form));