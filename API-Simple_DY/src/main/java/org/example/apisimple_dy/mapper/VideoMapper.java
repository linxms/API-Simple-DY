package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.apisimple_dy.entity.Video;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    @Select("SELECT * FROM video WHERE videoID = #{videoID}")
    Video selectById(@Param("videoID") Integer videoID);

    @Select("select count(*) from video where authorID = #{authorID} and videoPath = #{filePath}")
    int getFIleNum(Integer authorID, String filePath);

    @Select("select * from video order by likes DESC ")
    List<Video> getVideoCommand();

    @Update("update video set likes = likes+1 where videoID = #{videoID}")
    int incrementLikes(Integer videoID);

    @Delete("DELETE FROM video WHERE videoID = #{videoID}")
    int deleteById(@Param("videoID") Integer videoID);


}
