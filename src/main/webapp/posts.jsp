
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Posts - Facebook CRUD</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
</head>
<body>

	<div class="container">
		<div class="row">
			<h2>Posts</h2>

			<a href="${pageContext.request.contextPath}/posts/form"
				class="btn btn-success"> Novo Post </a>

			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">Id</th>
						<th scope="col">Conteúdo</th>
						<th scope="col">Data</th>
						<th scope="col">Usuário</th>
						<th scope="col">Ações</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="post" items="${posts_list}">
						<tr>
							<th scope="row">${post.id}</th>
							<td>${post.content}</td>
							<td>${post.postDate}</td>
							<td>${post.user.name}</td>

							<td><a
								href="${pageContext.request.contextPath}/posts/edit?id=${post.id}"
								class="bi bi-pencil-square" title="Editar"></a> &nbsp;

								<form action="${pageContext.request.contextPath}/posts/delete"
									method="post" style="display: inline;">

									<input type="hidden" name="id" value="${post.id}">

									<button type="submit" class="btn btn-link p-0 bi bi-trash"
										title="Excluir"
										onclick="return confirm('Tem certeza que deseja excluir este post?');">
									</button>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>
