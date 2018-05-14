# EasyCaptcha
JavaWeb图形验证码，支持gif验证码，可用于基于的session的web项目和前后端分离的项目。

## 效果展示
![验证码](http://115.159.40.243:8080/EasyWeb/image/captcha?codeKey=a)


## 导入项目
### gradle方式的引入
需要先在project的build.gradle中添加：
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
在项目的build.gradle中添加
```
dependencies {
    compile 'com.github.whvcse:EasyCaptcha:1.1.0'
}
```

### maven方式引入
```xml
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
      <version>1.1.0</version>
   </dependency>
</dependencies>
```

### jar包下载
[EasyCaptcha-1.1.0.jar](https://github.com/whvcse/EasyCaptcha/releases)

## 使用方法
### 快速使用
1.在web.xml里面加入如下配置：
```xml
<!-- 图形验证码 -->
<servlet>
    <servlet-name>CaptchaServlet</servlet-name>
    <servlet-class>com.wf.captcha.servlet.CaptchaServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>CaptchaServlet</servlet-name>
    <url-pattern>/image/captcha</url-pattern>
</servlet-mapping>
```
2.前端代码
```html
<img src="/image/captcha" />
```

### 在*SpringMVC*中使用
如果你不想使用项目提供的servlet，可以使用controller的形式实现，方法也很简单，代码如下：
```java
@RequestMapping("/images/captcha")
public void captcha(HttpServletRequest request, HttpServletResponse response) {
    CaptchaUtil captcha = new CaptchaUtil();
    try {
        captcha.out(request, response);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
前端代码：
```html
<img src="/image/captcha" />
```

### 前后端分离中使用
验证码一般都是保存在session中的，但是在前后端分离的项目中，不推荐使用session存储，可使用如下方式：
```java
@RequestMapping("/images/captcha")
public void captcha(String key, HttpServletRequest request, HttpServletResponse response) {
    CaptchaUtil captcha = new CaptchaUtil();
    try {
        captcha.out(key, request, response);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
与前面的使用区别就在于多了一个key，使用的时候需要前端生成一个随机key传递过来，服务器是已这个key为名字存储在servletContext中的，取的时候需要根据这个key取值。

前后端分离也同样可以使用框架自带的servlet，使用方式如下：
```html
<img src="/image/captcha?key=xxx" />
<!-- 此处的key应该有js随机生成，并且js在验证的时候也需要传递这个key -->

```

### 判断验证码是否正确
基于session存储的判断：
```java
CaptchaUtil captcha = new CaptchaUtil();
if (captcha == null || !captcha.ver(code, request)) {
    return JsonResult.error("验证码不正确");
}
```
前后端分离方式的判断：
```java
CaptchaUtil captcha = new CaptchaUtil();
if (captcha == null || !captcha.ver(key, code, request)) {
    return JsonResult.error("验证码不正确");
}
//此处的key便是生成的时候传递的key
```


### 参数设置
#### 设置宽高和位数
```java
//三个参数分别是宽、高、位数
CaptchaUtil captcha = new CaptchaUtil(130, 38, 5);
```
#### 修改存储时候的key
```java
CaptchaUtil captcha = new CaptchaUtil();
captcha.setCodeName("captcha");
```
默认存在session中是以captcha为key存储的，存储在servletContext中是以captcha-xxx为key存储的，xxx是生成的时候前端传递的key。