# User-Check

---
## 简介

从我开始用spring boot做web项目，很多时候都绕不开“用户”所涉及到的基础功能，例如登录、注册。</p>
这些功能并不复杂，但会涉及到一些如手机号码、邮箱等字符串的合法性校验的操作。</p>
虽然可以用微服务搭建一个“用户中心”来一劳永逸解决掉所有涉及用户的操作，但目前所做的大多项目都是相对独立的，且没有过多的资源去搭建微服务，所以想着趁着学校的毕设的这段时间顺手将一些功能封装起来，并尽可能使其具有一定的复用性，以便自己以后做项目方便调用，同时也能锻炼自己的代码能力。</p>
至于为什么要将项目命名为user-check，因为大多数操作都是和用户校验相关，当然也不排除我啥东西都往里面塞……

## 下载与安装
注意：该项目有用到一些Java 17的特性语法 </p>
1. 下载源码
2. `mvn install` 该项目
3. 导入依赖

## 功能说明
### ParamUtils
用于校验一些参数的情况，比方说校验一个对象里面的参数是不是都是非空值

- Method 
  - `boolean isAnyBlank(Object... params)` 校验传入参数，如果是字符串校验是否是空白，其他校验是否为null
  - `boolean isAnyBlank(T object)` 校验传入对象的值，如果是字符串校验是否是空白，其他校验是否为null
  - `void toNull(T object)` 将对象当中的0长度字符串的字段转换为null，用于处理前端传空字符串的情况

### JwtUtils
用于生成和解析jwt的工具类，该类复用性较差

- Entity
  - `JwtEntity` 返回生成的jwt及对应的签名key，由于使用了`io.jsonwebtoken`新的`Jwts.SIG.HS256.key().build();`方法进行`SecretKey`的生成（每次生成都是不一样的值），为简化生成时的操作，将jwt和key一并传回，校验时再一并传入。

- Method
  - `JwtEntity generateHs256Jwt(Map<String, Object> claims, long time, TemporalUnit unit)` 根据传入map生成hs256的jwt和对应的签名key，并设置过期时间
  - `JwtEntity generateHs256Jwt( T sourceObject, long time, TemporalUnit unit)` 根据传入对象生成，对象中为null的值参与在负载中的构建
  - `Claims parseJwt(JwtEntity jwtEntity)` 解析jwt，并获取相应的负载Map
  - `T parseJwt(JwtEntity jwtEntity, Class<T> targetClass)` 解析jwt，并将其转换为目标对象

### StringUtils
封装了一些自己会用到的但是lang3包里面没有的，只是一些比较简单的方法
- Method
  - `boolean strLengthBetween(String str, int minLength, int maxLength)` 判断字符串长度是否在某个闭区间
  - `boolean strLengthGT(String str, int minLength)` 判断字符串长度是否大于某个值
  - `boolean strLengthGTE(String str, int minLength)` 判断字符串长度是否大于等于某个值
  - `boolean strLengthLT(String str, int maxLength)` 判断字符串长度是否小于某个值
  - `boolean strLengthLTE(String str, int maxLength)` 判断字符串长度是否小于等于某个值

### PasswordUtils
封装部分对于密码的操作，主要是对`commons-codec`进行了一定的封装
- Method
  - `String encryptPassword(String str, MessageDigestAlgorithmEnum algorithmEnum)` 密码加密
  - `String encryptPassword(String str, String salt, MessageDigestAlgorithmEnum algorithmEnum)` 带盐密码加密
  - `boolean comparePassword(String str1, String str2)` 比较密码  

### EmailUtils
封装部分邮箱的操作
- Method
  - `boolean isIllegalEmail(String email)` 判断邮箱是否合法

### PhoneNumberUtils
封装部分对于手机号码的操作
- Method
  - `boolean isIllegalChinaPhoneNumber(String phoneNumber)` 校验是不是国内11位号码

### UsernameUtils
封装部分对于用户的操作
- Method
  - `boolean onlyNumLetterUnderscore(String str)` 判断是否只存在数字、字母、下划线

