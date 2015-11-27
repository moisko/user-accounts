"use strict";

$.editable.addInputType("account", {
	element : function(settings, original) {

		function getAccountProperty(id) {
			var tokens = id.split("_");
			var accountProperty = tokens[0];
			return accountProperty;
		};

		function createInputTypeControl(id, type) {
			var input;
			switch(type) {
				case "text":
					input = "<input id=\"" + id + "\" type=\"text\" min=\"1\" max=\"50\" size=\"15\" class=\"required\">";
				break;
				case "datetime-local":
					input = "<input id=\"" + id + "\" type=\"text\" size=\"12\" class=\"required\">";
				break;
				default:
					throw "Could not create control of type [" + type + "]";
				break;
			}
			return input;
		};

		function createControl(id) {
			var control;
			var accountProperty = getAccountProperty(id);
			switch(accountProperty) {
				case "firstName":
				case "lastName":
				case "email":
					control = createInputTypeControl(id, "text");
				break;
				case "dateOfBirth":
					control = createInputTypeControl(id, "datetime-local");
				break;
				default:
					throw "No account property defined for [" + accountProperty + "]";
				break;
			}
			return $(control);
		};

		var id = original.getAttribute("id");
		var control = createControl(id);
		$(this).append(control);
		return (control);
	},
	plugin : function(settings, original) {
		var id = original.getAttribute("id");
		if(id.indexOf("dateOfBirth", 0) === 0) {
			$("input", this).datetimepicker({
				format : "d/m/Y H:i",
				step : 10
			});
		}
	}
});