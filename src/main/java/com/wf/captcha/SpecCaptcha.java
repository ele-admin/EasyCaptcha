package com.wf.captcha;

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
        setFont(new Font(font.getFontName(), Font.ITALIC, font.getSize()));
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
        checkAlpha();
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
        boolean ok;
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bi.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 抗锯齿
            g.setFont(font);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 随机画干扰线
            for (int i = 0; i < 12; i++) {
                g.setColor(color(150, 250));
                g.setStroke(new BasicStroke(1.1f + RANDOM.nextFloat() / 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                int x1 = num(-10, width - 10);
                int y1 = num(5, height - 5);
                int x2 = num(10, width + 10);
                int y2 = num(2, height - 2);
                g.drawLine(x1, y1, x2, y2);
                // 画干扰圆圈
                g.setColor(color(100, 250));
                g.drawOval(num(width), num(height), 5 + num(25), 5 + num(25));
            }
            // 画字符串
            AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);// 指定透明度
            g.setComposite(ac3);
            int hp = (height - font.getSize()) >> 1;
            int h = height - hp;
            int w = width / strs.length;
            //int sp = (w - font.getSize()) / 2;
            for (int i = 0; i < strs.length; i++) {
                g.setColor(new Color(20 + num(110), 20 + num(110), 20 + num(110)));
                // 计算坐标
                int x = i * w + num(10);
                int y = h - num(9);
                if (x < 0) {
                    x = 0;
                }
                if (x + font.getSize() > width) {
                    x = width - font.getSize();
                }
                if (y > height) {
                    y = height;
                }
                if (y - font.getSize() < 0) {
                    y = font.getSize();
                }
                g.drawString(String.valueOf(strs[i]), x, y);
            }
            ImageIO.write(bi, "png", out);
            out.flush();
            ok = true;
        } catch (IOException e) {
            ok = false;
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ok;
    }
}