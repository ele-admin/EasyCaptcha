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
        SpecCaptcha specCaptcha = new SpecCaptcha();
        System.out.println(specCaptcha.text());
        specCaptcha.out(new FileOutputStream(new File("D:/a/aa.png")));
    }

    @Test
    public void testGIf() throws Exception {
        GifCaptcha specCaptcha = new GifCaptcha(130, 48, 5);
        System.out.println(specCaptcha.text());
        specCaptcha.out(new FileOutputStream(new File("D:/a/aa.gif")));
    }
}
