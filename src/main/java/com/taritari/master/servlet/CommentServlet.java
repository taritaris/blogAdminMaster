package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
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
import java.util.*;

/**
 * @author taritari
 * @date 2023-05-01 16:22
 * @description
 */
@WebServlet("/article/comments")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 获取分页参数
        String pageStr = request.getParameter("page");
        String keyWord = request.getParameter("search");

        int page = Integer.parseInt(pageStr);
        int size = 10;

        // 获取SessionFactory 和 Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 执行count查询获得总记录数
        String countSql = "SELECT count(*) FROM blog_comment WHERE parentUser is null";
        Query countQuery = session.createSQLQuery(countSql);
        BigInteger totalCount = (BigInteger) countQuery.uniqueResult();

        // 计算总页数
        int totalPage = (int) (totalCount.longValue() + size - 1) / size;

        // 构建带搜索的SQL

        StringBuffer str = new StringBuffer("SELECT bc.id,bc.number,ba.title,bc.`comment`,ba.author,ba.createTime,articleNumbers,bc.parentUser FROM blog_comment bc LEFT JOIN blog_article ba ON bc.articleNumbers=ba.numbers WHERE 1=1 AND bc.parentUser is null ");
        if (keyWord != null && !"".equals(keyWord)) {
            str.append(" AND (ba.title LIKE '%").append(keyWord).append("%' ");
            str.append("OR ba.author LIKE '%").append(keyWord).append("%' ");
            str.append("OR bc.comment LIKE '%").append(keyWord).append("%')");
        }
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


            Integer id = Convert.toInt(objects[0]);
            String number = objects[1].toString();
            String title = objects[2].toString();
            String comment =  objects[3].toString();
            String author = objects[4].toString();
            String createTime = objects[5].toString();
            String articleNumbers = objects[6].toString();

            data.put("id",id);
            data.put("number",number);
            data.put("title",title);
            data.put("createTime", createTime);
            data.put("comment",comment);
            data.put("author",author);
            data.put("articleNumbers",articleNumbers);
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
