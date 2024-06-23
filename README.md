

# API-Simple-DY
api最后的小组作业

**项目仓库地址**：
> https://github.com/linxms/API-Simple-DY

**后端代码框架** ： `SpringBoot <2.7.17>`

**数据库JDBC操作** ： `Mybatis-plus`
```xml
<!--mybatis-plus-->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.5.3.1</version>
		</dependency>

		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-generator</artifactId>
			<version>3.5.3.1</version>
		</dependency>
```

**token验证**  `JWT`
```xml
<!--JWT-->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.5</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.5</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if you prefer -->
			<version>0.11.5</version>
		</dependency>
```

**数据库** ： `MySQL8.0`

## 项目内容

### 后端

数据库设计：
三张表 ：User、Video、Browse

**User表**

| 字段名 | 类型 | 是否必需 | 键 | 字段描述 |
| ---- | ---- | ---- | ---- | ---- |
| id   | int  | 是 | 主键 | 账号，用于唯一识别 |
| name | varchar(255) | 是 |  | 用户名 |
| password| varchar(255) | 是 |  | 密码 |
| phone | varchar(255) | 否 | | 电话号码 |
| role | int | 是 |  | 角色 |

**Video表**

| 字段名 | 类型 | 是否必需 | 键 | 字段描述 |
| ---- | ---- | ---- |---- | ---- |
| videoID   | int  | 是   | 主键 | 视频号，用于唯一识别 |
| authorID | int |是 | 外键(User中的id) | 创作者账号 |
| content | varchar(255) | 否 |  | 视频简介 |
| videoPath | varchar(255) | 否 |  | 视频存放地址 |
| likes | int | 是 |  | 点赞数 |
| title | varchar(255) | 否 |  | 标题 |

**Browse表**

| 字段名 | 类型 | 是否必需 | 键 | 字段描述 |
| ---- | ---- | ---- |---- | ---- |
| videoID   | int  | 是   | 外键(Video中的videoID) | 视频号 |
| userID   | int  | 是 | 外键(User中的id) | 用户账号 |
| isLiked| int | 是 |  | 是否点赞 |
| content | varchar(255) | 否 |  | 评论 |

**ChatHistory表**

| 字段名    | 类型         | 是否必需 | 键               | 字段描述   |
| --------- | ------------ | -------- | ---------------- | ---------- |
| id        | bigint       | 是       | 主键             | 记录ID     |
| userID    | int          | 是       | 外键(User中的id) | 用户账号   |
| listID    | varchar(255) | 是       |                  | chat列表ID |
| role      | varchar(10)  | 是       |                  | 身份       |
| content   | text         | 是       |                  | 会话内容   |
| timestamp | timestamp    | 是       |                  | 时间戳     |





