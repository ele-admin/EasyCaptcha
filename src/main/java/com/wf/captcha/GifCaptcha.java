package com.wf.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Gif验证码类
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class GifCaptcha extends Captcha {

    public GifCaptcha() {
    }

    public GifCaptcha(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public GifCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public GifCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    @Override
    public boolean out(OutputStream os) {
        checkAlpha();
        boolean ok = false;
        try {
            char[] rands = textChar();  // 获取验证码数组
            GifEncoder gifEncoder = new GifEncoder();
            gifEncoder.start(os);
            gifEncoder.setQuality(180);
            gifEncoder.setDelay(100);
            gifEncoder.setRepeat(0);
            BufferedImage frame;
            Color fontcolor[] = new Color[len];
            for (int i = 0; i < len; i++) {
                fontcolor[i] = new Color(20 + num(110), 20 + num(110), 20 + num(110));
            }
            for (int i = 0; i < len; i++) {
                frame = graphicsImage(fontcolor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
            ok = true;
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ok;
    }

    /**
     * 画随机码图
     *
     * @param fontcolor 随机字体颜色
     * @param strs      字符数组
     * @param flag      透明度使用
     * @return BufferedImage
     */
    private BufferedImage graphicsImage(Color[] fontcolor, char[] strs, int flag) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        // 填充背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        // 抗锯齿
        g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 随机画干扰线
        for (int i = 0; i < num(5, 12); i++) {
            g2d.setStroke(new BasicStroke(1.1f + RANDOM.nextFloat() / 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g2d.setColor(color(150, 250));
            int x1 = num(-10, width - 10);
            int y1 = num(5, height - 5);
            int x2 = num(10, width + 10);
            int y2 = num(2, height - 2);
            g2d.drawLine(x1, y1, x2, y2);
            // 画干扰圆圈
            g2d.setColor(color(100, 250));
            g2d.drawOval(num(width), num(height), 5 + num(25), 5 + num(25));
        }
        // 画验证码
        int hp = (height - font.getSize()) >> 1;
        int h = height - hp;
        int w = width / strs.length;
        //int sp = (w - font.getSize()) / 2;
        for (int i = 0; i < strs.length; i++) {
            AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            // 计算坐标
            int x = i * w + num(6);
            int y = h - num(2, 8);
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
            g2d.drawString(String.valueOf(strs[i]), x, y);
        }
        g2d.dispose();
        return image;
    }

    /**
     * 获取透明度,从0到1,自动计算步长
     *
     * @param i
     * @param j
     * @return 透明度
     */
    private float getAlpha(int i, int j) {
        int num = i + j;
        float r = (float) 1 / (len - 1);
        float s = len * r;
        return num >= len ? (num * r - s) : num * r;
    }

}
