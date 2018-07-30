package com.wf.captcha;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 测试类
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class CaptchaTest {

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 5; i++) {
            SpecCaptcha specCaptcha = new SpecCaptcha();
            //specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
            System.out.println(specCaptcha.text());
            //specCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".png")));
        }
    }

    @Test
    public void testGIf() throws Exception {
        for (int i = 0; i < 5; i++) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            System.out.println(gifCaptcha.text());
            //gifCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".gif")));
        }
    }

    @Test
    public void testHan() throws Exception {
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
        //chineseCaptcha.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        System.out.println(chineseCaptcha.text());
        //chineseCaptcha.out(new FileOutputStream(new File("D:/Java/aa.png")));
    }

    @Test
    public void testGifHan() throws Exception {
        ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha();
        //chineseGifCaptcha.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        System.out.println(chineseGifCaptcha.text());
        //chineseGifCaptcha.out(new FileOutputStream(new File("D:/Java/aa.gif")));
    }

}
