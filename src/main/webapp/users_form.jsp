
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Gestão de Usuários</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-icons.css">

</head>

<body>

	<div class="container">

		<div class="row mt-3">

			<div class="col-md-2"></div>

			<div class="col-md-8">

				<h1>Cadastro de Usuário</h1>

				<form action="${pageContext.request.contextPath}/users/save"
					method="GET">

					<!-- ID do usuário -->
					<input type="hidden" id="user_id_id" name="user_id"
						value="${user.id}">


					<!-- Nome -->
					<div class="mb-3">

						<label for="user_name_id" class="form-label"> Nome </label> <input
							id="user_name_id" type="text" name="user_name"
							class="form-control" value="${user.name}" required>

					</div>


					<!-- Gênero -->
					<div class="mb-3">

						<label class="form-label"> Gênero </label>

						<div class="mb-1">

							<input id="user_gender_m_id" value="M" type="radio"
								name="user_gender" class="form-check-input"
								${user.gender == 'M' ? 'checked' : ''}> <label
								class="form-check-label" for="user_gender_m_id">
								Masculino </label>

						</div>


						<div class="mb-1">

							<input id="user_gender_f_id" value="F" type="radio"
								name="user_gender" class="form-check-input"
								${user.gender == 'F' ? 'checked' : ''}> <label
								class="form-check-label" for="user_gender_f_id">
								Feminino </label>

						</div>

					</div>


					<!-- Email -->
					<div class="mb-3">

						<label for="user_email_id" class="form-label"> Email </label> <input
							id="user_email_id" type="email" name="user_email"
							class="form-control" value="${user.email}" required>

					</div>


					<!-- Senha -->
					<div class="mb-3">

						<label for="user_pw_id" class="form-label"> Senha </label> <input
							id="user_pw_id" type="password" name="user_pw"
							class="form-control" required>

					</div>


					<!-- Botão -->
					<button type="submit" class="btn btn-primary">Cadastrar</button>

				</form>

			</div>

			<div class="col-md-2"></div>

		</div>

	</div>


	<!-- Bootstrap JS -->
	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>

</html>
