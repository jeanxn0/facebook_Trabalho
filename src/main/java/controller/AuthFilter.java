package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse resp,
            FilterChain filterchain)
            throws IOException, ServletException {

        HttpServletRequest httReq =
                (HttpServletRequest) req;

        HttpServletResponse httResp =
                (HttpServletResponse) resp;

        HttpSession session =
                httReq.getSession(false);

        boolean userIsLogged =
                session != null
                && session.getAttribute("usuer_logged") != null;

        boolean isPublicResource =
                httReq.getRequestURI().endsWith(".css")
                || httReq.getRequestURI().endsWith(".js")
                || httReq.getRequestURI().contains("images");

        boolean isLogin =
                httReq.getRequestURI().contains("login");

        if (userIsLogged
                || isLogin
                || isPublicResource) {

            filterchain.doFilter(
                    httReq,
                    httResp);

        } else {

            httResp.sendRedirect(
                    "/facebook/login.jsp?error=true");
        }
    }
}