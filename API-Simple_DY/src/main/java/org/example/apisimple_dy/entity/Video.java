package org.example.apisimple_dy.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Video {
    private Integer videoID;

    private Integer authorID;

    private String content;

    @TableField(value = "filelocation")
    private String file_location;

    private Integer likes;


}
