<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Accounts App</title>

<link rel="stylesheet" type="text/css" href="css/vendor/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="css/vendor/jquery.datetimepicker.css">

<script src="js/vendor/jquery-1.11.3.min.js"></script>
<script src="js/vendor/datatables.min.js"></script>
<script src="js/vendor/jquery.datetimepicker.js"></script>
<script src="js/vendor/jquery.jeditable.mini.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		// Functions

		function parse(dateTimeString) {

			function decreaseMonth(month) {
				var m = parseInt(month, 10);
				return --m;
			}

			var splittedLocalDateTime = dateTimeString.split(" "),
				localDate = splittedLocalDateTime[0],
				splittedDate = localDate.split("/"),
				date = splittedDate[0],
				month = splittedDate[1],
				year = splittedDate[2],
				time = splittedLocalDateTime[1],
				splittedTime = time.split(":"),
				hh = splittedTime[0],
				mm = splittedTime[1],
				dateTimeInMillis = new Date(year, decreaseMonth(month), date, hh, mm);

			return dateTimeInMillis.getTime();
		};

		function toDate(dateTimeString) {
			function decreaseMonth(month) {
				var m = parseInt(month, 10);
				return --m;
			}

			var splittedLocalDateTime = dateTimeString.split(" "),
				localDate = splittedLocalDateTime[0],
				splittedDate = localDate.split("/"),
				date = splittedDate[0],
				month = splittedDate[1],
				year = splittedDate[2],
				time = splittedLocalDateTime[1],
				splittedTime = time.split(":"),
				hh = splittedTime[0],
				mm = splittedTime[1],
				date = new Date(year, decreaseMonth(month), date, hh, mm);

			return date;
		};

		function toLocalDateTimeString(dateTimeInMillis) {

			function formatMinute(minute) {
				if (minute >= 0 && minute <= 9) {
					minute = "0" + minute;
				}
				return minute;
			}

			function formatHour(hour) {
				if (hour >= 0 && hour <= 9) {
					hour = "0" + hour;
				}
				return hour;
			}

			function formatDate(date) {
				if (date >= 1 && date <= 9) {
					date = "0" + date;
				}
				return date;
			}

			function formatMonth(month) {
				if (month >= 1 && month <= 9) {
					month = "0" + month;
				}
				return month;
			}

			function increaseMonth(month) {
				var m = parseInt(month, 10);
				return ++m;
			}

			var d = new Date(dateTimeInMillis),
				date = d.getDate(),
				month = d.getMonth(),
				fullYear = d.getFullYear(),
				hh = d.getHours(),
				mm = d.getMinutes(),
				localDateTimeString = formatDate(date)+ "/" + formatMonth(increaseMonth(month)) + "/" + fullYear + " " + formatHour(hh) + ":" + formatMinute(mm);

			return localDateTimeString;
		};

		function addAccount() {
			$.ajax({
				url : "/user-accounts/accounts",
				type : "POST",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify({
					"firstName" : $("#first-name").val(),
					"lastName" : $("#last-name").val(),
					"email" : $("#email").val(),
					"dateOfBirth" : parse($("#date-of-birth").val())
				}),
				success : function(account) {
					dataTable.fnAddData([ account.firstName,
					                      account.lastName,
					                      account.email,
					                      account.dateOfBirth,
					                      account.id ]);
					// Clear form
					clearAddAccountForm();
				},
				error : function(xhr, status) {
					alert("Failed to add account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
				}
			});
		};

		function clearAddAccountForm() {
			$("#first-name").val("");
			$("#last-name").val("");
			$("#email").val("");
			$("#date-of-birth").val("");
		};

		function getAccountIdFromTableRow(tableRow) {
			return tableRow.find("td a").attr("id");
		};

		function deleteAccount(tableRow) {
			var accountId = getAccountIdFromTableRow(tableRow);
			$.ajax({
				type : "DELETE",
				url : "/user-accounts/accounts/" + accountId,
				success : function(data) {
					dataTable.fnDeleteRow(tableRow);
				},
				error : function(xhr, status) {
					alert("Failed to delete account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
				}
			});
		};

		function constructDeleteLink(id) {
			return "<a href=accounts/" + id + " id=" + id + ">Delete account</a>";
		};

		function populateAccountsTable() {
			// Get accounts
			$.ajax({
				url : "/user-accounts/accounts/",
				type : "GET",
				success : function(accounts) {
					dataTable.fnAddData(accounts);
				},
				error : function(xhr, status) {
					alert("Failed to load user accounts.\nServer returned: " + xhr.statusText);
				}
			});
		};

		$.editable.addInputType("account", {
			element : function(settings, original) {

				function getAccountProperty(id) {
					var tokens = id.split("_");
					var accountProperty = tokens[0];
					return accountProperty;
				}

				function createInputTypeControl(id, type) {
					var input;
					switch(type) {
						case "text":
							input = "<input id=\"" + id + "\" type=\"text\" min=\"1\" max=\"50\" size=\"10\" class=\"required\">";
							break;
						case "datetime-local":
							input = "<input id=\"" + id + "\" type=\"text\" size=\"10\" class=\"required\">";
							break;
					}
					return input;
				}

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
				}

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

		$.fn.dataTableExt.oSort["date-bg-asc"] = function(dateTimeStringA, dateTimeStringB) {
				var dateA = toDate(dateTimeStringA);
				var dateB = toDate(dateTimeStringB);
				if(dateA > dateB) return 1;
				if(dateA < dateB) return -1;
				return 0;
		};

		$.fn.dataTableExt.oSort["date-bg-desc"] = function(dateTimeStringA, dateTimeStringB) {
				var dateA = toDate(dateTimeStringA);
				var dateB = toDate(dateTimeStringB);
				if(dateA > dateB) return -1;
				if(dateA < dateB) return 1;
				return 0;
		};

		// Execution

		var dataTable = $("#accounts-table").dataTable({
			"aoColumnDefs" : [
				{"aTargets" : [3],// DATE OF BIRTH column
					"mRender" : function(datetimeInMillis) {
						return toLocalDateTimeString(datetimeInMillis);
					},
					"sType" : "date-bg"
				},
				{"aTargets" : [4],// DELETE column
					"bSortable" : false,
					"mRender" : function(id) {
						return constructDeleteLink(id);
					}
				}
			],
			"fnHeaderCallback" : function(nHead, aData, iStart, iEnd, aiDisplay) {
				nHead.getElementsByTagName("th")[0].innerHTML = (iEnd - iStart) + " Measures";
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
					"onblur" : "submit"
				});
			}
		});

		dataTable.delegate("tbody tr td a", "click", function(event) {
			if(confirm("Are you sure you want to delete this account") == true) {
				var tableRow = $(this).parent().parent();
				deleteAccount(tableRow);
			}
			event.preventDefault();
		});

		populateAccountsTable();

		$("#date-of-birth").datetimepicker({
			format : "d/m/Y H:i"
		});

		$("#add-account-form").submit(function(event) {
			addAccount();
			event.preventDefault();
		});

	});
</script>
</head>
<body>
	<table id="accounts-table" class="display">
		<thead>
			<tr>
				<th colspan="6" />
			</tr>
			<tr>
				<th>FIRST NAME</th>
				<th>LAST NAME</th>
				<th>EMAIL</th>
				<th>DATE OF BIRTH</th>
				<th>DELETE</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>

	<h2>Add measure</h2>
	<form id="add-account-form" action="">
		<fieldset>
			<label for="first-name">FIRST NAME: </label>
			<input id="first-name" name="first-name" type="text" min="1" max="50" size="10" class="required">

			<label for="last-name">LAST NAME: </label>
			<input id="last-name" name="last-name" type="text" min="1" max="50" size="10" class="required">

			<label for="email">EMAIL: </label>
			<input id="email" name="email" type="email" class="required">

			<label for="date-of-birth">DATE OF BIRTH: </label>
			<input id="date-of-birth" name="date-of-birth" type="text" size="10" class="required">

			<button type="submit">Add Account</button>
		</fieldset>
	</form>
</body>
</html>
