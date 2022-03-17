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
	<a href=${context}/loginform>Login</a>
	<table>
		<caption>
			<h3>Products by name</h3>
		</caption>
		<thead>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>Produced</th>
			</tr>
		</thead>
		<tbody>
            <#list products as product>
				<tr>
					<td>${product.name()}</td>
					<td>${product.price()}</td>
					<td>${product.creationDate()}</td>
					<td><a href=${context}/product/edit/${product.id()}>Edit</a></td>
					<td><a href=${context}/product/delete/${product.id()}>Delete</a></td>
				</tr>
            </#list>
		</tbody>
	</table>
	<br>
	<a href=${context}/product/add>Create new</a>
</body>
</html>