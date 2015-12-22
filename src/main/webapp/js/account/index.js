$(document).ready(function() {
	"use strict";

	// jQuery DataTables

	var dataTable = $("#accounts-table").dataTable({
		"aaSorting": [
			[3, "asc"]
		],
		"aoColumnDefs": [{
			"aTargets": [3], // DATE OF BIRTH column
			"mRender": function(datetimeInMillis) {
				return datetime.toLocalDateTime(datetimeInMillis);
			},
			"sType": "date-bg"
		}, {
			"aTargets": [4], // DELETE column
			"bSortable": false,
			"mRender": function(id) {
				return "<a href=accounts/" + id + " id=" + id + ">Delete account</a>";
			}
		}],
		"fnHeaderCallback": function(nHead, aData, iStart, iEnd, aiDisplay) {
			var accounts = (iEnd - iStart);
			var header;
			if (accounts === 1) {
				header = accounts + " Account";
			} else if (accounts > 1) {
				header = accounts + " Accounts";
			} else {
				header = "User Accounts Table Empty";
			}
			nHead.getElementsByTagName("th")[0].innerHTML = header;
		},
		"fnRowCallback": function(nRow, aData, iStart, iEnd, aiDisplay) {
			var id = $("td a", nRow).attr("id");
			nRow.setAttribute("id", id);
			var tdElements = $("td", nRow);
			for (var index = 0; index < tdElements.length; index++) {
				var tdElement = tdElements[index];
				switch (index) {
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
						throw new Error("Invalid column index [" + index + "]");
				}
			}
		},
		"fnDrawCallback": function() {
			$("#accounts-table tbody tr td:not(.delete)").editable("/user-accounts/accounts/update", {
				"type": "account",
				"onblur": "submit",
				"callback": function(updatedValue) {
					function convertUpdatedValueToColumnType(column) {
						if (column === 3) { // DELETE column
							return datetime.parse(updatedValue);
						}
						return updatedValue;
					}

					var position = dataTable.fnGetPosition(this);
					var row = position[0];
					var column = position[1];
					var aData = dataTable.fnGetData(row);
					aData[column] = convertUpdatedValueToColumnType(column);
					dataTable.fnUpdate(aData, row);
				},
				"onerror": function(settings, original, xhr) {
					alert("Failed to update account.\nServer returned " + xhr.status + ": " + xhr.responseText);
					original.reset();
				}
			});
		}
	});

	accounts.init(dataTable);

	dataTable.delegate("tbody tr td a", "click", function(event) {
		event.preventDefault();
		if (confirm("Are you sure you want to delete this account?") === true) {
			var tr = $(this).parent().parent();
			var id = tr.attr("id");
			accounts.deleteAccount(dataTable, id);
		}
	});

	$("#date-of-birth").datetimepicker({
		format: "d/m/Y H:i"
	});

	$("#add-account-form").submit(function(event) {
		event.preventDefault();
		accounts.addAccount(dataTable);
	});
});