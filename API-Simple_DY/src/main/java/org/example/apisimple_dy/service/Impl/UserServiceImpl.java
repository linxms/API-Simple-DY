package org.example.apisimple_dy.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.apisimple_dy.config.JWTUtil;
import org.example.apisimple_dy.entity.User;
import org.example.apisimple_dy.mapper.UserMapper;
import org.example.apisimple_dy.service.UserService;

import java.util.HashMap;
import java.util.Map;


public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    JWTUtil jwtUtil;
    public UserServiceImpl(UserMapper userMapper){
        this.baseMapper = userMapper;
    }
    @Override
    public Map<String, Object> login(User user) throws RuntimeException {
        try {
            User loginUser = this.baseMapper.selectById(user.getId());

            if (loginUser == null){
                throw new RuntimeException("该用户不存在");
            } else if (user.getPassword() != loginUser.getPassword()){
                throw new RuntimeException("密码错误，请重新输入");
            } else {
                Map<String, Object> tokenClaim = new HashMap<>();
                tokenClaim.put("name", loginUser.getName());
                tokenClaim.put("role", loginUser.getRole());

                // 生成token
                String token = jwtUtil.doGenerateToken(tokenClaim, loginUser.getId());

                // 返回token和用户数据
                Map<String, Object> data = new HashMap<>();

                if (token != null){
                    data.put("token", token);
                    data.put("user", loginUser);
                } else {
                    throw new RuntimeException("获取token异常！");
                }

                if (!data.isEmpty()){
                    return data;
                } else {
                    throw new RuntimeException("其他异常");
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean addUser(User user) throws RuntimeException {
        return false;
    }

    @Override
    public boolean modify(User user) throws RuntimeException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws RuntimeException {
        return false;
    }

    @Override
    public IPage<User> selectPage(Page<User> page, User user) throws RuntimeException {
        return null;
    }
}
