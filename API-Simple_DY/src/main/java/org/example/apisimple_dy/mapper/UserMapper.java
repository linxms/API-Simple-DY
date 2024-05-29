package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.apisimple_dy.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
