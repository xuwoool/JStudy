package com.jstudy.sys.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstudy.common.utils.Encrypt;

public class LoginServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	private static final String COOKIE_UID = "JSTUDYUID";
	private static final String COOKIE_PWD = "JSTUDYPWD";
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("GET方式登录");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("POST方式登录");
		String bashPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
		log.debug(bashPath);
		String realname = "1@1.com";
		String realpwd = "202cb962ac59075b964b07152d234b70";
		
		String username = null;//输入邮箱
		String password = null;//输入密码
		String remember = null;//记住我一分钟
		
		//1.cookie验证
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (COOKIE_UID.equals(cookie.getName())) {
					username = cookie.getValue();
					continue;
				}
				if (COOKIE_PWD.equals(cookie.getName())) {
					password = cookie.getValue();//MD5加密后字符串
					continue;
				}
			}
			if (username != null && password != null) {
				//cookies安全认证
				if (realname.equals(username) && realpwd.equals(password)) {
					log.debug("cookies安全登录");
					request.getSession().setAttribute("username", username);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
			}
		}
		
		//2.表单验证
		username = request.getParameter("username");
		password = request.getParameter("password");
		remember = request.getParameter("remember");
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			
			if (realname.equals(username) && realpwd.equals(Encrypt.md5(password))) {
				
				String path = "/JStudy/";
				int maxAge = 1 * 24 * 60 * 60; //默认24小时
				if (remember != null) {
					maxAge = 60;//设置cookie生命周期60秒
				}
				//设置cookies
				Cookie cookieuid = new Cookie(COOKIE_UID,null);
				cookieuid.setPath(path);
				cookieuid.setValue(username);
				cookieuid.setMaxAge(maxAge);
				response.addCookie(cookieuid);
				
				Cookie cookiepwd = new Cookie(COOKIE_PWD,null);
				cookiepwd.setPath(path);
				cookiepwd.setMaxAge(maxAge);
				cookiepwd.setValue(Encrypt.md5(password));
				response.addCookie(cookiepwd);
				
				log.debug("表单安全登录");
				request.getSession().setAttribute("username", username);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("msg", "邮箱地址或密码不正确");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
}
