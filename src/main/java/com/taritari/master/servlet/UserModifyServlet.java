package com.taritari.master.servlet;

import cn.hutool.core.date.DateUtil;
import com.taritari.master.entity.User;
import com.taritari.master.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * @author taritari
 * @date 2023-04-29 20:54
 * @description
 */
@WebServlet("/user/modify")
public class UserModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 获取请求参数
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String birthday =request.getParameter("birthday");
        String id =request.getParameter("id");
        // 获取SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 获取用户
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(criteriaBuilder.equal(root.get("id"), id)); // 假定修改id为1的用户
        User user = session.createQuery(query).getSingleResult();
        session.beginTransaction();
        // 执行更新
        user.setUsername(username);
        user.setEmail(email);
        user.setBirthday(DateUtil.parse(birthday));
        session.update(user);

        // 提交事务
        session.getTransaction().commit();
        session.close();
        response.getWriter().print("success");
    }
}
