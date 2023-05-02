package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import com.taritari.master.entity.User;
import com.taritari.master.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author taritari
 * @date 2023-04-29 16:23
 * @description
 */
@WebServlet("/user/deleteUserById")
public class DeleteUserByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 获取id参数
        String id = request.getParameter("id");
        int userId = Convert.toInt(id);

        // 获取SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 开启事务
        session.beginTransaction();

        // 删除用户
        User user = session.get(User.class, userId);
        session.delete(user);
        session.flush();

        // 提交事务
        session.getTransaction().commit();

        // 返回成功响应
        response.getWriter().print("success");
    }
}
