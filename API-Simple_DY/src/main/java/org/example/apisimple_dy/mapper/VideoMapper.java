package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.apisimple_dy.entity.Video;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    @Select("select count(*) from video where authorID = #{authorID} and file_location = #{filePath}")
    int getFIleNum(Integer authorID, String filePath);

}
