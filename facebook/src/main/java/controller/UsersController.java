
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
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {
    "/users",
    "/users/save",
    "/users/update",
    "/users/delete"
})
public class UsersController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {

                case "/users": {
                    loadUsers(req);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("/users.jsp");

                    rd.forward(req, resp);
                    break;
                }

                case "/users/update": {
                    loadUser(req);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("/users_form.jsp");

                    rd.forward(req, resp);
                    break;
                }

                case "/users/save":
                case "/users/delete": {
                    resp.sendError(
                            HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                            "Utilize POST para realizar esta operação."
                    );
                    break;
                }

                default:
                    resp.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Página não encontrada."
                    );
            }

        } catch (ModelException e) {
            throw new ServletException(
                    "Erro ao consultar usuários.",
                    e
            );
        } catch (NumberFormatException e) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de usuário inválido."
            );
        } catch (IllegalArgumentException e) {
            resp.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getServletPath();

        try {
            switch (action) {

                case "/users/save": {
                    saveOrUpdateUser(req);

                    resp.sendRedirect(
                            req.getContextPath() + "/users"
                    );
                    break;
                }

                case "/users/delete": {
                    deleteUser(req);

                    resp.sendRedirect(
                            req.getContextPath() + "/users"
                    );
                    break;
                }

                default:
                    resp.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Ação não encontrada."
                    );
            }

        } catch (ModelException e) {
            throw new ServletException(
                    "Erro ao salvar, atualizar ou excluir usuário.",
                    e
            );
        } catch (NumberFormatException e) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de usuário inválido."
            );
        } catch (IllegalArgumentException e) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    private void loadUsers(HttpServletRequest req)
            throws ModelException {

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);

        List<User> users = dao.listAll();

        req.setAttribute("users_list", users);
    }

    private void loadUser(HttpServletRequest req)
            throws ModelException {

        String userIdStr = req.getParameter("userId");

        if (userIdStr == null || userIdStr.isBlank()) {
            throw new NumberFormatException();
        }

        int userId = Integer.parseInt(userIdStr);

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);

        User user = dao.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado."
            );
        }

        req.setAttribute("user", user);
    }

    private void saveOrUpdateUser(HttpServletRequest req)
            throws ModelException {

        String userIdStr = req.getParameter("user_id");

        String userName = req.getParameter("user_name");
        String userGender = req.getParameter("user_gender");
        String userEmail = req.getParameter("user_email");
        String userPW = req.getParameter("user_pw");

        if (userName == null || userName.isBlank()
                || userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome e e-mail são obrigatórios."
            );
        }

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);

        boolean isUpdate =
                userIdStr != null && !userIdStr.isBlank();

        User user;

        if (isUpdate) {
            user = dao.findById(Integer.parseInt(userIdStr));

            if (user == null) {
                throw new IllegalArgumentException(
                        "Usuário não encontrado."
                );
            }
        } else {
            user = new User();
        }

        user.setName(userName);
        user.setGender(userGender);
        user.setEmail(userEmail);

        if (userPW != null && !userPW.isBlank()) {
            String hashPW = PasswordEncryptor.hashPassword(userPW);
            user.setPassword(hashPW);
        }

        if (isUpdate) {
            dao.update(user);
        } else {
            if (userPW == null || userPW.isBlank()) {
                throw new IllegalArgumentException(
                        "A senha é obrigatória para novos usuários."
                );
            }

            dao.save(user);
        }
    }

    private void deleteUser(HttpServletRequest req)
            throws ModelException {

        String userIdStr = req.getParameter("userId");

        if (userIdStr == null || userIdStr.isBlank()) {
            throw new NumberFormatException();
        }

        int userId = Integer.parseInt(userIdStr);

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);

        User user = dao.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado."
            );
        }

        dao.delete(user);
    }
}
