<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html lang="pt-br">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Login - Facebook CRUD</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<style>
.hidden {
	display: none;
}
</style>

</head>

<body>

	<div class="d-flex flex-column min-vh-100 bg-light">

		<!-- Navbar -->

		<nav class="navbar navbar-dark bg-dark">

			<div class="container">

				<span class="navbar-brand mb-0 h1"> Facebook CRUD </span>

			</div>

		</nav>

		<!-- Conteúdo -->

		<main
			class="flex-grow-1 d-flex justify-content-center align-items-center">

			<div class="card shadow-sm p-4"
				style="width: 100%; max-width: 400px;">

				<h2 class="text-center mb-4">Login</h2>

				<!-- Formulário de Login -->

				<form action="${pageContext.request.contextPath}/login"
					method="POST">

					<!-- E-mail -->

					<div class="mb-3">

						<label for="user_login_id" class="form-label"> Login
							(e-mail) </label> <input type="email" class="form-control"
							id="user_login_id" name="user_login" required>

					</div>

					<!-- Senha -->

					<div class="mb-3">

						<label for="user_pw_id" class="form-label"> Senha </label> <input
							type="password" class="form-control" id="user_pw_id"
							name="user_pw" required>

					</div>

					<!-- Botão -->

					<div class="d-grid mb-3">

						<button type="submit" class="btn btn-dark btn-lg">Logar</button>

					</div>

					<!-- Mensagem de erro -->

					<c:if test="${param.error == 'true'}">

						<div class="text-danger text-center small">Usuário ou senha
							inválidos.</div>

					</c:if>

				</form>

			</div>

		</main>

		<!-- Footer -->

		<footer class="bg-dark text-white text-center py-3 mt-auto">

			<div class="container">

				<jsp:useBean id="date" class="java.util.Date" />

				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />

				<p class="mb-0">

					©
					<c:out value="${currentYear}" />

					Facebook CRUD. Todos os direitos reservados.

				</p>

			</div>

		</footer>

	</div>

</body>

</html>