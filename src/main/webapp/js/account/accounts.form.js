"use-strict";

var AddAccountForm = AddAccountForm || {};

(function(exports) {

	(function(exports) {

		var api = {
			clear: function clear() {
				$("#first-name").val("");
				$("#last-name").val("");
				$("#email").val("");
				$("#date-of-birth").val("");
			}
		};

		$.extend(exports, api);
	}((typeof exports === "undefined") ? window : exports));

}(AddAccountForm));