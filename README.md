

# API-Simple-DY
api最后的小组作业

芜湖~！！！！！！！！！！！！！！！

泥嚎
mlgb的，前端快动！！！！
> 有事没事往这里面写点东西

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

## 接口文档

* 接口主要分为User、Video、Browse三个模块

**返回Body**

*User*
```JAVA
{
    Integer userID,
    String name,
    String password,
    String phone,
    Integer role;

}
```

*Video*

```JAVA
{
    Integer videoID,
    Integer authorID,
    String content,
    String videoPath,
    Integer likes,
    String title;
}
```

*Browse*

```JAVA
{
    Integer videoID,
    Integer userID,
    Integer isLiked,
    String content;
}
```

### User模块: /user

**登录**（/login）

> 说明：用于用户登录系统

* 接口类型: `POST`
* 参数类型: 
    * @RequestBody User user
* 返还参数：    
    * 成功：
        * 状态码(code)：200
        * 信息(messgae)：success
        * 数据(data): null
    * 失败：
        * 状态码(code): 20001
        * 信息:(message): 登录失败
        * 数据(data): null  

**注册**（/addUser）
> 说明：用于添加新用户

* 接口类型: `POST`
* 参数类型: 
    * @RequestBody User user
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 注册失败/已有账号
        * 数据(data): null

**注销** （/deleteUser）
> 说明：注销自己的账号

* 接口类型: `POST`
* 参数类型: 
    * @RequestParam("userID") Integer userID
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 失败
        * 数据(data): null

**获取信息**（/getUser）
> 说明：获取用户自己的信息

* 接口类型: `GET`
* 参数类型: 
    * @RequestParam("userID") Integer userID
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): User对象
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 错误
        * 数据(data): null


### Video模块：/video

**发布视频**（/addVideo）
> 说明：用户发布自己的视频

* 接口类型: `POST`
* 参数类型: 
    * @RequestParam('file') MultipartFile videoFile
    * @RequestBody Video video
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null

**删除视频**（/deleteVideo）
> 说明：用户删除自己的视频

* 接口类型: `POST`
* 参数类型: 
    * @RequestParam("authorID") Integer authorID
    * @RequestParam("videoID") Integer videoID
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null

**查看视频**（/pageGetVideo）
> 说明：用户查看自己的视频

* 接口类型: `GET`
* 参数类型: 
    * @RequestParam("pageNum") Integer pageNum        (分页查询的页号)
    * @RequestParam("pageSize") Integer pageSize      (分页查询每页大小)
    * @RequestBody Video video (Video对象)
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): IPage videoPage (将分页查询存储在其中)
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null

### Browse模块 /browse

**推荐视频**（/videoCommand）
> 说明：按照点赞数从高到低来推荐视频

* 接口类型: `GET`
* 参数类型: 
    * @RequestParam("userID") Integer userID
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): List videos (将分页查询存储在List列表中)
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null

**浏览视频**（/browseVideo）
> 说明：用户浏览视频，并将浏览记录保存在表Browse中

* 接口类型: `POST`
* 参数类型: 
    * @RequestBody Browse browse
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null

**点赞视频**（/putLikes）
> 说明：对浏览视频进行点赞，已点赞过的视频无法再次点赞

* 接口类型: `POST`
* 参数类型: 
    * @RequestBody Video video
    * @RequestParam("userID") Integer userID
* 返还参数:
    * 成功:
        * 状态码(code): 200
        * 信息(message): success
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 异常
        * 数据(data): null
    * 失败:
        * 状态码(code): 20001
        * 信息(messgae): 视频已经点赞
        * 数据(data): null