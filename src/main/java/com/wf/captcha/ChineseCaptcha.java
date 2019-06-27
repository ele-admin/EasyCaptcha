package com.wf.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ChineseCaptcha extends ChineseCaptchaAbstract {

    public ChineseCaptcha() {
        super();
    }

    public ChineseCaptcha(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public ChineseCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public ChineseCaptcha(int width, int height, int len, Font font) {
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
            AlphaComposite ac3;
            int len = strs.length;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 抗锯齿
            g.setColor(color());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int hp = (height - font.getSize()) >> 1;
            int h = height - hp;
            int w = width / strs.length;
            int sp = (w - font.getSize()) / 2;
            // 画字符串
            for (int i = 0; i < len; i++) {
                // 计算坐标
                int x = i * w + sp + num(-5, 5);
                int y = h + num(-5, 5);
                if (x < 5) {
                    x = 5;
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
                g.setFont(font.deriveFont(num(2) == 0 ? Font.PLAIN : Font.ITALIC));
                g.drawString(String.valueOf(strs[i]), x, y);
            }
            // 随机画干扰线
            g.setStroke(new BasicStroke(1.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);  // 指定透明度
            g.setComposite(ac3);
            drawLine(2, g.getColor(), g);
            // 画干扰圆圈
            drawOval(5, g.getColor(), g);
            
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(bi);
//             ImageIO.write(bi, "png", out);
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
