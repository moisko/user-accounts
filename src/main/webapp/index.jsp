<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>User Accounts App</title>

<link rel="stylesheet" type="text/css" href="css/vendor/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/vendor/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/vendor/jquery.datetimepicker.css">
<link rel="stylesheet" type="text/css" href="css/index.css">

<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

</head>
<body>
<div class="container">
	<div class="row">
		<table id="accounts-table" class="table table-striped table-bordered">
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
	</div>

	<h2>Add account</h2>
	<form id="add-account-form" action="" class="form-inline">
		<div class="form-group">
			<label for="first-name" class="sr-only">FIRST NAME</label>
			<input id="first-name" name="first-name" type="text" min="1" max="50" placeholder="First Name" class="form-control required">
		</div>
		<div class="form-group">
			<label for="last-name" class="sr-only">LAST NAME</label>
			<input id="last-name" name="last-name" type="text" min="1" max="50" placeholder="Last Name" class="form-control required">
		</div>
		<div class="form-group">
			<label for="email" class="sr-only">EMAIL</label>
			<input id="email" name="email" type="email" placeholder="Email" class="form-control required">
		</div>
		<div class="form-group">
			<label for="date-of-birth" class="sr-only">DATE OF BIRTH</label>
			<input id="date-of-birth" name="date-of-birth" type="text" placeholder="Date Of Birth" class="form-control required">
		</div>
		<button class="btn btn-primary" type="submit">Add Account</button>
	</form>
</div>

<script src="js/vendor/jquery-1.11.3.min.js"></script>
<script src="js/vendor/datatables.min.js"></script>
<script src="js/vendor/jquery.datetimepicker.js"></script>
<script src="js/vendor/jquery.jeditable.mini.js"></script>
<script src="js/vendor/jquery.blockUI.js"></script>
<script src="js/vendor/bootstrap.min.js"></script>
<script src="js/account/account.js"></script>

</body>
</html>
