# 数据脱敏工具

#### 一、介绍
1. 支持13种类型数据的脱敏，例如：邮箱地址、手机号、身份证、银行卡、车牌号、姓名、家庭地址、ip地址、生日、密码等
2. 配置灵活，同时支持全局脱敏和局部脱敏
3. 任意对象都可以被脱敏
4. 支持jsonpath表达式，可灵活控制同一个对象不同层级关系时既要脱敏又要不脱敏的场景
---
#### 二、软件架构

待补充

---


#### 三、快速入门

##### 1.  添加依赖
```
<dependency>
  <groupId>io.github.applesline</groupId>
  <artifactId>desensitize</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
##### 2.  打开脱敏开关 @EnableDesensitize
```
import org.applesline.desensitize.annotation.EnableDesensitize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDesensitize
public class DemoApplication {

    public static void main(String[] args) {
	SpringApplication.run(DemoApplication.class, args);
    }
}

```
##### 3.  针对方法的局部脱敏 @Desensitize
```
    @RequestMapping("queryResult")
    @Desensitize(fieldMapping = @FieldMapping(fields = {"_mobile"},type = DesensitizeType.MOBILE))
    public Result queryResult() {
        return new Result("123456789@163.com","17622233344");
    }
```

##### 4. 脱敏效果展示（响应体中的_mobile字段被脱敏了）
```
{
    "_email": "123456789@163.com",
    "_mobile": "176****3344"
}
```
---

#### 四、场景示例
##### 场景一：多字段脱敏

###### 1.1 配置示例
```
@Desensitize(
    fieldMapping = {
    @FieldMapping(fields = {"_mobile"},type = DesensitizeType.MOBILE),
    @FieldMapping(fields = {"_email"},type = DesensitizeType.EMAIL)}
)
```

###### 1.2 脱敏效果
```
{
    "_email": "123******@163.com",
    "_mobile": "176****3344"
}
```



##### 场景二：多字段匹配同一种脱敏算法


###### 2.1 配置示例
```
    @Desensitize(
            fieldMapping = {
            @FieldMapping(fields = {"_mobile","phone"},type = DesensitizeType.MOBILE),
            @FieldMapping(fields = {"_email"},type = DesensitizeType.EMAIL)}
    )
```

###### 2.2 脱敏效果

```
{
    "phone": "123**********.com",
    "result": {
        "_email": "123******@163.com",
        "_mobile": "176****3344"
    }
}
```




##### 场景三：忽略字段名相同层级不同的字段
###### 3.1 注解配置

```
    @Desensitize(
            fieldMapping = {
            @FieldMapping(fields = {"_mobile","phone"},type = DesensitizeType.MOBILE),
            @FieldMapping(fields = {"_email"},type = DesensitizeType.EMAIL)},
            ignoreByJpe = "$._mobile"
    )
```
###### 3.2 脱敏效果

```
{
    "_mobile": "17622233345",
    "result": {
        "_email": "123******@163.com",
        "_mobile": "176****3344"
    }
}
```

##### 场景四：全局配置脱敏规则局部方法中使用
###### 4.1 启动类中配置全局规则

```
@SpringBootApplication
@EnableDesensitize(
		fieldMapping = {
				@FieldMapping(fields = {"_mobile","phone"},type = DesensitizeType.MOBILE),
				@FieldMapping(fields = {"_email"},type = DesensitizeType.EMAIL)},
		ignoreByJpe = "$._mobile"
)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

```
###### 4.2 局部方法中使用全局规则

```
    @Desensitize
    public ApiResult queryResult() {
        return new ApiResult("17622233345",new Result("123456789@163.com","17622233344"));
    }
```
###### 4.3 脱敏效果

```
{
    "_mobile": "17622233345",
    "result": {
        "_email": "123******@163.com",
        "_mobile": "176****3344"
    }
}
```


##### 场景五：局部方法中覆盖全局配置的脱敏规则
###### 5.1 启动类中配置全局规则
```
@SpringBootApplication
@EnableDesensitize(
		fieldMapping = {
				@FieldMapping(fields = {"_mobile","phone"},type = DesensitizeType.MOBILE),
				@FieldMapping(fields = {"_email"},type = DesensitizeType.EMAIL)},
		ignoreByJpe = "$._mobile"
)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

```
###### 5.2 局部方法中覆盖全局规则

```
    @Desensitize( fieldMapping =  @FieldMapping(fields = {"_mobile"},type = DesensitizeType.MOBILE) )
    public ApiResult queryResult() {
        return new ApiResult("17622233345",new Result("123456789@163.com","17622233344"));
    }
```
###### 5.3 脱敏效果

```
{
    "_mobile": "176****3345",
    "result": {
        "_email": "123456789@163.com",
        "_mobile": "176****3344"
    }
}
```
##### 场景中依赖的实体类结构
```
class ApiResult {
    private String _mobile;
    private Result result;

    public ApiResult(String _mobile, Result result) {
        this._mobile = _mobile;
        this.result = result;
    }

    setter...
    getter...
}

class Result {
    private String _email;
    private String _mobile;

    public Result(String _email, String _mobile) {
        this._email = _email;
        this._mobile = _mobile;
    }

    setter...
    getter...
}
```
---
#### 五、联系作者
使用过程中有任何问题欢迎前来骚扰~_~ 

邮箱地址：applesline@163.com




