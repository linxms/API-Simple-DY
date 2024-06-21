package org.example.apisimple_dy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Browse {
    @TableId(value = "videoID")
    private Integer videoID;

    private Integer userID;

    private Integer isLiked;

    private String content;
}
