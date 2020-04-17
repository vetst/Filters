package com.users.servlet;

import com.users.exception.DBException;
import com.users.model.User;
import com.users.service.UserService;
import com.users.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void init() {
        try {
            userService.createTable();
            userService.addUser("admin", "admin", "admin", "admin");
            userService.addUser("user", "user", "user", "user");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = new User();
        try {
            user = userService.validateUser(name, password);
        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (user.getRole().equals("admin")) {
            session.setAttribute("admin", name);
            resp.sendRedirect(req.getContextPath() + "/admin/panel");
        } else if (user.getRole().equals("user")) {
            session.setAttribute("user", name);
            session.setAttribute("admin", null);
            resp.sendRedirect(req.getContextPath() + "/user/");
        }
    }
}
