package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect("/facebook/login.jsp");
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String userEmail = req.getParameter("user_login");
        String userPW = req.getParameter("user_pw");

        if (userEmail == null || userPW == null
                || userEmail.isBlank() || userPW.isBlank()) {

            resp.sendRedirect(
                    "/facebook/login.jsp?error=true");

            return;
        }

        UserDAO userDAO =
                DAOFactory.createDAO(UserDAO.class);

        User userToLogin = null;

        try {

            userToLogin =
                    userDAO.findByEmail(userEmail);

        } catch (ModelException e) {

            e.printStackTrace();

            resp.sendRedirect(
                    "/facebook/login.jsp?error=true");

            return;
        }

        if (userToLogin == null) {

            resp.sendRedirect(
                    "/facebook/login.jsp?error=true");

            return;
        }

        if (!userToLogin.getPassword().equals(userPW)) {

            resp.sendRedirect(
                    "/facebook/login.jsp?error=true");

            return;
        }

        HttpSession session =
                req.getSession();

        session.setAttribute(
                "usuer_logged",
                userToLogin);

        resp.sendRedirect("/facebook/");
    }
}