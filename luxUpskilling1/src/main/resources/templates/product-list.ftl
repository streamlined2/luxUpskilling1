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
	<#if userRole?length!=0>
		<a href=${context}/logout>Logout</a>
	<#else>
		<a href=${context}/loginform>Login</a>
	</#if>
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
					<#if userRole=="ADMIN">
						<td><a href=${context}/product/edit/${product.id()}>Edit</a></td>
						<td><a href=${context}/product/delete/${product.id()}>Delete</a></td>
					</#if>
					<#if userRole=="USER">
						<td><a href=${context}/product/cart/add/${product.id()}>Order</a></td>
						<td><a href=${context}/product/cart/delete/${product.id()}>Decline</a></td>
					</#if>
				</tr>
            </#list>
		</tbody>
	</table>
	<br>
	<#if userRole=="USER">
		${cart!"Void order"}
	</#if>
	<#if userRole=="ADMIN">
		<a href=${context}/product/add>Create new</a>
	</#if>
</body>
</html>