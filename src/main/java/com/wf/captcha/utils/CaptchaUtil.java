package com.wf.captcha.utils;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wf.captcha.Captcha;
import com.wf.captcha.GifCaptcha;

/**
 * 图形验证码工具类
 * 
 * @author wangfan
 * @date 2018-5-14 上午9:41:06
 */
public class CaptchaUtil {
	private String codeName = "captcha";
	private int width = 130;
	private int height = 38;
	private int len = 5;

	public CaptchaUtil() {
	}

	/**
	 * 验证码的宽、高、位数
	 */
	public CaptchaUtil(int width, int height, int len) {
		set(width, height, len);
	}

	/**
	 * 验证验证码
	 */
	public boolean ver(String code, HttpServletRequest request) {
		String captcha = (String) request.getSession().getAttribute(codeName);
		return code.equals(captcha);
	}

	/**
	 * 验证验证码，用于分离的项目
	 */
	public boolean ver(String key, String code, HttpServletRequest rq) {
		ServletContext sc = rq.getServletContext();
		String keyName = codeName + "-" + key;
		String captcha = (String) sc.getAttribute(keyName);
		return code.equals(captcha);
	}

	/**
	 * 输出验证码
	 */
	public void out(HttpServletRequest rq, HttpServletResponse rp)
			throws IOException {

		setHeader(rp);

		// 验证码的宽、高、位数
		Captcha captcha = new GifCaptcha(width, height, len);
		// 存入缓存
		rq.getSession().setAttribute(codeName, captcha.text().toLowerCase());

		// 输入图片
		captcha.out(rp.getOutputStream());
	}

	/**
	 * 输出验证码，用于分离项目
	 */
	public void out(String key, HttpServletRequest rq, HttpServletResponse rp)
			throws IOException {

		setHeader(rp);

		// 验证码的宽、高、位数
		Captcha captcha = new GifCaptcha(width, height, len);
		// 存入缓存
		ServletContext sc = rq.getServletContext();
		sc.setAttribute(codeName, captcha.text().toLowerCase());

		// 输入图片
		captcha.out(rp.getOutputStream());
	}

	private void setHeader(HttpServletResponse rp) {
		rp.setContentType("image/gif");
		rp.setHeader("Pragma", "No-cache");
		rp.setHeader("Cache-Control", "no-cache");
		rp.setDateHeader("Expires", 0);
	}

	public String getCodeName() {
		return codeName;
	}

	/**
	 * 设置保存code的key
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * 设置验证码的宽度
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * 设置验证码的高度
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public int getLen() {
		return len;
	}

	/**
	 * 设置验证码的位数
	 */
	public void setLen(int len) {
		this.len = len;
	}

	/**
	 * 设置验证码的宽、高、位数
	 */
	public void set(int width, int height, int len) {
		setWidth(width);
		setHeight(height);
		setLen(len);
	}
}
