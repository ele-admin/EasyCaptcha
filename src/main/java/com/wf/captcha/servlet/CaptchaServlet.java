package com.wf.captcha.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wf.captcha.utils.CaptchaUtil;

/**
 * 验证码servlet
 * 
 * @author wangfan
 * @date 2018-5-14 上午9:53:01
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = -90304944339413093L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 是否有key决定是存在session中还是servletContext中
		String key = request.getParameter("key");
		CaptchaUtil cu = new CaptchaUtil();
		if (key != null && !key.trim().isEmpty()) {
			cu.out(key, request, response);
		} else {
			cu.out(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
