package com.taritari.master.servlet;

import cn.hutool.core.util.RandomUtil;
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
 * @date 2023-05-01 13:55
 * @description
 */
@WebServlet("/tag/addTag")
public class AddTagServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        // 获取参数
        String tagName = request.getParameter("tagName");

        // 获取SessionFactory和Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 开启事务
        session.beginTransaction();
        StringBuffer str = new StringBuffer("INSERT INTO blog_tag(tagName,tagId) VALUES (");
        str.append("'").append(tagName).append("'").append(",").append(RandomUtil.randomInt(0,9999)).append(")");
        // 执行添加类别SQL语句
        String insertSql = str+"";
        Query query = session.createSQLQuery(insertSql);
        query.executeUpdate();

        // 提交事务
        session.getTransaction().commit();

        // 返回成功响应
        response.getWriter().print("success");
    }
}
