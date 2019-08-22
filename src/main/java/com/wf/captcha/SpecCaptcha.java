package com.wf.captcha;

import com.wf.captcha.base.Captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * png格式验证码
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class SpecCaptcha extends Captcha {

    public SpecCaptcha() {
    }

    public SpecCaptcha(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public SpecCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public SpecCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    /**
     * 生成验证码
     *
     * @param out 输出流
     * @return 是否成功
     */
    @Override
    public boolean out(OutputStream out) {
        return graphicsImage(textChar(), out);
    }

    /**
     * 生成验证码图形
     *
     * @param strs 验证码
     * @param out  输出流
     * @return boolean
     */
    private boolean graphicsImage(char[] strs, OutputStream out) {
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bi.getGraphics();
            // 填充背景
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            // 随机画干扰线
            // drawLine(3, g);
            // 随机画干扰圆
            // drawOval(8, g);
            drawBesselLine(2, g);
            // 画字符串
            // AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);// 指定透明度
            // g.setComposite(ac3);
            g.setFont(getFont());
            int fontSize = getFont().getSize();
            int fY = height - ((height - fontSize) >> 1);  // 文字的纵坐标
            int fW = width / strs.length;  // 每一个字符所占的宽度
            int fSp = (fW - fontSize) / 2;  // 字符的左右边距
            for (int i = 0; i < strs.length; i++) {
                g.setColor(color());
                // 计算坐标
                int x = i * fW + fSp;
                int y = fY/* - num(3, 6)*/;
                /*if (x < 8) {
                    x = 8;
                }
                if (x + fontSize > width) {
                    x = width - fontSize;
                }*/
                /*if (y > height) {
                    y = height;
                }
                if (y - fontSize < 0) {
                    y = fontSize;
                }*/
                g.drawString(String.valueOf(strs[i]), x, y);
            }
            ImageIO.write(bi, "png", out);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}