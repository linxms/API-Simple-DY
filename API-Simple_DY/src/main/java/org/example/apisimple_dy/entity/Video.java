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
