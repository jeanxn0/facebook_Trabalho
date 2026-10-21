
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cadastro de Comentário</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<div class="container mt-5">

    <h2>
        <c:choose>
            <c:when test="${not empty comment}">
                Editar comentário
            </c:when>
            <c:otherwise>
                Novo comentário
            </c:otherwise>
        </c:choose>
    </h2>

    <form action="${pageContext.request.contextPath}/comments"
          method="post">

        <c:if test="${not empty comment}">
            <input type="hidden" name="id" value="${comment.id}">
        </c:if>

        <div class="mb-3">
            <label for="content" class="form-label">Comentário</label>
            <textarea id="content" name="content"
                      class="form-control" rows="4"
                      required><c:out value="${comment.content}"/></textarea>
        </div>

        <div class="mb-3">
            <label for="post_id" class="form-label">Post relacionado</label>
            <select id="post_id" name="post_id"
                    class="form-select" required>
                <option value="">Selecione um post</option>

                <c:forEach var="post" items="${posts}">
                    <option value="${post.id}"
                        <c:if test="${not empty comment and comment.post.id == post.id}">
                            selected
                        </c:if>>
                        Post #${post.id} -
                        <c:out value="${post.content}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Salvar</button>

        <a href="${pageContext.request.contextPath}/comments"
           class="btn btn-secondary">Cancelar</a>
    </form>

</div>
</body>
</html>
