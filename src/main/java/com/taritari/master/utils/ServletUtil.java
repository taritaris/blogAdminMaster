package com.taritari.master.utils;

import com.alibaba.fastjson.JSON;

import com.taritari.master.enums.ResultEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtil {
    public static void sendJson(HttpServletResponse response, ResultEnum resultEnum, Object data) throws IOException {
        Result result = Result.buildResult(resultEnum, data);
        sendJson(response, result);
    }
    public static void sendJson(HttpServletResponse response , Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = JSON.toJSONString(data);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    public static void sendJson(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = JSON.toJSONString(result);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}

