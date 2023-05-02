package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taritari
 * @date 2023-05-01 10:17
 * @description
 */
@WebServlet("/tag/tags")
public class TagServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取分页参数
        String pageStr = request.getParameter("page");
        String tagId = request.getParameter("tagId");
        int page = Integer.parseInt(pageStr);
        int size = 10;

        // 获取SessionFactory 和 Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 执行count查询获得总记录数
        String countSql = "SELECT COUNT(*) FROM blog_tag";
        Query countQuery = session.createSQLQuery(countSql);
        BigInteger totalCount = (BigInteger) countQuery.uniqueResult();

        // 计算总页数
        int totalPage = (int) (totalCount.longValue() + size - 1) / size;
        StringBuffer str = new StringBuffer("SELECT tagName,COUNT(blog_article.title) as count FROM blog_tag  LEFT JOIN blog_article ON blog_tag.tagId=blog_article.tagId ");
        if (ObjectUtil.isNotEmpty(tagId)){
            str.append("WHERE blog_tag.tagId="+tagId);
        }
        str.append(" GROUP BY tagName");
        // 执行分页查询
        String sql = str+"";
        Query sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setFirstResult((page - 1) * size);
        sqlQuery.setMaxResults(size);
        List<Object[]> pageList = sqlQuery.getResultList();

        List<Map<String,Object>> listData = new ArrayList<>();
        // SQL查询结果
        for (Object[] objects : pageList) {
            Map<String,Object> data = new HashMap<>(8);
            String tagName = objects[0].toString();
            int count = Convert.toInt(objects[1]);
            data.put("tagName",tagName);
            data.put("count",count);
            listData.add(data);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", listData);
        result.put("currentPage", page);
        result.put("totalPage", totalPage);
        result.put("totalCount", totalCount);
        ServletUtil.sendJson(response, JSONObject.toJSON(result));

        session.close();
    }

}
