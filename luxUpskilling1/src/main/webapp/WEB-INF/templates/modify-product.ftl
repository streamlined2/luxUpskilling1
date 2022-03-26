<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify product</title>
</head>
<body>
	<form>
		<fieldset>
			<legend>Modify product</legend>
			<table>
				<tr><input type="hidden" id="id" name="id" value="${product.id()}" /></tr>
				<tr>
					<td><label for="name">Name</label></td>
					<td><input type="text" id="name" name="name"
						value="${product.name()}" placeholder="Enter product name" size="60"
						pattern="(\w|\s){2,60}" title="Name of product" required /></td>
				</tr>
				<tr>
					<td><label for="price">Price</label></td>
					<td><input type="number" id="price" name="price"
						value="${product.price()}" placeholder="Enter product price" min="0"
						size="15" title="Price of product" required /></td>
				</tr>
				<tr>
					<td><label for="creationDate">Produced</label></td>
					<td><input type="date" id="creationDate" name="creationDate"
						value="${product.creationDate()}"
						placeholder="Enter date of production" title="Date of production"
						required /></td>
				</tr>
			</table>
			<hr>
			<input type="submit" value="Save" formaction="${rc.getContextPath()}/saveproduct" formmethod="post" />
			<input type="reset" value="Reset" />
			<input type="submit" value="Cancel" formaction="${rc.getContextPath()}/products" formmethod="get" formnovalidate />
		</fieldset>
	</form>
</body>
</html>