<html>
<head>
<meta charset="UTF-8">
<title>List of products</title>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}
th, td {
	padding: 5px;
}
</style>
</head>
<body>
	<table>
		<caption>
			<h3>Products by name</h3>
		</caption>
		<thead>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>Created</th>
			</tr>
		</thead>
		<tbody>
            <#list products as product>
				<tr>
					<td>${product.name()}</td>
					<td>${product.price()}</td>
					<td>${product.creationDate()}</td>
					<td><a href=${context}/products/edit/${product.id()}>Edit</a></td>
					<td><a href=${context}/products/delete/${product.id()}>Delete</a></td>
				</tr>
            </#list>
		</tbody>
	</table>
	<br>
	<a href=${context}/products/add>Create new</a>
</body>
</html>