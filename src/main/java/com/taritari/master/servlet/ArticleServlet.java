package com.taritari.master.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
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
import java.util.*;

/**
 * @author taritari
 * @date 2023-04-30 14:58
 * @description
 */
@WebServlet("/article/articles")
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取分页参数
        String pageStr = request.getParameter("page");
        String search = request.getParameter("search");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int page = Integer.parseInt(pageStr);
        int size = 10;

        // 获取SessionFactory 和 Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // 执行count查询获得总记录数
        String countSql = "SELECT COUNT(*) FROM blog_article,blog_articleviews WHERE numbers=articleNumber";
        Query countQuery = session.createSQLQuery(countSql);
        BigInteger totalCount = (BigInteger) countQuery.uniqueResult();

        // 计算总页数
        int totalPage = (int) (totalCount.longValue() + size - 1) / size;
        // 或执行SQL查询
//        String sql = "SELECT blog_article.id,author,title,createTime,imgSrc,numbers,numberOfViews,tagId " +
//                "FROM blog_article,blog_articleviews " +
//                "WHERE numbers=articleNumber";

        StringBuffer str = new StringBuffer("SELECT blog_article.id,author,title,createTime,imgSrc,numbers,numberOfViews,tagId FROM blog_article,blog_articleviews WHERE numbers=articleNumber");
        if (ObjectUtil.isNotEmpty(search)){
            str.append(" AND title LIKE '%").append(search).append("%' ");
        }
        if (ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime)) {
            str.append(" AND createTime BETWEEN  ").append("'"+startTime+"'").append(" AND ").append("'"+endTime+"'");
        }
        if (ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isEmpty(endTime)) {
            str.append(" AND createTime >= '").append(startTime).append("'");
        }
        if (ObjectUtil.isEmpty(startTime) && ObjectUtil.isNotEmpty(endTime)) {
            str.append(" AND createTime <= '").append(endTime).append("'");
        }
        // 执行分页查询
        String sql = str+"";

        Query sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setFirstResult((page - 1) * size);
        sqlQuery.setMaxResults(size);
        List<Object[]> pageList = sqlQuery.getResultList();

        //获取标签
        String tag = "SELECT * FROM blog_tag";
        Query tagSql = session.createSQLQuery(tag);
        List<Object[]> tagSqlList = tagSql.getResultList();

        List<Map<String,Object>> listData = new ArrayList<>();
        // SQL查询结果
        for (Object[] objects : pageList) {
            Map<String,Object> data = new HashMap<>(8);
            Integer tagId = Convert.toInt(objects[7]);
            String tagName = null;
            for (Object[] tagObject : tagSqlList){
                if (tagId==Convert.toInt(tagObject[2])){
                    tagName = tagObject[1].toString();
                }
            }

            Integer id = Convert.toInt(objects[0]);
            String title = objects[2].toString();
            String author = objects[1].toString();
            Date createTime = (Date) objects[3];
            String numbers = objects[5].toString();
            Integer numberOfViews = Convert.toInt(objects[6]);
            //获取文章评论
            String ViewSql = "SELECT numberOfViews as views  FROM blog_articleviews WHERE articleNumber = "+numbers;
            Query ViewsSql = session.createSQLQuery(ViewSql);
            List<Object[]> resultList = ViewsSql.getResultList();
            Object views = resultList.get(0);

            data.put("id",id);
            data.put("title",title);
            data.put("author",author);
            data.put("createTime", DateUtil.date(createTime).toString());
            data.put("numbers",numbers);
            data.put("numberOfViews",numberOfViews);
            data.put("tagName",tagName);
            data.put("views",views);
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
