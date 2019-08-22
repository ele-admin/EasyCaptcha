package com.wf.captcha;

import com.wf.captcha.base.Captcha;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试类
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class CaptchaTest {

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 5; i++) {
            SpecCaptcha specCaptcha = new SpecCaptcha();
            // specCaptcha.setCharType(Captcha.TYPE_ONLY_UPPER);
            System.out.println(specCaptcha.text());
            // specCaptcha.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/actionj.ttf").getFile())).deriveFont(Font.BOLD, 32));
            specCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".png")));
        }
    }

    @Test
    public void testGIf() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            System.out.println(gifCaptcha.text());
            gifCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".gif")));
        }*/
    }

    @Test
    public void testHan() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
            System.out.println(chineseCaptcha.text());
            chineseCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".png")));
        }*/
    }

    @Test
    public void testGifHan() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha();
            System.out.println(chineseGifCaptcha.text());
            chineseGifCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".gif")));
        }*/
    }

    @Test
    public void test2() throws Exception {
        String[] colors = new String[]{"#8cc540", "#009f5d", "#019fa0", "#019fde", "#007cdc", "#887ddd", "#cd7bdd", "#ff5675", "#ff1244", "#ff8345", "#f8bd0b", "#d1d2d4"};
        String[] fonts = new String[]{"actionj.ttf", "epilog.ttf", "fresnel.ttf", "headache.ttf", "lexo.ttf", "prefix.ttf", "progbot.ttf", "ransom.ttf", "robot.ttf", "scandal.ttf"};
        int i = 0;
        for (String f : fonts) {
            i++;
            FileOutputStream out = new FileOutputStream(new File("D:/Java/aa" + i + ".png"));
            try {
                int width = 130, height = 48;
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);
                // 抗锯齿
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                // 画字符串
                float fontSize = 32f;
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/" + f).getFile())).deriveFont(Font.BOLD, fontSize);
                g.setFont(font);
                char[] chars = "N4WbS".toCharArray();
                float sp = (width / chars.length - fontSize) / 2;
                int j = 0;
                for (char c : chars) {
                    j++;
                    g.setColor(Color.getColor(colors[j]));
                    float x = sp * j + fontSize * (j - 1);
                    float y = (height - fontSize) / 2 + fontSize;
                    System.out.println(x + "-" + y);
                    g.drawString(String.valueOf(c), x, y);
                }
                g.drawString("Hello", 8, 37);
                //
                QuadCurve2D shape = new QuadCurve2D.Double();
                shape.setCurve(8, 38, 38, 10, 120, 40);
                g.draw(shape);
                ImageIO.write(bi, "png", out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test3() throws Exception {
        String filePath = getClass().getResource("/actionj.ttf").getFile();
        System.out.println(filePath);
    }


}
