package Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet( urlPatterns = "/Register" , name = "Register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confpass = req.getParameter("confirmPassword");
        out.println("le username est ="+username);
        out.println("le password est ="+password);
        out.println("le confpass est ="+confpass);
        Cookie usernameCookie = new Cookie("username", username);
        resp.addCookie(usernameCookie);
        Cookie passwordCookie = new Cookie("password", password);
        resp.addCookie(passwordCookie);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
