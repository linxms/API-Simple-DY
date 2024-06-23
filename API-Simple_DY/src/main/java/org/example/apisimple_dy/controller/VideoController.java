package org.example.apisimple_dy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.core.annotation.Timed;
import org.example.apisimple_dy.commonIO.Result;
import org.example.apisimple_dy.entity.Video;
import org.example.apisimple_dy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Timed(value = "videoController.post")
    @PostMapping("/addVideo")
    public Result<?> addVideo(@RequestParam("sourcePath") String sourcePath, @RequestParam("videoName") String videoName, @RequestBody Video video){
        if (video.getAuthorID() == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            videoService.videoUpload(sourcePath, videoName, video);
            return Result.success();
        } catch (RuntimeException | IOException e) {
            return Result.fail(e.getMessage());
        }
    }

    @Timed(value = "videoController.get")
    @GetMapping("/pageGetVideo")
    public Result<?> getVideo(@RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize,
                              @RequestBody Video video){
        try {
            Page<Video> page = new Page<>(pageNum, pageSize);
            IPage<Video> videoIPage = videoService.selectMyVideo(page, video.getAuthorID());
            return Result.success(videoIPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Timed(value = "videoController.delete")
    @DeleteMapping("/deleteVideo")
    public Result<?> deleteVideo(@RequestParam("authorID") Integer authorID, @RequestParam("videoID") Integer videoID){
        try {
            videoService.deleteVideo(authorID, videoID);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

}
