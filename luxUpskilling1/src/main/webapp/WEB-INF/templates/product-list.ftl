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
	<#if error??>
		<div class="error">
			<p>${error}</p>
		</div>
	</#if>
	<#if userRole?length!=0>
		<a href=${rc.getContextPath()}/logout>Logout</a>
	<#else>
		<a href=${rc.getContextPath()}/loginform>Login</a>
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
						<td><a href=${rc.getContextPath()}/product/edit/${product.id()}>Edit</a></td>
						<td><a href=${rc.getContextPath()}/product/delete/${product.id()}>Delete</a></td>
					</#if>
					<#if userRole=="USER">
						<td><a href=${rc.getContextPath()}/product/cart/add/${product.id()}>Order</a></td>
						<td><a href=${rc.getContextPath()}/product/cart/delete/${product.id()}>Decline</a></td>
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
		<a href=${rc.getContextPath()}/product/add>Create new</a>
	</#if>
</body>
</html>