package org.example.apisimple_dy.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
public class Video {
    @TableField(value = "videoID")
    private Integer videoID;

    @TableField(value = "authorID")
    private Integer authorID;

    private String content;

    @TableField(value = "videoPath")
    private String videoPath;

    private Integer likes;

    private String title;

    public Video(Integer authorID, String videoPath){
        this.authorID = authorID;
        this.videoPath = videoPath;
    }

    public Video(Integer authorID){
        this.authorID = authorID;
    }
}
