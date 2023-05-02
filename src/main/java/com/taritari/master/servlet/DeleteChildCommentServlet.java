package com.taritari.master.servlet;

import com.taritari.master.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author taritari
 * @date 2023-05-01 20:48
 * @description
 */
@WebServlet("/comment/deleteChildComment")
public class DeleteChildCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        // 获取id参数
        String id =request.getParameter("id");

        // 获取SessionFactory和Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 开启事务
        session.beginTransaction();

        // 使用SQL语句删除数据
        String deleteSql = "DELETE FROM blog_comment WHERE id="+id;
        Query query = session.createSQLQuery(deleteSql);

        query.executeUpdate();

        // 提交事务
        session.getTransaction().commit();

        // 返回成功响应

    }
}
