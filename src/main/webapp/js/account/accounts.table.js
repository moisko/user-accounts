var accounts = accounts || {};

(function(exports) {

	(function(exports) {
		"use strict";

		var accountsTableApi = {
			addAccount: function addAccount(dataTable) {
				$.ajax({
					url: "/user-accounts/accounts",
					type: "POST",
					dataType: "json",
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({
						"firstName": $("#first-name").val(),
						"lastName": $("#last-name").val(),
						"email": $("#email").val(),
						"dateOfBirth": datetime.parse($("#date-of-birth").val())
					}),
					success: function(account) {
						dataTable.fnAddData([account.firstName,
							account.lastName,
							account.email,
							account.dateOfBirth,
							account.id
						]);
						form.clearAddAccountForm();
					},
					error: function(xhr, status) {
						alert("Failed to add account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
					}
				});
			},
			deleteAccount: function deleteAccount(dataTable, id) {
				$.ajax({
					type: "DELETE",
					url: "/user-accounts/accounts/" + id,
					success: function(data) {
						var id = data.id;
						var tr = $("#" + id);
						dataTable.fnDeleteRow(tr);
					},
					error: function(xhr, status) {
						alert("Failed to delete account.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
					}
				});
			},
			init: function init(dataTable) {
				$.ajax({
					url: "/user-accounts/accounts/",
					type: "GET",
					beforeSend: function() {
						$.blockUI({
							message: "<h1>Loading ...</h1>"
						});
					},
					success: function(accounts) {
						if (accounts.length > 0) {
							dataTable.fnAddData(accounts);
						}
					},
					error: function(xhr, status) {
						alert("Failed to load user accounts.\nServer returned: " + xhr.statusText + "-" + xhr.responseText);
					}
				}).done(function() {
					$.unblockUI();
				});
			}
		};

		$.extend(exports, accountsTableApi);
	}((typeof exports === "undefined") ? window : exports));

}(accounts));