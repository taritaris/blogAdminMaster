package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.taritari.master.utils.HibernateUtil;
import com.taritari.master.utils.ServletUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taritari
 * @date 2023-05-01 17:04
 * @description
 */
@WebServlet("/user/getChildComments")
public class GetChildCommentByNumberServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String nums = request.getParameter("number");
        // 获取SessionFactory 和 Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        StringBuffer str = new StringBuffer("SELECT * FROM blog_comment WHERE parentNumber = "+nums);
        // 执行分页查询
        String sql = str+"";
        Query sqlQuery = session.createSQLQuery(sql);
        List<Object[]> pageList = sqlQuery.getResultList();
        List<Map<String,Object>> listData = new ArrayList<>();
        // SQL查询结果
        for (Object[] objects : pageList) {
            Map<String,Object> data = new HashMap<>(8);

            Integer id = Convert.toInt(objects[0]);
            String number = objects[1].toString();
            String userNumbers = objects[3].toString();
            String comment = objects[6].toString();
            data.put("id",id);
            data.put("number",number);
            data.put("userNumbers",userNumbers);
            data.put("comment",comment);
            listData.add(data);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", listData);
        ServletUtil.sendJson(response, JSONObject.toJSON(result));
        session.close();
    }
}
