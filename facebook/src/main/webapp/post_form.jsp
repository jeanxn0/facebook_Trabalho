<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>



<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Cadastro de Post</title>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-icons.css">

</head>


<body>

	<div class="container mt-4">


		<h1>

			<c:choose>

				<c:when test="${not empty post}">
                    Editar Post
                </c:when>

				<c:otherwise>
                    Cadastrar Post
                </c:otherwise>

			</c:choose>

		</h1>


		<form action="${pageContext.request.contextPath}/posts" method="post">


			<c:if test="${not empty post}">

				<input type="hidden" name="id" value="${post.id}">

			</c:if>


			<div class="mb-4">

				<label for="conteudo_post" class="form-label"> Conteúdo do
					post </label>


				<textarea name="conteudo_post" id="conteudo_post"
					class="form-control" rows="6" required>${post.content}</textarea>

			</div>


			<div class="mb-4">

				<label for="user_id" class="form-label"> Usuário </label> <select
					name="user_id" id="user_id" class="form-control" required>


					<c:forEach var="usuario" items="${usuarios}">

						<option value="${usuario.id}"
							<c:if test="${not empty post and post.user.id == usuario.id}">
                                selected
                            </c:if>>

							<c:out value="${usuario.name}" />

						</option>

					</c:forEach>


				</select>

			</div>


			<button type="submit" class="btn btn-primary">
				<i class="bi bi-check-circle"></i> Salvar
			</button>


			<a href="${pageContext.request.contextPath}/posts"
				class="btn btn-secondary"> <i class="bi bi-x-circle"></i>
				Cancelar

			</a>


		</form>


	</div>


	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>

</html>