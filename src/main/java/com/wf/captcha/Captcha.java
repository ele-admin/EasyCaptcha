package com.wf.captcha;

import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;

/**
 * 验证码抽象类
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public abstract class Captcha extends Randoms {
    protected Font font = new Font("Verdana", Font.PLAIN, 32); // 字体
    protected int len = 5; // 验证码随机字符长度
    protected int width = 130; // 验证码显示宽度
    protected int height = 48; // 验证码显示高度
    private String chars = null; // 当前验证码
    protected int charType = TYPE_DEFAULT;  // 验证码类型，1字母数字混合，2纯数字，3纯字母
    public static final int TYPE_DEFAULT = 1;  // 字母数字混合
    public static final int TYPE_ONLY_NUMBER = 2;  // 纯数字
    public static final int TYPE_ONLY_CHAR = 3;  // 纯字母

    /**
     * 生成随机验证码
     *
     * @return 验证码字符数组
     */
    protected char[] alphas() {
        "".toLowerCase();
        "".toUpperCase();
        char[] cs = new char[len];
        for (int i = 0; i < len; i++) {
            switch (charType) {
                case 2:
                    cs[i] = alpha(numMaxIndex);
                    break;
                case 3:
                    cs[i] = alpha(charMinIndex, charMaxIndex);
                    break;
                default:
                    cs[i] = alpha();
            }
        }
        chars = new String(cs);
        return cs;
    }

    /**
     * 给定范围获得随机颜色
     *
     * @param fc 0-255
     * @param bc 0-255
     * @return 随机颜色
     */
    protected Color color(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码输出,抽象方法，由子类实现
     *
     * @param os 输出流
     * @return 是否成功
     */
    public abstract boolean out(OutputStream os);

    /**
     * 获取当前的验证码
     *
     * @return 字符串
     */
    public String text() {
        checkAlpha();
        return chars;
    }

    /**
     * 获取当前验证码的字符数组
     *
     * @return 字符数组
     */
    public char[] textChar() {
        checkAlpha();
        return chars.toCharArray();
    }

    /**
     * 检查验证码是否生成，没有这立即生成
     */
    public void checkAlpha() {
        if (chars == null) {
            alphas(); // 生成验证码
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCharType() {
        return charType;
    }

    public void setCharType(int charType) {
        this.charType = charType;
    }
}