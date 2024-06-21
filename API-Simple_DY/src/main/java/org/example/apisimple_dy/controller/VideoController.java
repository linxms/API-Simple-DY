package org.example.apisimple_dy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @PostMapping("/addVideo")
    public Result<?> addVideo(@RequestParam("file")MultipartFile videoFile, @RequestBody Video video){
        if (video.getAuthorID() == null){
            throw new RuntimeException("数据不能为空");
        }
        try {
            videoService.videoUpload(videoFile, video);
            return Result.success();
        } catch (RuntimeException | IOException e) {
            return Result.fail(e.getMessage());
        }
    }

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

    @PostMapping("/deleteVideo")
    public Result<?> deleteVideo(@PathVariable Integer authorID, @PathVariable Integer videoID){
        try {
            videoService.deleteVideo(authorID, videoID);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

}
