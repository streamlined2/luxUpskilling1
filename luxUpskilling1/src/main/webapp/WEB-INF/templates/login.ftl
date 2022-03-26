<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login form</title>
</head>
<body>
	<form>
		<fieldset>
			<legend>Login</legend>
			<table>
				<tr>
					<td><label for="name">User</label></td>
					<td><input type="text" id="name" name="name"
						placeholder="Enter user name" size="20"
						pattern="(\w|\s){2,20}" title="User name" required /></td>
				</tr>
				<tr>
					<td><label for="password">Password</label></td>
					<td><input type="password" id="password" name="password"
						placeholder="Enter user password" size="20"
						pattern="(\w|\s){2,20}" title="User password" required /></td>
				</tr>
			</table>
			<hr>
			<input type="submit" value="Login" formaction="${rc.getContextPath()}/login" formmethod="post" />
			<input type="reset" value="Reset" />
			<input type="submit" value="Cancel" formaction="${rc.getContextPath()}/products" formmethod="get" formnovalidate />
			<input type="hidden" id="targetUrl" name="targetUrl" value="${targetUrl}" />
		</fieldset>
	</form>
</body>
</html>