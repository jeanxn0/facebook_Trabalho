
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestão de Comentários</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">
</head>
<body>
<div class="container mt-5">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Gestão de Comentários</h2>
        <a href="${pageContext.request.contextPath}/comments/form"
           class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> Novo comentário
        </a>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Comentário</th>
                <th>Post </th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="comment" items="${comments_list}">
                <tr>
                    <td><c:out value="${comment.id}"/></td>
                    <td><c:out value="${comment.content}"/></td>
                    <td>
                        <c:out value="${comment.post.content}"/>
                    </td>
                    <td>
                        <a class="btn btn-sm btn-warning"
                           href="${pageContext.request.contextPath}/comments/edit?id=${comment.id}">
                            <i class="bi bi-pencil"></i> Editar
                        </a>

                        <form action="${pageContext.request.contextPath}/comments/delete"
                              method="post" class="d-inline"
                              onsubmit="return confirm('Deseja excluir este comentário?');">
                            <input type="hidden" name="id" value="${comment.id}">
                            <button type="submit" class="btn btn-sm btn-danger">
                                <i class="bi bi-trash"></i> Excluir
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty comments_list}">
                <tr>
                    <td colspan="4" class="text-center">
                        Nenhum comentário cadastrado.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
        Voltar ao início
    </a>
</div>
</body>
</html>
