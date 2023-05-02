package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
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
 * @date 2023-04-19 12:19
 * @description
 */
@WebServlet("/user/users")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        // 获取请求分页参数
        int page = Convert.toInt(request.getParameter("page"));
        int size = 10;
        String username = request.getParameter("username");
        // 获取SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 构建查询
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        if (ObjectUtil.isNotEmpty(username)){
            query.select(from).where(criteriaBuilder.like(from.get("username"), "%" + username + "%"));
        }else {
            query.select(from);
        }
        PageUtil.queryPage(response, session, query, page, size);


    }
}
