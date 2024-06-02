package org.example.apisimple_dy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.apisimple_dy.entity.User;

import java.util.Map;

public interface UserService extends IService<User>{
    Map<String, Object> login(User user) throws RuntimeException;

    boolean addUser(User user) throws RuntimeException;

    boolean modify(User user) throws RuntimeException;

    boolean delete(Integer id) throws RuntimeException;

    IPage<User> selectPage(Page<User> page, User user) throws RuntimeException;

    interface VideoService {
    }
}
