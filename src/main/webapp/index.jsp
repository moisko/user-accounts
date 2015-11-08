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
		}

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
		}

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
					                      toLocalDateTimeString(account.dateOfBirth),
					                      constructDeleteLink(account.id) ]);
				},
				error : function(xhr, status) {
					alert("Failed to add account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
				}
			});
		}

		function getAccountIdFromTableRow(tableRow) {
			return tableRow.find("td a").attr("id");
		}

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
		}

		function constructDeleteLink(id) {
			return "<a href=accounts/" + id + " id=" + id + ">Delete account</a>";
		}

		function populateAccountsTable() {
			// Get accounts
			$.ajax({
				url : "/user-accounts/accounts/",
				type : "GET",
				success : function(accounts) {
					accounts.forEach(function(account) {
						dataTable.fnAddData([ account[1],
						                      account[2],
						                      account[3],
						                      toLocalDateTimeString(account[4]),
						                      constructDeleteLink(account[0]) ]);
					});
				},
				error : function(xhr, status) {
					alert("Failed to load user accounts.\nServer returned: " + xhr.statusText);
				}
			});
		}

		// Execution

		var dataTable = $("#accounts-table").dataTable({
			"order" : [[3, "desc"]],
			"fnHeaderCallback" : function(nHead, aData, iStart, iEnd, aiDisplay) {
				nHead.getElementsByTagName("th")[0].innerHTML = (iEnd - iStart) + " Measures";
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
	<div>
		<table id="accounts-table" class="display">
			<thead>
				<tr>
					<th colspan="6"/>
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
	</div>

	<div>
		<h2>Add measure</h2>
		<form id="add-account-form" action="">
			<label for="first-name">FIRST NAME: </label>
			<input id="first-name" name="first-name" type="text" min="1" max="50" size="10" class="required">

			<label for="last-name">LAST NAME: </label>
			<input id="last-name" name="last-name" type="text" min="1" max="50" size="10" class="required">

			<label for="email">EMAIL: </label>
			<input id="email" name="email" type="email" size="20" class="required">

			<label for="date-of-birth">DATE OF BIRTH: </label>
			<input id="date-of-birth" name="date-of-birth" type="text" size="12" class="required">

			<button type="submit">Add Account</button>
		</form>
	</div>
</body>
</html>
