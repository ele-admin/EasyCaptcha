package com.wf.captcha.base;

//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;

/**
 * 算术验证码抽象类
 * Created by 王帆 on 2019-08-23 上午 10:08.
 */
public abstract class ArithmeticCaptchaAbstract extends Captcha {
    private String arithmeticString;  // 计算公式

    protected static int difficulty = 10;
    protected static int algorithmSign = 4;

    public ArithmeticCaptchaAbstract() {
        setLen(2);
    }

    /**
     * 生成随机验证码
     *
     * @return 验证码字符数组
     */
    @Override
    protected char[] alphas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(num(difficulty));
            if (i < len - 1) {
                int type = num(1, algorithmSign);
                if (type == 1) {
                    sb.append("+");
                } else if (type == 2) {
                    sb.append("-");
                } else if (type == 3) {
                    sb.append("x");
                }
            }
        }

//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("javascript");
//        try {
//            chars = String.valueOf(engine.eval(sb.toString().replaceAll("x", "*")));
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
        int result = (int) Calculator.conversion(sb.toString().replaceAll("x", "*"));
        this.chars = String.valueOf(result);

        sb.append("=?");
        arithmeticString = sb.toString();
        return chars.toCharArray();
    }

    public String getArithmeticString() {
        checkAlpha();
        return arithmeticString;
    }

    public void setArithmeticString(String arithmeticString) {
        this.arithmeticString = arithmeticString;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * algorithmSign 2 : 支持加法
     * algorithmSign 3 : 支持加减法
     * algorithmSign 4 : 支持加减乘法
     * @param algorithmSign 计算公式标示
     */
    public void supportAlgorithmSign(int algorithmSign){
        this.algorithmSign = algorithmSign;
    }
}
