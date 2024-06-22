package org.example.apisimple_dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.apisimple_dy.entity.ChatHistory;

import java.util.List;

@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {
    @Select("SELECT * FROM chat_history WHERE user_id = #{userId} AND list_id = #{listId} ORDER BY timestamp ASC")
    List<ChatHistory> selectByUserIdAndListId(@Param("userId") String userId, @Param("listId") String listId);

    @Select("SELECT * FROM chat_history WHERE user_id = #{userId} ORDER BY list_id, timestamp ASC")
    List<ChatHistory> selectByUserId(@Param("userId") String userId);
}
