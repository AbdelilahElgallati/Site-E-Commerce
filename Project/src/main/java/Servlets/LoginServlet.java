package Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet( urlPatterns = "/Login" , name = "Login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    String storedUsername = cookie.getValue();
                    if (storedUsername.equals(username)) {
                        HttpSession session = req.getSession(true);
                        session.setAttribute("nom", username);
                        resp.sendRedirect(req.getContextPath() + "/Home");
                    }
                } else if (cookie.getName().equals("password")) {
                    String storedPassword = cookie.getValue();
                    if (storedPassword.equals(password)) {
                        out.println("Mot de passe correct");

                    }
                }
            }
        }
    }
}