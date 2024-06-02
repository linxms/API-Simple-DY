package org.example.apisimple_dy.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
public class Video {
    private Integer videoID;

    private Integer authorID;

    private String content;

    @TableField(value = "filelocation")
    private String file_location;

    private Integer likes;

    private String title;

    public Video(Integer authorID, String file_location){
        this.authorID = authorID;
        this.file_location = file_location;
    }
}
