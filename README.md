# 《中小型养老院信息管理系统的设计与实现》

## 项目简介

本项目是一个基于 Spring Boot + Vue 3 的前后端分离养老院信息管理系统，面向中小型养老院的日常信息化管理场景。系统围绕老人档案、员工信息、房间床位、健康档案、护理记录、缴费记录、公告通知和用户账号等内容进行管理，并支持管理员、员工、家属三类角色登录使用。

项目定位为毕业设计系统，重点是结构清晰、功能完整、可以运行、便于答辩演示。

## 技术栈

### 后端

- Spring Boot 3.2.5
- Java 17
- MyBatis-Plus 3.5.6
- MySQL
- Maven

### 前端

- Vue 3
- Vite
- Element Plus
- Element Plus Icons
- Axios
- Vue Router
- Pinia

## 系统功能

### 管理员 ADMIN

- 首页仪表盘
- 老人信息管理
- 员工信息管理
- 房间床位管理
- 健康档案管理
- 护理记录管理
- 缴费管理
- 公告管理
- 用户账号管理

### 员工 STAFF

- 员工工作台
- 老人信息查看
- 房间床位查看
- 健康档案管理
- 护理记录管理
- 公告查看

### 家属用户 FAMILY

- 用户注册
- 家属用户中心
- 我的老人信息
- 我的健康档案
- 我的护理记录
- 我的缴费记录
- 公告查看

## 项目目录结构

```text
CodexTest
├── backend                 后端 Spring Boot 项目
│   ├── src/main/java       Java 源码
│   ├── src/main/resources  配置文件和 Mapper XML
│   └── pom.xml             Maven 配置
├── frontend                前端 Vue 3 + Vite 项目
│   ├── src                 前端源码
│   ├── package.json        前端依赖和脚本
│   └── vite.config.js      Vite 配置
├── database                数据库脚本
│   ├── nursing_home.sql    建库建表脚本
│   └── init.sql            演示初始化数据
├── docs                    项目文档
└── README.md               项目说明
```

## 环境要求

建议使用以下环境：

- JDK：17
- Maven：3.8+
- Node.js：18+
- MySQL：8.0+
- npm：随 Node.js 安装

## 数据库初始化说明

数据库名称为 `nursing_home`，后端默认连接配置位于 `backend/src/main/resources/application.yml`：

```yaml
url: jdbc:mysql://localhost:3306/nursing_home?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
username: root
password: 123456
```

初始化步骤：

1. 确认 MySQL 服务已启动。
2. 创建数据库和数据表：

```bash
mysql --default-character-set=utf8mb4 -uroot -p123456 < database/nursing_home.sql
```

3. 导入演示数据：

```bash
mysql --default-character-set=utf8mb4 -uroot -p123456 nursing_home < database/init.sql
```

如果使用 MySQL 命令行，也可以进入项目根目录后执行：

```sql
source database/nursing_home.sql;
source database/init.sql;
```

## 后端启动说明

1. 进入后端目录：

```bash
cd backend
```

2. 根据本机 MySQL 情况修改 `src/main/resources/application.yml` 中的数据库账号和密码。

3. 安装依赖并启动项目：

```bash
mvn spring-boot:run
```

后端默认访问地址：

```text
http://localhost:8080
```

## 前端启动说明

1. 进入前端目录：

```bash
cd frontend
```

2. 安装依赖：

```bash
npm install
```

3. 启动开发服务器：

```bash
npm run dev
```

前端默认访问地址：

```text
http://127.0.0.1:5173
```

前端已配置 `/api` 代理到后端 `http://localhost:8080`。

## 默认测试账号

演示数据中包含以下测试账号：

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | admin | 123456 | 拥有全部功能权限 |
| 员工 | staff01 | 123456 | 可访问员工工作台和部分业务模块 |
| 家属 | family01 | 123456 | 可查看绑定老人相关信息 |

也可以在注册页面注册新的家属用户，注册用户默认角色为 `FAMILY`。

## 项目截图说明

项目没有内置截图文件。建议在系统启动后手动截图，并保存到论文或答辩材料中。截图建议包括：

- 登录页面
- 注册页面
- 管理员首页仪表盘
- 员工工作台
- 家属用户中心
- 老人信息管理页面
- 员工信息管理页面
- 房间床位管理页面
- 健康档案管理页面
- 护理记录管理页面
- 缴费管理页面
- 公告管理页面
- 用户账号管理页面
- 新增或编辑弹窗页面
- 不同角色菜单对比页面

详细截图清单见 [docs/项目截图说明.md](docs/项目截图说明.md)。

## 答辩演示流程

建议答辩时按照以下顺序演示：

1. 简要介绍项目背景和技术架构。
2. 展示登录页面和家属注册页面。
3. 使用管理员账号登录，展示首页仪表盘和完整菜单。
4. 演示老人信息、员工信息、房间床位等基础管理模块。
5. 演示健康档案、护理记录、缴费记录、公告管理等核心业务模块。
6. 切换员工账号，展示员工可访问的菜单和功能。
7. 切换家属账号，展示家属用户只能查看个人相关信息。
8. 总结系统实现效果和毕业设计价值。

完整讲解流程见 [docs/答辩演示流程.md](docs/答辩演示流程.md)。

## 常见问题

### 后端启动失败

- 检查 JDK 是否为 17。
- 检查 Maven 依赖是否下载完成。
- 检查 `backend/src/main/resources/application.yml` 中数据库连接是否正确。
- 检查 MySQL 服务是否已启动。

### 前端依赖安装失败

- 建议使用 Node.js 18+。
- 删除异常的 `node_modules` 后重新执行 `npm install`。
- 如果网络较慢，可以切换 npm 镜像源后重试。

### 数据库连接失败

- 确认数据库 `nursing_home` 已创建。
- 确认 MySQL 用户名、密码与 `application.yml` 一致。
- 确认 MySQL 端口为 `3306`。
- 确认已执行建表脚本和初始化数据脚本。

### 页面接口请求失败

- 确认后端已启动并监听 `8080` 端口。
- 确认前端通过 `npm run dev` 启动。
- 检查浏览器控制台和后端控制台错误信息。
- 确认前端 Vite 代理 `/api` 指向 `http://localhost:8080`。

### 登录失败

- 确认已导入 `database/init.sql`。
- 使用默认账号 `admin / 123456`、`staff01 / 123456`、`family01 / 123456` 测试。
- 检查用户状态是否为“正常”。
