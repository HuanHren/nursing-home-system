# 中小型养老院信息管理系统

本项目用于毕业设计《中小型养老院信息管理系统的设计与实现》。

## 项目结构

```text
backend/   后端 Spring Boot 项目
frontend/  前端 Vue 3 项目
database/  数据库 SQL 脚本
docs/      项目文档
```

## 技术栈

后端：Java 17、Spring Boot、MyBatis-Plus、MySQL、Maven

前端：Vue 3、Vite、Element Plus、Axios、Vue Router、Pinia

## 开发说明

当前版本只包含基础项目结构，暂未实现完整业务模块。

后续开发顺序建议：

1. 登录功能
2. 首页统计
3. 老人信息管理
4. 员工信息管理
5. 房间床位管理
6. 健康档案管理
7. 护理记录管理
8. 缴费管理
9. 公告管理

## 运行方式

后端：

```bash
cd backend
mvn spring-boot:run
```

前端：

```bash
cd frontend
npm install
npm run dev
```
