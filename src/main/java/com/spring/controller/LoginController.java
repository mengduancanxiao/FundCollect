package com.spring.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "/checkuser")
	@ResponseBody
	public String checkuser(String username, String password, HttpServletRequest req) {
		username = req.getParameter("username");
		System.out.println("username = " + username + " password = " + password + " ------------------ ");
		return "login demo";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST, headers="Content-Type=text/plain")
	@ResponseBody
	public String index(String username, String password) {
	    System.out.println("username === " + username);
	    return "index3";
	}
	
	public String jdbclink(String username, String password) {
		Connection conn = null;
        String sql;
		String url = "jdbc:mysql://localhost:3306/jeecg?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "select * from t_s_base_user where username = '" + username + "' and password = '" + password + "'";
            ResultSet result = stmt.executeQuery(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
		} catch (Exception e) {
			e.printStackTrace();
		}// 动态加载mysql驱动
		return "";
	}
}
