package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.apisimple_dy.entity.Video;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    @Select("select count(*) from video where authorID = #{authorID} and file_location = #{filePath}")
    int getFIleNum(Integer authorID, String filePath);

    @Select("select * from video order by likes DESC ")
    List<Video> getVideoCommand();

    @Update("update video set likes = likes+1 where videoID = #{videoID}")
    int incrementLikes(Integer videoID);

}
