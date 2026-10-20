
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Usuários - Facebook CRUD</title>

    <!-- Bootstrap CSS -->

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap-icons.css">

</head>

<body>

    <div class="container">

        <!-- Botão Home -->

        <div class="row mt-3">

            <div class="col-md-2"></div>

            <div class="col-md-8">

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/">

                    Home

                </a>

            </div>

            <div class="col-md-2"></div>

        </div>


        <!-- Lista de usuários -->

        <div class="row mt-3">

            <div class="col-md-2"></div>

            <div class="col-md-8">

                <h2>Usuários</h2>

                <table class="table table-striped">

                    <thead>

                        <tr>

                            <th scope="col">Id</th>

                            <th scope="col">Nome</th>

                            <th scope="col">Sexo</th>

                            <th scope="col">Email</th>

                            <th scope="col">Ações</th>

                        </tr>

                    </thead>

                    <tbody>

                        <c:forEach var="usuario" items="${users_list}">

                            <tr>

                                <th scope="row">
                                    ${usuario.id}
                                </th>

                                <td>
                                    ${usuario.name}
                                </td>

                                <td>
                                    ${usuario.gender}
                                </td>

                                <td>
                                    ${usuario.email}
                                </td>

                                <td>

                                    <!-- Editar -->

                                    <a href="${pageContext.request.contextPath}/users/update?userId=${usuario.id}"
                                       class="bi bi-pencil-square"
                                       title="Editar">
                                    </a>

                                    &nbsp;

                                    

                                    <a href="${pageContext.request.contextPath}/users/delete?userId=${usuario.id}"
                                       class="bi bi-trash"
                                       title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir este usuário?');">
                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                    </tbody>

                </table>


                <!-- Cadastrar -->

                <a href="${pageContext.request.contextPath}/users_form.jsp"
                   class="btn btn-primary">

                    Cadastrar Usuário

                </a>

            </div>

            <div class="col-md-2"></div>

        </div>

    </div>


    <!-- Bootstrap JS -->

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>

</html>
