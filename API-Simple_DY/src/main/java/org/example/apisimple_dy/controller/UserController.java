package org.example.apisimple_dy.controller;


import io.micrometer.core.annotation.Timed;
import org.example.apisimple_dy.commonIO.Result;
import org.example.apisimple_dy.entity.User;
import org.example.apisimple_dy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 存着新的user对象。
     * @return 返回是否成功添加用户的信息。
     */
    @Timed(value = "userController.post")
    @PostMapping("/addUser")
    public Result<?> addUser(@RequestBody User user){
        if (user.getId() == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            userService.addUser(user);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 登录
     *
     * @param user 用户信息
     * @return 成功则返回token，失败则报错
     */
    @Timed(value = "userController.post")
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        try {
            Map<String, Object> data = userService.login(user);
            return Result.success(data);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }

    }

    /**
     * 修改用户信息
     * @param user 储存的user的新信息，需要对原信息进行覆盖
     * @return 是否修改成功的信息
     */
    @Timed(value = "userController.post")
    @PostMapping("/modifyUser")
    public Result<?> modifyUser(@RequestBody User user){
        try {
            userService.modify(user);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 获取当前用户的信息
     * @param userID 用户对表查询
     * @return 一个user对象，里面存放了对应的信息
     */
    @Timed(value = "userController.get")
    @GetMapping("/getUser")
    public Result<?> getUser(@RequestParam("userID") Integer userID){
        try {
            User user = userService.getById(userID);
            if (user==null)
                throw new RuntimeException("用户不存在");
            return Result.success(user);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Timed(value = "userController.delete")
    @DeleteMapping("/deleteUser")
    public Result<?> deleteUser(@RequestParam("userID") Integer userID){
        try {
            userService.delete(userID);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
