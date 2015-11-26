(function(exports) {

	(function(exports) {

		"use strict";

		var api = {
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
						"dateOfBirth" : app.parse($("#date-of-birth").val())
					}),
					success : function(account) {
						dataTable.fnAddData([ account.firstName,
						                      account.lastName,
						                      account.email,
						                      account.dateOfBirth,
						                      account.id ]);
						app.clearAddAccountForm();
					},
					error : function(xhr, status) {
						alert("Failed to add account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
					}
				});
			},
			deleteAccount: function deleteAccount(dataTable, tableRow) {
				var accountId = tableRow.find("td a").attr("id");
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
		};

		$.extend(exports, api);
	}((typeof exports === "undefined") ? window : exports));

}(app));