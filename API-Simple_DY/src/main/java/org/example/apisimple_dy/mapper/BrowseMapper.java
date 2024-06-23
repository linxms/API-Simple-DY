package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.apisimple_dy.entity.Browse;
import org.example.apisimple_dy.entity.Video;

import java.util.List;

@Mapper
public interface BrowseMapper extends BaseMapper<Browse> {
    @Select("select videoID from browse where userID = #{userID}")
    List<Integer> findBrowsedVideo(Integer userID);

    @Select("select * from browse where videoID = #{videoID} and userID = #{userID}")
    int findVideoIsBrowsed(Integer videoID, Integer userID);

    @Select("select isLiked from browse where #{videoID} and userID = #{userID}")
    int findVideoIsLiked(Integer videoID, Integer userID);

    @Update("update browse set isLiked = isLiked + 1 where videoID = #{videoID} and userID = #{userID}")
    int putIsLiked(Integer videoID, Integer userID);


    @Delete("DELETE FROM browse WHERE videoID = #{videoID}")
    int deleteBrowseByVideoId(Integer videoID);
}
