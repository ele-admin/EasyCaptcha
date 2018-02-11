package com.wf.captcha.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class CaptchaUtil {

	/**
	 * 验证验证码
	 * 
	 * @param verKey
	 * @param verCode
	 * @param request
	 * @return
	 */
	public static boolean isVerified(String verKey, String verCode,
			HttpServletRequest request) {
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String cacheVerCode = (String) servletContext.getAttribute("code_"
				+ verKey);
		return verCode.equals(cacheVerCode);
	}
}
