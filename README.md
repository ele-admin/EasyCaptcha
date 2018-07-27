# EasyCaptcha

## 1.简介

&emsp;&emsp;Java图形验证码，支持gif验证码，可用于Java Web、JavaSE和Android项目。


## 2.效果展示

**gif效果：**

![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftog813jz9g303m01cjrc.jpg) 
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftogh8z6hug303m01cdfs.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftoghsymykg303m01c3yg.jpg)

**png效果：**

![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftogec24tbj303m01cmwx.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftogf2vvodj303m01cjr5.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKgy1ftogfxh2rwj303m01cjr5.jpg)



## 3.导入项目

### 2.1.gradle方式的引入
需要先在project的build.gradle中添加：
```text
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
在项目的build.gradle中添加
```text
dependencies {
    compile 'com.github.whvcse:EasyCaptcha:1.2.0'
}
```

### 2.2.maven方式引入
在你的pom.xml中添加如下代码：
```xml
<project>
    <repositories>
        <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
    <dependencies>
       <dependency>
          <groupId>com.github.whvcse</groupId>
          <artifactId>EasyCaptcha</artifactId>
          <version>1.2.0</version>
       </dependency>
    </dependencies>
</project>

```

### 2.3.jar包下载
[EasyCaptcha-1.2.0.jar](https://github.com/whvcse/EasyCaptcha/releases)


---


## 3.使用方法

### 3.1.快速使用
1.在web.xml里面加入如下配置：
```xml
<web-app>
    <!-- 图形验证码servlet -->
    <servlet>
        <servlet-name>CaptchaServlet</servlet-name>
        <servlet-class>com.wf.captcha.servlet.CaptchaServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>CaptchaServlet</servlet-name>
        <url-pattern>/images/captcha</url-pattern>
    </servlet-mapping>
</web-app>

```
2.前端代码
```html
<img src="/images/captcha" />
```

### 3.2.在SpringMVC中使用
也可以使用controller的形式输出验证码，方法如下：
```java
@Controller
public class MainController {
    
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
```
前端代码：
```html
<img src="/images/captcha" />
```

### 3.3.判断验证码是否正确

```java
@Controller
public class LoginController {
    
    @PostMapping("/login")
    public JsonResult login(String username,String password,String code){
        
        if (CaptchaUtil.ver(code, request)) {
            return JsonResult.error("验证码不正确");
        }
    }   
}
```

### 3.4.设置宽高和位数
```java
@Controller
public class MainController {
    
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);
        
        // 设置宽、高、位数
        CaptchaUtil.out(130, 48, 5, request, response);
    }
}
```

## 4.更多设置

###
