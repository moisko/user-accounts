$(document).ready(function() {

	// Utility functions

	function constructDeleteLink(id) {
		return "<a href=accounts/" + id + " id=" + id + ">Delete account</a>";
	};

	// Custom input type 

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
					console.log("No account property defined for [" + accountProperty + "]");
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

	// Custom sorting by dates

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

	// jQuery DataTable

	var dataTable = $("#accounts-table").dataTable({
		"aaSorting" : [[ 3, "asc" ]],
		"aoColumnDefs" : [
			{
				"aTargets" : [3],// DATE OF BIRTH column
				"mRender" : function(datetimeInMillis) {
								return datetime.toLocalDateTimeString(datetimeInMillis);
							},
				"sType" : "date-bg"
			},
			{
				"aTargets" : [4],// DELETE column
				"bSortable" : false,
				"mRender" : function(id) {
								return constructDeleteLink(id);
							}
			}
		],
		"fnHeaderCallback" : function(nHead, aData, iStart, iEnd, aiDisplay) {
			nHead.getElementsByTagName("th")[0].innerHTML = (iEnd - iStart) + " Accounts";
		},
		"fnRowCallback" : function(nRow, aData, iStart, iEnd, aiDisplay) {
			var id = $("td a", nRow).attr("id");
			var tdElements = $("td", nRow);
			var index = 0;
			for(index = 0; index < tdElements.length; index++) {
				var tdElement = tdElements[index];
				switch(index) {
					case 0:
					tdElement.setAttribute("id", "firstName_" + id);
					tdElement.setAttribute("class", "edit");
					break;
					case 1:
					tdElement.setAttribute("id", "lastName_" + id);
					tdElement.setAttribute("class", "edit");
					break;
					case 2:
					tdElement.setAttribute("id", "email_" + id);
					tdElement.setAttribute("class", "edit");
					break;
					case 3:
					tdElement.setAttribute("id", "dateOfBirth_" + id);
					tdElement.setAttribute("class", "edit");
					break;
					case 4:
					tdElement.setAttribute("class", "delete");
					break;
					default:
					break;
				}
			}
		},
		"fnDrawCallback" : function () {
			$("#accounts-table tbody tr td:not(.delete)").editable("/user-accounts/accounts/update", {
				"type" : "account",
				"onblur" : "submit",
				"callback" : function(updatedValue) {
					function convertUpdatedValueToColumnType(column) {
						if(column === 3) {// DELETE column
							return parse(updatedValue);
						}
						return updatedValue;
					};
					var position = dataTable.fnGetPosition(this);
					var row = position[0];
					var column = position[1];
					var aData = dataTable.fnGetData(row);
					aData[column] = convertUpdatedValueToColumnType(column);
					dataTable.fnUpdate(aData, row);
				},
				"onerror" : function(settings, original, xhr) {
					alert("Failed to update account.\nServer returned " + xhr.status + ": " + xhr.responseText);
					original.reset();
				}
			});
		}
	});

	accountsTable.populateAccountsTable(dataTable);

	dataTable.delegate("tbody tr td a", "click", function(event) {
		event.preventDefault();
		if(confirm("Are you sure you want to delete this account") === true) {
			var tableRow = $(this).parent().parent();
			accountsTable.deleteAccount(dataTable, tableRow);
		}
	});

	$("#date-of-birth").datetimepicker({
		format : "d/m/Y H:i"
	});

	$("#add-account-form").submit(function(event) {
		event.preventDefault();
		accountsTable.addAccount(dataTable);
	});
});