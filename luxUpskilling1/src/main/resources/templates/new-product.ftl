<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create product</title>
</head>
<body>
	<form>
		<fieldset>
			<legend>Create new product</legend>
			<table>
				<tr>
					<td><label for="name">Name</label></td>
					<td><input type="text" id="name" name="name"
						placeholder="Enter product name" size="60"
						pattern="(\w|\s){2,100}" title="Name of product" required /></td>
				</tr>
				<tr>
					<td><label for="price">Price</label></td>
					<td><input type="number" id="price" name="price"
						placeholder="Enter product price" min="0"
						size="15" title="Price of product" required /></td>
				</tr>
				<tr>
					<td><label for="creationDate">Produced</label></td>
					<td><input type="date" id="creationDate" name="creationDate"
						placeholder="Enter date of production" title="Date of production"
						required /></td>
				</tr>
			</table>
			<hr>
			<input type="submit" value="Create" formaction="${context}/saveproduct" formmethod="post" />
			<input type="reset" value="Reset" />
			<input type="submit" value="Cancel" formaction="${context}/products" formmethod="post" formnovalidate />
		</fieldset>
	</form>
</body>
</html>