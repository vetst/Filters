package com.users.servlet;

import com.users.exception.DBException;
import com.users.service.UserService;
import com.users.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/edit")
public class EditServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String name = req.getParameter("name");
        String surName = req.getParameter("surName");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        req.setAttribute("userId", userId);
        req.setAttribute("name", name);
        req.setAttribute("surName", surName);
        req.setAttribute("password", password);
        req.setAttribute("role", role);
        getServletContext().getRequestDispatcher("/edit-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String userId = req.getParameter("userId");
        String name = req.getParameter("name");
        String surName = req.getParameter("surName");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        try {
            Long id = Long.parseLong(userId);
            userService.updateUser(id, name, surName, password, role);
            resp.sendRedirect(req.getContextPath() + "/admin/panel");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
