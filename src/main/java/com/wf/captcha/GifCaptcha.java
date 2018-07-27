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
        g2d.setColor(Color.WHITE);  // 填充背景颜色
        g2d.fillRect(0, 0, width, height);
        AlphaComposite ac3;
        int h = height - ((height - font.getSize()) >> 1);
        int w = width / len;
        g2d.setFont(font);
        // 随机画干扰线
        for (int i = 0; i < 8; i++) {
            int x1 = num(-10, width - 10);
            int y1 = num(5, height - 5);
            int x2 = num(10, width + 10);
            int y2 = num(2, height - 2);
            g2d.setColor(color(150, 250));
            g2d.setStroke(new BasicStroke(1.3f));
            g2d.drawLine(x1, y1, x2, y2);
            // 画干扰圆圈
            g2d.setColor(color(100, 250));
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawOval(num(width), num(height), 5 + num(10), 5 + num(10));
        }
        // 画验证码
        for (int i = 0; i < len; i++) {
            ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            g2d.drawString(String.valueOf(strs[i]), (width - (len - i) * w) + (w - font.getSize()) + num(7, 11), h - num(2, 6));
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
        float r = (float) 1 / len, s = (len + 1) * r;
        return num > len ? (num * r - s) : num * r;
    }

}
