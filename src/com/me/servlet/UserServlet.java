package com.me.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.me.bean.User;
import com.me.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class UserServlet
 */
@Slf4j
@WebServlet(name = "userServlet", value = "/userServlet")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.getUserByUserName(username);
        if (user == null) {
            request.setAttribute("message", "用户不存在！");
            request.getRequestDispatcher("/users.jsp").forward(request, response);
            return;
        }

        try {
            byte[] result = MessageDigest.getInstance("MD5").digest(password.getBytes(StandardCharsets.UTF_8));
            //用digest()方法获得byte[]数组表示的摘要，最后，把它转换为十六进制的字符串
            String passwordMD5= new BigInteger(1, result).toString(16);
            if (!passwordMD5.equals(user.getPassword())){
                request.setAttribute("message", "密码错误！");
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }

        user = userService.getUserWithTrades(username);

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
    }

}
