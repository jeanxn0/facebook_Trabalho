
package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Comment;
import model.ModelException;
import model.Post;
import model.dao.CommentDAO;
import model.dao.DAOFactory;
import model.dao.PostDAO;

@WebServlet(urlPatterns = {
    "/comments",
    "/comments/form",
    "/comments/edit",
    "/comments/delete"
})
public class CommentsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            if ("/comments".equals(action)) {

                loadComments(req);

                RequestDispatcher rd =
                    req.getRequestDispatcher("/comments.jsp");

                rd.forward(req, resp);

            } else if ("/comments/form".equals(action)) {

                loadPosts(req);

                RequestDispatcher rd =
                    req.getRequestDispatcher("/comment_form.jsp");

                rd.forward(req, resp);

            } else if ("/comments/edit".equals(action)) {

                int id = Integer.parseInt(req.getParameter("id"));

                CommentDAO commentDAO =
                    DAOFactory.createDAO(CommentDAO.class);

                Comment comment = commentDAO.findById(id);

                if (comment == null) {
                    resp.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Comentário não encontrado."
                    );
                    return;
                }

                req.setAttribute("comment", comment);
                loadPosts(req);

                RequestDispatcher rd =
                    req.getRequestDispatcher("/comment_form.jsp");

                rd.forward(req, resp);

            } else {

                resp.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Página não encontrada."
                );
            }

        } catch (NumberFormatException e) {

            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID inválido."
            );

        } catch (ModelException e) {

            throw new ServletException(
                "Erro ao consultar os comentários ou posts.",
                e
            );
        }
    }

    private void loadComments(HttpServletRequest req)
            throws ModelException {

        CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);

        List<Comment> comments = dao.listAll();

        req.setAttribute("comments_list", comments);
    }

    private void loadPosts(HttpServletRequest req)
            throws ModelException {

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        List<Post> posts = dao.listAll();

        req.setAttribute("posts", posts);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getServletPath();

        try {
            CommentDAO commentDAO =
                DAOFactory.createDAO(CommentDAO.class);

            if ("/comments/delete".equals(action)) {

                int id = Integer.parseInt(req.getParameter("id"));

                Comment comment = commentDAO.findById(id);

                if (comment == null) {
                    resp.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Comentário não encontrado."
                    );
                    return;
                }

                commentDAO.delete(comment);

                resp.sendRedirect(
                    req.getContextPath() + "/comments"
                );

                return;
            }

            if ("/comments".equals(action)) {

                String idParam = req.getParameter("id");
                String content = req.getParameter("content");
                String postIdParam = req.getParameter("post_id");

                if (content == null || content.trim().isEmpty()) {
                    resp.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O conteúdo do comentário é obrigatório."
                    );
                    return;
                }

                int postId = Integer.parseInt(postIdParam);

                PostDAO postDAO =
                    DAOFactory.createDAO(PostDAO.class);

                Post post = postDAO.findById(postId);

                if (post == null) {
                    resp.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Post não encontrado."
                    );
                    return;
                }

                Comment comment = new Comment();
                comment.setContent(content.trim());
                comment.setPost(post);

                if (idParam != null && !idParam.trim().isEmpty()) {

                    int id = Integer.parseInt(idParam);

                    comment.setId(id);

                    commentDAO.update(comment);

                } else {

                    commentDAO.save(comment);
                }

                resp.sendRedirect(
                    req.getContextPath() + "/comments"
                );

            } else {

                resp.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Ação não encontrada."
                );
            }

        } catch (NumberFormatException e) {

            resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID inválido."
            );

        } catch (ModelException e) {

            throw new ServletException(
                "Erro ao salvar ou excluir o comentário.",
                e
            );
        }
    }
}
