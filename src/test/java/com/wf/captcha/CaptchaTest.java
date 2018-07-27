package com.wf.captcha;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 测试类
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class CaptchaTest {

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 100; i++) {
            SpecCaptcha specCaptcha = new SpecCaptcha();
            specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
            System.out.println(specCaptcha.text());
            specCaptcha.out(new FileOutputStream(new File("D:/a/aa" + i + ".png")));
        }
    }

    @Test
    public void testGIf() throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        System.out.println(gifCaptcha.text());
        //gifCaptcha.out(new FileOutputStream(new File("D:/a/aa.gif")));
    }
}
