package com.javapro.Questionnaire;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    static final Map<String, String> users = new HashMap<String, String>();
    static {
        users.put("user1", "qwerty");
        users.put("user2", "qazwsx");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String curUser = users.get(login);
        if (password.equals(curUser)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userLogin", login);
            session.setAttribute("userResult1", new int[ResultServlet.getAnswers1().length]);
            session.setAttribute("userResult2", new int[ResultServlet.getAnswers2().length]);
        }

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null))
            session.removeAttribute("userLogin");

        response.sendRedirect("index.jsp");
    }
}
