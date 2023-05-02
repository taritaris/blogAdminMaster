
package com.taritari.master.utils;

import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taritari
 * @date 2023-04-29 13:10 
 * @description
 */
public class PageUtil {
    public static <T> void queryPage(HttpServletResponse response, Session session, CriteriaQuery<T> query,
                              int page, int size) throws IOException {
        // 计算总记录数
        Query<T> countQuery = session.createQuery(query);
        long totalCount = countQuery.getResultList().size();

        // 计算总页数
        int totalPage = (int) (totalCount + size - 1) / size;

        // 分页查询
        Query<T> query1 = session.createQuery(query);
        query1.setFirstResult((page - 1) * size);
        query1.setMaxResults(size);
        List<T> list = query1.getResultList();
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("currentPage", page);
        result.put("totalPage", totalPage);
        result.put("totalCount",totalCount);
        // 返回结果
        ServletUtil.sendJson(response, result);

    }
}