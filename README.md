# SpringBoot-Shiro-JWT-Scaffolding

## 介绍
这是一个基于SpringBoot-Shiro-JWT-Restful风格封装的脚手架模板

## 项目结构
aspect：AOP切面模块  
common：公用工具模块  
mapper：mybatis相关模块  
model：实体模型  
service：业务逻辑模块  
shiro：鉴权模块  
util：项目专属工具类  
web：控制器相关模块  


## 更新内容
1.创建初始结构--2021年4月23日  
2.拆分成聚合工程--2021年4月24日  
3.新增登录注册鉴权模块--2021年4月27日  
4.新增日志切面模块--2021年5月8日  
5.新增mongodb作为自定义日志存储--2021年6月4日

## 模块调试
1.注册
![img.png](domoImage/register.png)
2.有user身份访问user接口
![img.png](domoImage/user.png)
3.获取RSA公钥
![img.png](domoImage/publicKey.png)
4.登录请求
![img.png](domoImage/login1.png)
![img.png](domoImage/login2.png)
5.使用token请求鉴权接口
![img.png](domoImage/tokenAPI.png)
4.mongodb日志表结构（日志切面比加密切面优先级高，因此不会记录敏感信息）
![img_2.png](domoImage/mongodb.png)