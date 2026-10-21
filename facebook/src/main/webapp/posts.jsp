```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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

    <div class="container mt-4">

        <div class="row">

            <h2 class="mb-3">Posts</h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/posts/form"
                   class="btn btn-success">
                    <i class="bi bi-plus-circle"></i> Novo Post
                </a>
            </div>

            <table class="table table-striped table-bordered">

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

                            <td>
                                <a href="${pageContext.request.contextPath}/posts/edit?id=${post.id}"
                                   class="bi bi-pencil-square"
                                   title="Editar">
                                </a>

                                &nbsp;

                                <form action="${pageContext.request.contextPath}/posts/delete"
                                      method="post"
                                      style="display: inline;">

                                    <input type="hidden"
                                           name="id"
                                           value="${post.id}">

                                    <button type="submit"
                                            class="btn btn-link p-0 bi bi-trash text-danger"
                                            title="Excluir"
                                            onclick="return confirm('Tem certeza que deseja excluir este post?');">
                                    </button>

                                </form>
                            </td>

                        </tr>
                    </c:forEach>

                </tbody>

            </table>

            <!-- Botão para voltar à página inicial -->
            <div class="mt-3 mb-4">

                <a href="${pageContext.request.contextPath}/"
                   class="btn btn-secondary">

                    <i class="bi bi-arrow-left"></i>
                    Voltar ao início

                </a>

            </div>

        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>
```