var accountsTable = (function() {
	return {
		addAccount: function addAccount(dataTable) {
			$.ajax({
				url : "/user-accounts/accounts",
				type : "POST",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify({
					"firstName" : $("#first-name").val(),
					"lastName" : $("#last-name").val(),
					"email" : $("#email").val(),
					"dateOfBirth" : datetime.parse($("#date-of-birth").val())
				}),
				success : $.proxy(function(account) {
					dataTable.fnAddData([ account.firstName,
					                      account.lastName,
					                      account.email,
					                      account.dateOfBirth,
					                      account.id ]);
					this.clearAddAccountForm();
				}, this),
				error : function(xhr, status) {
					alert("Failed to add account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
				}
			});
		},
		clearAddAccountForm: function clearAddAccountForm() {
			$("#first-name").val("");
			$("#last-name").val("");
			$("#email").val("");
			$("#date-of-birth").val("");
		},
		getAccountIdFromTableRow: function getAccountIdFromTableRow(tableRow) {
			return tableRow.find("td a").attr("id");
		},
		deleteAccount: function deleteAccount(dataTable, tableRow) {
			var accountId = this.getAccountIdFromTableRow(tableRow);
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
		},
		populateAccountsTable: function populateAccountsTable(dataTable) {
			$.ajax({
				url : "/user-accounts/accounts/",
				type : "GET",
				beforeSend : function() {
					$.blockUI({
						message : "<h1>Loading ...</h1>"
					});
				},
				success : function(accounts) {
					if(accounts.length > 0) {
						dataTable.fnAddData(accounts);
					}
				},
				error : function(xhr, status) {
					alert("Failed to load user accounts.\nServer returned: " + xhr.statusText);
				}
			}).done(function() {
				$.unblockUI();
			});
		}
	}
}());