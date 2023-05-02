package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import com.taritari.master.entity.User;
import com.taritari.master.utils.HibernateUtil;
import com.taritari.master.utils.PageUtil;
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

/**
 * @author taritari
 * @date 2023-04-29 14:07
 * @description
 */
@WebServlet("/user/selectUserNameByUserName")
public class SelectUserNameByUserNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 获取请求分页参数
        int page = 1;
        int size = Integer.MAX_VALUE;
        String username = request.getParameter("username");
        // 获取SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        // 构建查询
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
        Root<User> from = query.from(User.class);
        query.select(from.get("username")).where(criteriaBuilder.like(from.get("username"), "%" + username + "%"));
        PageUtil.queryPage(response, session, query, page, size);
    }
}
