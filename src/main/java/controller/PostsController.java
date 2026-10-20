
package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = { "/posts", "/posts/form", "/posts/delete", "/posts/edit" })
public class PostsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		if ("/posts".equals(action)) {

			loadPosts(req);

			RequestDispatcher rd = req.getRequestDispatcher("/posts.jsp");

			rd.forward(req, resp);

		} else if ("/posts/form".equals(action)) {

			UserDAO dao = DAOFactory.createDAO(UserDAO.class);

			List<User> usuarios = List.of();

			try {
				usuarios = dao.listAll();
			} catch (ModelException e) {
				e.printStackTrace();
			}

			req.setAttribute("usuarios", usuarios);

			RequestDispatcher rd = req.getRequestDispatcher("/post_form.jsp");

			rd.forward(req, resp);

		}

		else if ("/posts/edit".equals(action)) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));

				PostDAO dao = DAOFactory.createDAO(PostDAO.class);
				Post post = dao.findById(id);

				if (post == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Post não encontrado.");
					return;
				}

				UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
				req.setAttribute("post", post);
				req.setAttribute("usuarios", userDAO.listAll());

				RequestDispatcher rd = req.getRequestDispatcher("/post_form.jsp");
				rd.forward(req, resp);

			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
			} catch (ModelException e) {
				throw new ServletException("Erro ao carregar o post.", e);
			}
		} else {

			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "URL não suportada: " + action);
		}
	}

	private void loadPosts(HttpServletRequest req) {

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		List<Post> posts = List.of();

		try {
			posts = dao.listAll();
		} catch (ModelException me) {
			me.printStackTrace();
		}

		req.setAttribute("posts_list", posts);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		if ("/posts/delete".equals(action)) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));

				PostDAO dao = DAOFactory.createDAO(PostDAO.class);
				Post post = dao.findById(id);

				if (post == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Post não encontrado.");
					return;
				}

				dao.delete(post);
				resp.sendRedirect(req.getContextPath() + "/posts");
				return;

			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
				return;
			} catch (ModelException e) {
				throw new ServletException("Erro ao excluir o post.", e);
			}
		}

		String conteudo = req.getParameter("conteudo_post");
		String userIdParam = req.getParameter("user_id");

		try {
			int userId = Integer.parseInt(userIdParam);

			UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
			User usuario = userDAO.findById(userId);

			if (usuario == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuário não encontrado.");
				return;
			}

			Post post = new Post();
			post.setContent(conteudo);
			post.setUser(usuario);

			PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);
			postDAO.save(post);

			resp.sendRedirect(req.getContextPath() + "/posts");

		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuário inválido.");
		} catch (ModelException e) {
			throw new ServletException("Erro ao salvar o post.", e);
		}
	}

}
